package com.application.divarizky.autismdetection.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.application.divarizky.autismdetection.R
import com.application.divarizky.autismdetection.view.theme.Dimens.appNameTextStyle
import com.application.divarizky.autismdetection.view.theme.MediumBlue
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2500) // Delay configuration
        onSplashFinished()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MediumBlue),
        contentAlignment = Alignment.Center
    ) {
        // Layer 1: Background Image
        Image(
            painter = painterResource(id = R.drawable.img_splashscreen),
            contentDescription = null,
            modifier = Modifier
                .graphicsLayer { alpha = 0.3f },
            contentScale = ContentScale.Fit
        )

        // Layer 2: Logo Apps and Text
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_white_logo),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier.size(85.dp)
            )
            Text(
                text = stringResource(R.string.app),
                style = appNameTextStyle,
                color = White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(onSplashFinished = {})
}
