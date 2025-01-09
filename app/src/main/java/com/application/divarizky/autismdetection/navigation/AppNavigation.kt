package com.application.divarizky.autismdetection.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.application.divarizky.autismdetection.MyApp
import com.application.divarizky.autismdetection.utils.Validator
import com.application.divarizky.autismdetection.view.screens.AboutScreen
import com.application.divarizky.autismdetection.view.screens.AutismDetectionScreen
import com.application.divarizky.autismdetection.view.screens.HomeScreen
import com.application.divarizky.autismdetection.view.screens.LoginScreen
import com.application.divarizky.autismdetection.view.screens.SettingScreen
import com.application.divarizky.autismdetection.view.screens.SignUpScreen
import com.application.divarizky.autismdetection.view.screens.SplashScreen
import com.application.divarizky.autismdetection.view.screens.WelcomeScreen
import com.application.divarizky.autismdetection.view.screens.viewModelFactory
import com.application.divarizky.autismdetection.viewmodel.AutismViewModel
import com.application.divarizky.autismdetection.viewmodel.BottomNavbarViewModel
import com.application.divarizky.autismdetection.viewmodel.LoginViewModel
import com.application.divarizky.autismdetection.viewmodel.SettingViewModel
import com.application.divarizky.autismdetection.viewmodel.SignUpViewModel

//sealed class NavigationRoutes(val route: String) {
//    data object SplashScreen : NavigationRoutes("splashscreen")
//    data object Welcome : NavigationRoutes("welcome_screen")
//
//    // Pengelompokkan navigasi pada Halaman Autentikasi
//    data object Auth : NavigationRoutes("auth") {
//        data object Login : NavigationRoutes("login_screen")
//        data object SignUp : NavigationRoutes("signup_screen")
//    }
//
//    // Pengelompokkan navigasi pada Halaman Utama setelah berhasil Login
//    data object Main : NavigationRoutes("main") {
//        data object Home : NavigationRoutes("home_screen")
//        data object AutismDetection : NavigationRoutes("autism_detection_screen")
//        data object Settings : NavigationRoutes("settings_screen")
//        data object About : NavigationRoutes("about_screen")
//    }
//}

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

//@Composable
//inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
//    navController: NavController,
//    noinline factory: () -> T
//): T {
//    val navGraphRoute = destination.parent?.route ?: return viewModel(factory = viewModelFactory(factory))
//    val parentEntry = remember(this) {
//        navController.getBackStackEntry(navGraphRoute)
//    }
//    return ViewModelProvider(parentEntry, viewModelFactory(factory))[T::class.java]
//}
