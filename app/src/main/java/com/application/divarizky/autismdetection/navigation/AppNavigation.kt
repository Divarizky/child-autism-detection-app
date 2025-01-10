package com.application.divarizky.autismdetection.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.application.divarizky.autismdetection.MyApp
import com.application.divarizky.autismdetection.utils.Validator
import com.application.divarizky.autismdetection.view.screens.AboutScreen
import com.application.divarizky.autismdetection.view.screens.AutismDetectionScreen
import com.application.divarizky.autismdetection.view.screens.ForgotPasswordScreen
import com.application.divarizky.autismdetection.view.screens.HomeScreen
import com.application.divarizky.autismdetection.view.screens.LoginScreen
import com.application.divarizky.autismdetection.view.screens.SettingScreen
import com.application.divarizky.autismdetection.view.screens.SignUpScreen
import com.application.divarizky.autismdetection.view.screens.SplashScreen
import com.application.divarizky.autismdetection.view.screens.WelcomeScreen
import com.application.divarizky.autismdetection.view.screens.viewModelFactory
import com.application.divarizky.autismdetection.viewmodel.AutismViewModel
import com.application.divarizky.autismdetection.viewmodel.BottomNavbarViewModel
import com.application.divarizky.autismdetection.viewmodel.ForgotPasswordViewModel
import com.application.divarizky.autismdetection.viewmodel.LoginViewModel
import com.application.divarizky.autismdetection.viewmodel.SettingViewModel
import com.application.divarizky.autismdetection.viewmodel.SignUpViewModel

@Composable
fun AppNavigation(
    bottomNavbarViewModel: BottomNavbarViewModel = viewModel(),
    loginViewModel: LoginViewModel = viewModel(
        factory = viewModelFactory {
            LoginViewModel(MyApp.appModule.userRepository, Validator)
        }
    )
) {
    val navController = rememberNavController()

    // Mengecek status login sebelum menentukan navigasi awal
    LaunchedEffect(Unit) {
        loginViewModel.checkLoginStatus()
    }

    NavHost(navController = navController, startDestination = "splash_screen") {
        composable("splash_screen") {
            SplashScreen { isLoggedIn ->
                if (isLoggedIn) {
                    navController.navigate("main") {
                        popUpTo(0)
                        launchSingleTop = true
                    }
                } else {
                    navController.navigate("auth") {
                        popUpTo(0)
                        launchSingleTop = true
                    }
                }
            }
        }
        // Navigasi halaman otentikasi
        navigation(
            startDestination = "welcome_screen",
            route = "auth"
        ) {
            composable("welcome_screen") {
                WelcomeScreen(navController)
            }
            composable("login_screen") {
                val loginViewModel: LoginViewModel = viewModel(
                    factory = viewModelFactory {
                        LoginViewModel(MyApp.appModule.userRepository, Validator)
                    }
                )
                LoginScreen(loginViewModel, navController)
            }
            composable("forgot_password_screen") {
                val forgotPasswordViewModel: ForgotPasswordViewModel = viewModel(
                    factory = viewModelFactory {
                        ForgotPasswordViewModel(MyApp.appModule.userRepository, Validator)
                    }
                )
                ForgotPasswordScreen(forgotPasswordViewModel, navController)
            }
            composable("sign_up_screen") {
                val signUpViewModel: SignUpViewModel = viewModel(
                    factory = viewModelFactory {
                        SignUpViewModel(MyApp.appModule.userRepository, Validator)
                    }
                )
                SignUpScreen(signUpViewModel, navController)
            }
        }

        // Navigasi halaman utama
        navigation(
            startDestination = "home_screen",
            route = "main"
        ) {
            composable("home_screen") {
                HomeScreen(navController)
            }
            composable("detection_screen") {
                val autismViewModel: AutismViewModel = viewModel(
                    factory = viewModelFactory {
                        AutismViewModel(MyApp.appModule.userRepository, MyApp.appModule.context)
                    }
                )
                AutismDetectionScreen(autismViewModel, bottomNavbarViewModel, navController)
            }
            composable("settings_screen") {
                val settingViewModel: SettingViewModel = viewModel(
                    factory = viewModelFactory {
                        SettingViewModel(MyApp.appModule.userRepository)
                    }
                )
                SettingScreen(settingViewModel, bottomNavbarViewModel, navController)
            }
            composable("about_screen") {
                AboutScreen(navController)
            }
        }
    }

    LaunchedEffect(navController.currentBackStackEntry) {
        val currentRoute = navController.currentBackStackEntry?.destination?.route
        if (currentRoute != null) {
            bottomNavbarViewModel.onNavigationItemSelected(currentRoute)
        }
    }
}
