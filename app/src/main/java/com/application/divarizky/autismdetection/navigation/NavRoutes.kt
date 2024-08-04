package com.application.divarizky.autismdetection.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.application.divarizky.autismdetection.ui.screens.AutismDetectionScreen
import com.application.divarizky.autismdetection.ui.screens.HomeScreen
import com.application.divarizky.autismdetection.ui.screens.SplashScreen
import com.application.divarizky.autismdetection.ui.screens.WelcomeScreen

sealed class NavigationRoutes(val route: String) {
    data object SplashScreen : NavigationRoutes("splashscreen")
    data object Welcome : NavigationRoutes("welcome_screen")
    data object Home : NavigationRoutes("home_screen")
    data object AutismDetection : NavigationRoutes("autism_detection_screen")
}

@Composable
fun NavRoutes(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = NavigationRoutes.SplashScreen.route) {
        composable(route = NavigationRoutes.SplashScreen.route) {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(NavigationRoutes.Welcome.route) {
                        popUpTo(NavigationRoutes.SplashScreen.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = NavigationRoutes.Welcome.route) {
            WelcomeScreen(navController)
        }
        composable(route = NavigationRoutes.Home.route) {
            HomeScreen(navController)
        }
        composable(route = NavigationRoutes.AutismDetection.route) {
            AutismDetectionScreen(navController)
        }
    }
}