package com.application.divarizky.autismdetection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.application.divarizky.autismdetection.navigation.NavRoutes
import com.application.divarizky.autismdetection.navigation.NavigationRoutes
import com.application.divarizky.autismdetection.ui.theme.AutismDetectionTheme
import com.application.divarizky.autismdetection.ui.viewmodel.SharedViewModel

class MainActivity : ComponentActivity() {
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutismDetectionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavRoutes(navController = navController, sharedViewModel = sharedViewModel)
                }
            }
        }
    }

    override fun onBackPressed() {
        if (sharedViewModel.currentRoute.value == NavigationRoutes.AutismDetection.route) {
            sharedViewModel.onNavigationItemSelected(NavigationRoutes.Home.route)
        }
        super.onBackPressed()
    }
}
