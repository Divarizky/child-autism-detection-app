package com.application.divarizky.autismdetection.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.application.divarizky.autismdetection.R
import com.application.divarizky.autismdetection.ui.theme.Dimens.buttonCornerRadius
import com.application.divarizky.autismdetection.ui.theme.Dimens.buttonTextStyle
import com.application.divarizky.autismdetection.ui.theme.Dimens.imageSize
import com.application.divarizky.autismdetection.ui.theme.Dimens.paddings
import com.application.divarizky.autismdetection.ui.theme.Dimens.regularTextStyle
import com.application.divarizky.autismdetection.ui.theme.Dimens.smallTextStyle
import com.application.divarizky.autismdetection.ui.theme.Dimens.titleTextStyle
import com.application.divarizky.autismdetection.ui.theme.MediumBlue

@Composable
fun AutismDetectionScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddings)
    ) {
        Header()
        ScanImage()
        Spacer(modifier = Modifier.weight(1f))
        ActionButtons()
    }
}

@Composable
fun Header() {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.autism_detection_screen_title),
            textAlign = TextAlign.Center,
            style = titleTextStyle,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ScanImage() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 100.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_scan),
            contentDescription = stringResource(R.string.scan_image_description),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(imageSize)
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            textAlign = TextAlign.Center,
            text = stringResource(R.string.autism_detection_screen_support_text),
            style = smallTextStyle
        )
    }
}

@Composable
fun ActionButtons() {
    Column(
        modifier = Modifier
            .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                // scan logic
            },
            shape = RoundedCornerShape(buttonCornerRadius),
            colors = ButtonDefaults.buttonColors(containerColor = MediumBlue),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = stringResource(R.string.take_a_picture_button_text),
                style = buttonTextStyle
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // upload from gallery logic
            },
            shape = RoundedCornerShape(buttonCornerRadius),
            colors = ButtonDefaults.buttonColors(containerColor = MediumBlue),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = stringResource(R.string.upload_from_gallery_button_text),
                style = buttonTextStyle
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AutismDetectionPreview() {
    val navController = rememberNavController()
    AutismDetectionScreen(navController = navController)
}
