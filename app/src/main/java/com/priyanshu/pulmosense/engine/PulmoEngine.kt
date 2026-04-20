package com.priyanshu.pulmosense.engine

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jtransforms.fft.FloatFFT_1D
import org.tensorflow.lite.Interpreter
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.math.*

class PulmoEngine(context: Context) {
    private var interpreter: Interpreter

    // CRITICAL FIX: Keep a class-level reference to the buffer so the Garbage Collector doesn't delete our model!
    private var modelByteBuffer: ByteBuffer

    // Audio & Spectrogram Constants
    private val SAMPLE_RATE = 16000
    private val RECORD_TIME_SEC = 20
    private val CHUNK_TIME_SEC = 5
    private val SAMPLES_PER_CHUNK = SAMPLE_RATE * CHUNK_TIME_SEC
    private val N_FFT = 1024
    private val HOP_LENGTH = 512
    private val N_MELS = 128
    private val TIME_STEPS = 157

    init {
        // 1. Open the file directly as a standard stream
        val inputStream = context.assets.open("pulmosense.tflite")
        val modelBytes = inputStream.readBytes()
        inputStream.close()

        // 2. Assign it to the CLASS variable, not a temporary local variable
        modelByteBuffer = ByteBuffer.allocateDirect(modelBytes.size)
        modelByteBuffer.order(ByteOrder.nativeOrder())
        modelByteBuffer.put(modelBytes)
        modelByteBuffer.rewind()

        // 3. Initialize the TensorFlow engine
        val options = Interpreter.Options().apply { numThreads = 4 }
        interpreter = Interpreter(modelByteBuffer, options)
    }

    /**
     * Master function: Records 20s of audio, processes it, and returns the final 8-class probabilities.
     */
    @SuppressLint("MissingPermission")
    suspend fun recordAndAnalyze(): FloatArray = withContext(Dispatchers.IO) {
        val totalSamples = SAMPLE_RATE * RECORD_TIME_SEC
        val audioData = ShortArray(totalSamples)

// 1. Record 20 seconds of raw PCM Audio
        val bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT)

        val audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            SAMPLE_RATE,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            bufferSize // FIX 1: Only ask the OS for its minimum safe buffer limit!
        )

        // FIX 2: Check if the hardware actually gave us the microphone
        if (audioRecord.state != AudioRecord.STATE_INITIALIZED) {
            throw Exception("Microphone hardware busy or rejected.")
        }

        audioRecord.startRecording()

        // FIX 3: Read the audio in small, safe chunks to prevent memory overload
        val tempBuffer = ShortArray(bufferSize)
        var read = 0
        while (read < totalSamples) {
            val toRead = minOf(tempBuffer.size, totalSamples - read)
            val result = audioRecord.read(tempBuffer, 0, toRead)

            if (result < 0) throw Exception("Hardware error during audio capture.")

            System.arraycopy(tempBuffer, 0, audioData, read, result)
            read += result
        }

        audioRecord.stop()
        audioRecord.release()

        // Convert ShortArray to FloatArray (-1.0 to 1.0)
        val floatAudio = FloatArray(totalSamples) { audioData[it] / 32768.0f }

        // 2. Split into 4 chunks of 5 seconds and run inference on each
        val finalProbabilities = FloatArray(8)
        val numChunks = RECORD_TIME_SEC / CHUNK_TIME_SEC

        for (i in 0 until numChunks) {
            val startIdx = i * SAMPLES_PER_CHUNK
            val chunk = floatAudio.sliceArray(startIdx until startIdx + SAMPLES_PER_CHUNK)

            // Generate the RGB Spectrogram Tensor
            val inputTensor = generateRGBMelSpectrogram(chunk)

            // Create a buffer to catch the TFLite output [1, 8]
            val outputTensor = Array(1) { FloatArray(8) }

            // Run the Neural Network!
            interpreter.run(inputTensor, outputTensor)

            // Accumulate probabilities
            for (j in 0 until 8) {
                finalProbabilities[j] += outputTensor[0][j]
            }
        }

        // 3. Average the results of the 4 chunks
        for (j in 0 until 8) {
            finalProbabilities[j] /= numChunks.toFloat()
        }

        return@withContext finalProbabilities
    }

    /**
     * Native Kotlin DSP Pipeline: Converts 1D audio to a 3D (128x157x3) matrix.
     */
    private fun generateRGBMelSpectrogram(audioChunk: FloatArray): ByteBuffer {
        // Allocate a ByteBuffer for the [1, 128, 157, 3] Float32 tensor
        val byteBuffer = ByteBuffer.allocateDirect(1 * N_MELS * TIME_STEPS * 3 * 4)
        byteBuffer.order(ByteOrder.nativeOrder())

        val fft = FloatFFT_1D(N_FFT.toLong())
        val window = FloatArray(N_FFT) { 0.5f * (1f - cos(2.0 * Math.PI * it / (N_FFT - 1))).toFloat() } // Hann Window

        val spectrogram = Array(N_MELS) { FloatArray(TIME_STEPS) }
        var maxLogPower = -Float.MAX_VALUE
        var minLogPower = Float.MAX_VALUE

        // STFT sliding window
        for (t in 0 until TIME_STEPS) {
            val start = t * HOP_LENGTH
            val fftBuffer = FloatArray(N_FFT * 2)

            // Apply window and copy to real parts of FFT buffer
            for (n in 0 until N_FFT) {
                if (start + n < audioChunk.size) {
                    fftBuffer[n * 2] = audioChunk[start + n] * window[n]
                }
            }

            fft.realForward(fftBuffer)

            // Calculate power spectrum and map to a highly simplified pseudo-Mel distribution
            for (m in 0 until N_MELS) {
                // In a production app, this uses a strict Mel filterbank matrix.
                // For this MVP, we map FFT bins to Mel bins.
                val binIdx = (m * (N_FFT / 2) / N_MELS) * 2
                val real = fftBuffer[binIdx]
                val imag = fftBuffer[binIdx + 1]
                val power = (real * real) + (imag * imag)

                // Convert to Log scale (Decibels)
                val logPower = 10f * log10(power + 1e-9f)
                spectrogram[m][t] = logPower

                if (logPower > maxLogPower) maxLogPower = logPower
                if (logPower < minLogPower) minLogPower = logPower
            }
        }

        // Normalize [0, 1] and stack into 3 channels (RGB)
        val range = maxLogPower - minLogPower
        for (m in 0 until N_MELS) {
            for (t in 0 until TIME_STEPS) {
                val normalized = if (range > 0) (spectrogram[m][t] - minLogPower) / range else 0f
                byteBuffer.putFloat(normalized) // Red
                byteBuffer.putFloat(normalized) // Green
                byteBuffer.putFloat(normalized) // Blue
            }
        }

        byteBuffer.rewind()
        return byteBuffer
    }
}