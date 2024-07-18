package com.application.divarizky.autismdetection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.application.divarizky.autismdetection.ui.screens.AutismDetectionScreen
import com.application.divarizky.autismdetection.ui.theme.AutismDetectionTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutismDetectionTheme {
                AutismDetectionScreen()
            }
        }
    }
}
