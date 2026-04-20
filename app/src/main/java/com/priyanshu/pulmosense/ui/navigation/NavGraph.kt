package com.priyanshu.pulmosense.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.priyanshu.pulmosense.ui.screens.auth.AuthScreen
import com.priyanshu.pulmosense.ui.screens.auth.MockAuthViewModel
import com.priyanshu.pulmosense.ui.screens.main.AccountScreen
import com.priyanshu.pulmosense.ui.screens.main.MainScreen
import com.priyanshu.pulmosense.ui.screens.main.PrivacyScreen
import com.priyanshu.pulmosense.ui.screens.main.RecordingScreen
import com.priyanshu.pulmosense.ui.screens.main.ResultsScreen
import com.priyanshu.pulmosense.ui.screens.onboarding.OnboardingScreen

@Composable
fun PulmoSenseNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.Onboarding.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { fadeIn(animationSpec = tween(500)) },
        exitTransition = { fadeOut(animationSpec = tween(500)) }
    ) {
        composable(Routes.Onboarding.route) {
            OnboardingScreen(onGetStarted = {
                navController.navigate(Routes.Auth.route) {
                    popUpTo(Routes.Onboarding.route) { inclusive = true }
                }
            })
        }
        composable(Routes.Auth.route) {
            val authViewModel: MockAuthViewModel = viewModel()
            AuthScreen(
                viewModel = authViewModel,
                onLoginSuccess = {
                    navController.navigate(Routes.Main.route) {
                        popUpTo(Routes.Auth.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.Main.route) {
            MainScreen(
                onNavigateToRecording = {
                    navController.navigate(Routes.Recording.route)
                },
                onNavigateToAccount = {
                    navController.navigate(Routes.Account.route)
                }
            )
        }
        composable(Routes.Recording.route) {
// 1. Where you define the RecordingScreen:
            RecordingScreen(
                onRecordingComplete = { finalOutput ->
                    // Convert the FloatArray [0.1, 0.2, 0.7...] into a String "0.1,0.2,0.7..."
                    val arrayString = finalOutput?.joinToString(",") ?: ""
                    navController.navigate(Routes.Results.createRoute(arrayString))
                }
            )
        }

            // 2. Where you define the ResultsScreen:
            composable(route = Routes.Results.route) { backStackEntry ->
                // Extract the string from the route
                val arrayString = backStackEntry.arguments?.getString("arrayString") ?: ""

                // Convert it back into a FloatArray for the UI!
                val modelOutput = if (arrayString.isNotEmpty()) {
                    arrayString.split(",").map { it.toFloat() }.toFloatArray()
                } else {
                    null
                }

                ResultsScreen(
                    modelOutput = modelOutput,
                    onFinish = {
                        // Your brilliant, clean fix!
                        // Pops everything off the stack until it lands safely back on the Main screen.
                        navController.popBackStack(Routes.Main.route, false)
                    }
                )
            }
        composable(Routes.Account.route) {
            AccountScreen(
                onBack = { navController.popBackStack() },
                onNavigateToPrivacy = { navController.navigate(Routes.Privacy.route) },
                onSignOut = {
                    navController.navigate(Routes.Auth.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.Privacy.route) {
            PrivacyScreen(onBack = { navController.popBackStack() })
        }
    }
}
