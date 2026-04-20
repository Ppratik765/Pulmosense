package com.priyanshu.pulmosense.ui.navigation

sealed class Routes(val route: String) {
    object Onboarding : Routes("onboarding")
    object Auth : Routes("auth")
    object Main : Routes("main")
    object Dashboard : Routes("dashboard")
    object Recording : Routes("recording")
    object Results {
        const val route = "results/{arrayString}"
        fun createRoute(arrayString: String) = "results/$arrayString"
    }
    object Account : Routes("account")
    object Privacy : Routes("privacy")
}
