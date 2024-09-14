package com.application.divarizky.autismdetection.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.application.divarizky.autismdetection.ui.screens.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.application.divarizky.autismdetection.ui.screens.autismdetection.AutismDetectionScreen
import com.application.divarizky.autismdetection.ui.screens.home.HomeScreen
import com.application.divarizky.autismdetection.ui.screens.login.LoginScreen
import com.application.divarizky.autismdetection.ui.screens.settings.SettingScreen
import com.application.divarizky.autismdetection.ui.screens.signup.SignUpScreen

sealed class NavigationRoutes(val route: String) {
    data object SplashScreen : NavigationRoutes("splashscreen")
    data object Welcome : NavigationRoutes("welcome_screen")

    // Grouping authentication-related screens
    data object Auth : NavigationRoutes("auth") {
        data object Login : NavigationRoutes("login_screen")
        data object SignUp : NavigationRoutes("signup_screen")
    }

    // Grouping main screens after login
    data object Main : NavigationRoutes("main") {
        data object Home : NavigationRoutes("home_screen")
        data object AutismDetection : NavigationRoutes("autism_detection_screen")
        data object Settings : NavigationRoutes("settings_screen")
    }
}

@Composable
fun NavRoutes(
    navController: NavHostController = rememberNavController(),
    routesViewModel: RoutesViewModel = viewModel()
) {
    NavHost(navController = navController, startDestination = NavigationRoutes.SplashScreen.route) {

        // Splash Screen Navigation
        composable(route = NavigationRoutes.SplashScreen.route) {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(NavigationRoutes.Welcome.route) {
                        popUpTo(NavigationRoutes.SplashScreen.route) { inclusive = true }
                    }
                }
            )
        }

        // Welcome Screen Navigation
        composable(route = NavigationRoutes.Welcome.route) {
            WelcomeScreen(navController)
        }

        // Authentication Nested Navigation
        navigation(startDestination = NavigationRoutes.Auth.Login.route, route = NavigationRoutes.Auth.route) {
            composable(route = NavigationRoutes.Auth.Login.route) {
                LoginScreen(navController)
            }
            composable(route = NavigationRoutes.Auth.SignUp.route) {
                SignUpScreen(navController)
            }
        }

        // Main Screens Nested Navigation
        navigation(startDestination = NavigationRoutes.Main.Home.route, route = NavigationRoutes.Main.route) {
            composable(route = NavigationRoutes.Main.Home.route) {
                HomeScreen(navController, routesViewModel)
            }
            composable(route = NavigationRoutes.Main.AutismDetection.route) {
                AutismDetectionScreen(navController, routesViewModel)
            }
            composable(route = NavigationRoutes.Main.Settings.route) {
                SettingScreen(navController, routesViewModel)
            }
        }
    }
}
