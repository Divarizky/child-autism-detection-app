package com.application.divarizky.autismdetection.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.application.divarizky.autismdetection.view.screens.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.application.divarizky.autismdetection.MyApp
import com.application.divarizky.autismdetection.utils.Validator
import com.application.divarizky.autismdetection.view.screens.AutismDetectionScreen
import com.application.divarizky.autismdetection.view.screens.HomeScreen
import com.application.divarizky.autismdetection.view.screens.LoginScreen
import com.application.divarizky.autismdetection.view.screens.SettingScreen
import com.application.divarizky.autismdetection.view.screens.SignUpScreen
import com.application.divarizky.autismdetection.viewmodel.AutismViewModel
import com.application.divarizky.autismdetection.viewmodel.BottomNavbarViewModel
import com.application.divarizky.autismdetection.viewmodel.LoginViewModel
import com.application.divarizky.autismdetection.viewmodel.SettingViewModel
import com.application.divarizky.autismdetection.viewmodel.SignUpViewModel

sealed class NavigationRoutes(val route: String) {
    data object SplashScreen : NavigationRoutes("splashscreen")
    data object Welcome : NavigationRoutes("welcome_screen")

    // Pengelompokkan navigasi pada Halaman Autentikasi
    data object Auth : NavigationRoutes("auth") {
        data object Login : NavigationRoutes("login_screen")
        data object SignUp : NavigationRoutes("signup_screen")
    }

    // Pengelompokkan navigasi pada Halaman Utama setelah berhasil Login
    data object Main : NavigationRoutes("main") {
        data object Home : NavigationRoutes("home_screen")
        data object AutismDetection : NavigationRoutes("autism_detection_screen")
        data object Settings : NavigationRoutes("settings_screen")
        data object About : NavigationRoutes("about_screen")
    }
}

@Composable
fun NavRoutes(
    navController: NavHostController = rememberNavController(),
    bottomNavbarViewModel: BottomNavbarViewModel = viewModel()
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

        // Nested Navigation untuk Halaman Autentikasi (Login & Sign Up)
        navigation(startDestination = NavigationRoutes.Auth.Login.route, route = NavigationRoutes.Auth.route) {
            composable(route = NavigationRoutes.Auth.Login.route) {
                val loginViewModel: LoginViewModel = viewModel(
                    factory = viewModelFactory { LoginViewModel(MyApp.appModule.userRepository, Validator) }
                )
                LoginScreen(viewModel = loginViewModel, navController = navController)
            }
            composable(route = NavigationRoutes.Auth.SignUp.route) {
                val signUpViewModel: SignUpViewModel = viewModel(
                    factory = viewModelFactory { SignUpViewModel(MyApp.appModule.userRepository, Validator) }
                )
                SignUpScreen(viewModel = signUpViewModel, navController = navController)
            }
        }

        // Nested Navigation untuk Halaman Utama
        navigation(startDestination = NavigationRoutes.Main.Home.route, route = NavigationRoutes.Main.route) {
            composable(route = NavigationRoutes.Main.Home.route) {
                HomeScreen(navController, bottomNavbarViewModel)
            }
            composable(route = NavigationRoutes.Main.AutismDetection.route) {
                val autismViewModel: AutismViewModel = viewModel(
                    factory = viewModelFactory { AutismViewModel(MyApp.appModule.userRepository, MyApp.appModule.context) }
                )
                AutismDetectionScreen(
                    viewModel = autismViewModel,
                    bottomNavbarViewModel = BottomNavbarViewModel(),
                    navController
                )
            }
            composable(route = NavigationRoutes.Main.Settings.route) {
                val settingViewModel: SettingViewModel = viewModel(
                    factory = viewModelFactory { SettingViewModel(MyApp.appModule.userRepository) }
                )
                SettingScreen(
                    viewModel = settingViewModel,
                    bottomNavbarViewModel = BottomNavbarViewModel(),
                    navController
                )
            }
            composable(route = NavigationRoutes.Main.About.route) {
                AboutScreen(navController)
            }
        }
    }
}
