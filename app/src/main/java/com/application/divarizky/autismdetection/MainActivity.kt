package com.application.divarizky.autismdetection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.application.divarizky.autismdetection.navigation.AppNavigation
import com.application.divarizky.autismdetection.view.theme.AutismDetectionTheme
import com.application.divarizky.autismdetection.viewmodel.BottomNavbarViewModel

class MainActivity : ComponentActivity() {
//    private val bottomNavbarViewModel: BottomNavbarViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutismDetectionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    navController = rememberNavController()
                    AppNavigation()
                }
            }
        }
    }

//    override fun onBackPressed() {
//        val currentRoute = bottomNavbarViewModel.currentRoute.value
//
//        when {
//            currentRoute == NavigationRoutes.Main.AutismDetection.route -> {
//                bottomNavbarViewModel.onNavigationItemSelected(NavigationRoutes.Main.Home.route)
//                navController.navigate(NavigationRoutes.Main.Home.route) {
//                    popUpTo(NavigationRoutes.Main.Home.route) { inclusive = true }
//                }
//            }
//            currentRoute == NavigationRoutes.Main.Home.route -> {
//                finishAffinity()
//            }
//            else -> {
//                super.onBackPressed()
//            }
//        }
//    }
}
