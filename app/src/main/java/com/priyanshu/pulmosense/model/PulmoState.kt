package com.priyanshu.pulmosense.model

enum class ScreeningMode {
    Idle,
    ActiveBreathing,
    Analyzing,
    ResultReady
}

enum class ConditionType {
    Normal,
    Wheeze,
    Crackle,
    Unknown
}

data class ScreeningResult(
    val id: String,
    val score: Int,
    val condition: ConditionType,
    val timestamp: Long
)

data class PulmoState(
    val isPassiveSleepMonitorEnabled: Boolean = false,
    val recentScreenings: List<ScreeningResult>? = null, // null means loading
    val currentScreeningState: ScreeningMode = ScreeningMode.Idle,
    val lungHealthScore: Int = 0,
    val detectedFeatures: List<ConditionType> = emptyList()
)
