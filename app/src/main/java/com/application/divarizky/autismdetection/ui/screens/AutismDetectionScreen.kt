package com.application.divarizky.autismdetection.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.application.divarizky.autismdetection.R
import com.application.divarizky.autismdetection.ui.theme.AutismDetectionTheme
import com.application.divarizky.autismdetection.ui.theme.LocalResponsiveConfig
import com.application.divarizky.autismdetection.ui.theme.MediumBlue

@Composable
fun AutismDetectionScreen(navController: NavHostController) {
    val responsiveConfig = LocalResponsiveConfig.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(responsiveConfig.paddingStandard)
    ) {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.autism_detection_screen_title),
                textAlign = TextAlign.Center,
                fontSize = responsiveConfig.titleTextStyle
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 100.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_scan),
                contentDescription = stringResource(R.string.scan_image_description),
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(responsiveConfig.imageSize)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier
                .padding(bottom = responsiveConfig.verticalPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    // scan logic
                },
                shape = RoundedCornerShape(responsiveConfig.cornerRadius),
                colors = ButtonDefaults.buttonColors(containerColor = MediumBlue),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(responsiveConfig.buttonHeight)
            ) {
                Text(text = stringResource(R.string.take_a_picture_button_text))
            }
            Spacer(modifier = Modifier.height(responsiveConfig.verticalPadding))
            Button(
                onClick = {
                    // upload from gallery logic
                },
                shape = RoundedCornerShape(responsiveConfig.cornerRadius),
                colors = ButtonDefaults.buttonColors(containerColor = MediumBlue),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(responsiveConfig.buttonHeight)
            ) {
                Text(text = stringResource(R.string.upload_from_gallery_button_text))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AutismDetectionPreview() {
    val configuration = LocalConfiguration.current
    AutismDetectionTheme(screenWidth = configuration.screenWidthDp.dp) {
        AutismDetectionScreen(navController = rememberNavController())
    }
}
