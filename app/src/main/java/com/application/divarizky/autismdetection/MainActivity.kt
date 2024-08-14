package com.application.divarizky.autismdetection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.application.divarizky.autismdetection.navigation.NavRoutes
import com.application.divarizky.autismdetection.navigation.NavigationRoutes
import com.application.divarizky.autismdetection.navigation.RoutesViewModel
import com.application.divarizky.autismdetection.ui.theme.AutismDetectionTheme

class MainActivity : ComponentActivity() {
    private val routesViewModel: RoutesViewModel by viewModels()
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutismDetectionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    navController = rememberNavController()
                    NavRoutes(navController = navController, routesViewModel = routesViewModel)
                }
            }
        }
    }

    override fun onBackPressed() {
        val currentRoute = routesViewModel.currentRoute.value

        when {
            currentRoute == NavigationRoutes.Main.AutismDetection.route -> {
                routesViewModel.onNavigationItemSelected(NavigationRoutes.Main.Home.route)
                navController.navigate(NavigationRoutes.Main.Home.route) {
                    popUpTo(NavigationRoutes.Main.Home.route) { inclusive = true }
                }
            }
            currentRoute == NavigationRoutes.Main.Home.route -> {
                finishAffinity()
            }
            else -> {
                super.onBackPressed()
            }
        }
    }
}
