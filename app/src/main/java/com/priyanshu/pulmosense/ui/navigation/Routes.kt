package com.priyanshu.pulmosense.ui.navigation

sealed class Routes(val route: String) {
    object Onboarding : Routes("onboarding")
    object Auth : Routes("auth")
    object Main : Routes("main")
    object Dashboard : Routes("dashboard")
    object Recording : Routes("recording")
    object Results : Routes("results")
    object Account : Routes("account")
    object Privacy : Routes("privacy")
}
