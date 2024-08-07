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
import com.application.divarizky.autismdetection.ui.viewmodel.SharedViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

sealed class NavigationRoutes(val route: String) {
    object SplashScreen : NavigationRoutes("splashscreen")
    object Welcome : NavigationRoutes("welcome_screen")
    object Home : NavigationRoutes("home_screen")
    object AutismDetection : NavigationRoutes("autism_detection_screen")
}

@Composable
fun NavRoutes(
    navController: NavHostController = rememberNavController(),
    sharedViewModel: SharedViewModel = viewModel()
) {
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
            HomeScreen(navController, sharedViewModel)
        }
        composable(route = NavigationRoutes.AutismDetection.route) {
            AutismDetectionScreen(navController, sharedViewModel)
        }
    }
}
