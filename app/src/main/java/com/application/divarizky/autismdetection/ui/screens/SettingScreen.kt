package com.application.divarizky.autismdetection.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.application.divarizky.autismdetection.R
import com.application.divarizky.autismdetection.ui.components.BottomNavbar
import com.application.divarizky.autismdetection.ui.theme.Dimens
import com.application.divarizky.autismdetection.ui.theme.Dimens.buttonCornerRadius
import com.application.divarizky.autismdetection.ui.theme.Dimens.buttonHeight
import com.application.divarizky.autismdetection.ui.theme.Dimens.buttonTextStyle
import com.application.divarizky.autismdetection.ui.theme.Dimens.paddings
import com.application.divarizky.autismdetection.ui.theme.Dimens.regularTextStyle
import com.application.divarizky.autismdetection.ui.theme.MediumBlue
import com.application.divarizky.autismdetection.ui.theme.White
import com.application.divarizky.autismdetection.navigation.RoutesViewModel

@Composable
fun SettingScreen(
    navController: NavHostController,
    routesViewModel: RoutesViewModel = viewModel()
) {
    Scaffold(
        bottomBar = {
            BottomNavbar(navController, routesViewModel)
        }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(color = MediumBlue)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            SettingHeader()
            Spacer(modifier = Modifier.weight(1f))
            IllustrationImage()
            Spacer(modifier = Modifier.weight(1f))
            SettingsContent()
        }
    }
}

@Composable
fun SettingHeader() {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.paddings)
    ) {
        Text(
            text = stringResource(R.string.settings_title),
            style = Dimens.titleTextStyle,
            fontWeight = FontWeight.Bold,
            color = White
        )
    }
}

@Composable
fun IllustrationImage() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(Dimens.paddings)
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_settings),
            contentDescription = stringResource(R.string.settings_image_desc),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .width(Dimens.imageWidth)
                .height(Dimens.imageHeight)
        )
    }
}

@Composable
fun SettingsContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.White,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
            .padding(vertical = 16.dp)
    ) {
        TextField(
            value = "",
            onValueChange = {},
            label = { Text(stringResource(R.string.email_label)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )

        Spacer(modifier = Modifier.size(16.dp))

        TextField(
            value = "",
            onValueChange = {},
            label = { Text(stringResource(R.string.password_label)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            text = stringResource(R.string.about_us),
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold,
            style = regularTextStyle,
            color = MediumBlue,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Spacer(modifier = Modifier.size(16.dp))

        Button(
            onClick = { /* TODO: Handle Logout */ },
            shape = RoundedCornerShape(buttonCornerRadius),
            colors = ButtonDefaults.buttonColors(containerColor = MediumBlue),
            modifier = Modifier
                .fillMaxWidth()
                .height(buttonHeight)
                .padding(horizontal = paddings)
        ) {
            Text(
                text = stringResource(R.string.logout),
                style = buttonTextStyle,
            )
        }

        Spacer(modifier = Modifier.size(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun SettingScreenPreview() {
    SettingScreen(navController = rememberNavController())
}
