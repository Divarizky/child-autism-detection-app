package com.application.divarizky.autismdetection.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.application.divarizky.autismdetection.R
import com.application.divarizky.autismdetection.data.model.User
import com.application.divarizky.autismdetection.navigation.BottomNavigationBar
import com.application.divarizky.autismdetection.view.theme.Dimens
import com.application.divarizky.autismdetection.view.theme.Dimens.buttonCornerRadius
import com.application.divarizky.autismdetection.view.theme.Dimens.buttonHeight
import com.application.divarizky.autismdetection.view.theme.Dimens.buttonTextStyle
import com.application.divarizky.autismdetection.view.theme.Dimens.height
import com.application.divarizky.autismdetection.view.theme.Dimens.paddings
import com.application.divarizky.autismdetection.view.theme.Dimens.regularTextStyle
import com.application.divarizky.autismdetection.view.theme.LightGrey
import com.application.divarizky.autismdetection.view.theme.MediumBlue
import com.application.divarizky.autismdetection.view.theme.NunitoSansFamily
import com.application.divarizky.autismdetection.view.theme.White
import com.application.divarizky.autismdetection.viewmodel.BottomNavbarViewModel
import com.application.divarizky.autismdetection.viewmodel.SettingViewModel

@Composable
fun SettingScreen(
    viewModel: SettingViewModel,
    bottomNavbarViewModel: BottomNavbarViewModel,
    navController: NavController
) {
    val user by viewModel.user.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.loadUser(1)
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController, bottomNavbarViewModel)
        }
    ) { innerPadding ->
        val scrollStateInternal = rememberScrollState(viewModel.scrollState.value)
        viewModel.updateScrollState(scrollStateInternal)

        Box(
            modifier = Modifier
                .fillMaxSize() // Memastikan seluruh layar digunakan
                .background(color = MediumBlue)
                .padding(innerPadding)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollStateInternal)
            ) {
                SettingHeader()
                Spacer(modifier = Modifier.height(paddings))
                IllustrationImage()
                Spacer(modifier = Modifier.height(paddings))
                SettingsContent(
                    user = user,
                    settingViewModel = viewModel,
                    navController = navController,
                    onAboutClick = { navController.navigate("about_screen") }
                )
            }
        }
    }
}

@Composable
fun SettingHeader() {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddings)
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
        modifier = Modifier.padding(paddings)
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
fun SettingsContent(
    user: User?,
    settingViewModel: SettingViewModel,
    navController: NavController,
    onAboutClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(topStart = paddings, topEnd = paddings)
            )
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(bottom = 100.dp)
    ) {
        Spacer(modifier = Modifier.height(36.dp))

        Text(
            text = stringResource(R.string.email_label),
            textAlign = TextAlign.Left,
            style = regularTextStyle,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = paddings)
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Email section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = paddings)
                .border(
                    width = 1.dp,
                    color = LightGrey,
                    shape = RoundedCornerShape(buttonCornerRadius)
                )
                .padding(paddings)
        ) {
            Text(
                text = user?.email.orEmpty(),
                style = regularTextStyle,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(paddings))

        Text(
            text = stringResource(R.string.password_label),
            textAlign = TextAlign.Left,
            style = regularTextStyle,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = paddings)
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Password section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = paddings)
                .border(
                    width = 1.dp,
                    color = LightGrey,
                    shape = RoundedCornerShape(buttonCornerRadius)
                )
                .padding(paddings)
        ) {
            Text(
                text = user?.password?.let { maskPassword(it) }.orEmpty(),
                style = regularTextStyle,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Text(
            modifier = Modifier
                .align(Alignment.Start)
                .clickable { onAboutClick() }
                .padding(horizontal = paddings, vertical = paddings),
            text = stringResource(id = R.string.about_us),
            style = TextStyle(
                fontFamily = NunitoSansFamily,
                fontSize = regularTextStyle.fontSize,
                color = MediumBlue,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(height))

        Button(
            onClick = {
                settingViewModel.logout {
                    navController.navigate("login_screen") {
                        popUpTo(0) { inclusive = false }
                    }
                }
            },
            shape = RoundedCornerShape(buttonCornerRadius),
            colors = ButtonDefaults.buttonColors(containerColor = MediumBlue),
            modifier = Modifier
                .fillMaxWidth()
                .height(buttonHeight)
                .padding(horizontal = paddings)
        ) {
            Text(
                text = stringResource(R.string.log_out),
                style = buttonTextStyle,
                color = White
            )
        }
    }
}

// Fungsi untuk mengubah password menjadi asterik "*"
fun maskPassword(password: String): String {
    return "*".repeat(password.length)
}
