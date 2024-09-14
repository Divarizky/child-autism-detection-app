package com.application.divarizky.autismdetection.ui.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.application.divarizky.autismdetection.MyApp
import com.application.divarizky.autismdetection.R
import com.application.divarizky.autismdetection.data.model.User
import com.application.divarizky.autismdetection.data.repository.UserRepository
import com.application.divarizky.autismdetection.navigation.RoutesViewModel
import com.application.divarizky.autismdetection.ui.components.BottomNavbar
import com.application.divarizky.autismdetection.ui.components.CustomTextField
import com.application.divarizky.autismdetection.ui.theme.Dimens
import com.application.divarizky.autismdetection.ui.theme.Dimens.buttonCornerRadius
import com.application.divarizky.autismdetection.ui.theme.Dimens.buttonHeight
import com.application.divarizky.autismdetection.ui.theme.Dimens.buttonTextStyle
import com.application.divarizky.autismdetection.ui.theme.Dimens.cornerRadius
import com.application.divarizky.autismdetection.ui.theme.Dimens.height
import com.application.divarizky.autismdetection.ui.theme.Dimens.mediumTextStyle
import com.application.divarizky.autismdetection.ui.theme.Dimens.paddings
import com.application.divarizky.autismdetection.ui.theme.Dimens.regularTextStyle
import com.application.divarizky.autismdetection.ui.theme.LightGrey
import com.application.divarizky.autismdetection.ui.theme.MediumBlue
import com.application.divarizky.autismdetection.ui.theme.White
import com.application.divarizky.autismdetection.utils.viewModelFactory

@Composable
fun SettingScreen(
    navController: NavHostController,
    routesViewModel: RoutesViewModel = viewModel(),
    settingViewModel: SettingViewModel = viewModel(factory = viewModelFactory {
        SettingViewModel(MyApp.appModule.userRepository)
    })
) {
    val user by settingViewModel.user.observeAsState()

    // Get the ID of the currently logged in user
    val currentUserId = 1

    LaunchedEffect(Unit) {
        settingViewModel.loadUser(currentUserId)
    }

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
            Spacer(modifier = Modifier.height(paddings))
            IllustrationImage()
            Spacer(modifier = Modifier.height(paddings))
            SettingsContent(user, settingViewModel, navController)
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
    navController: NavHostController
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                Color.White,
                shape = RoundedCornerShape(topStart = paddings, topEnd = paddings)
            )
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

        // Display email
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = paddings)
                .border(
                    width = 1.dp,
                    color = LightGrey,
                    shape = RoundedCornerShape(buttonCornerRadius)
                )
                .padding(paddings) // Padding inside the border
        ) {
            Text(
                text = user?.email.orEmpty(),
                style = regularTextStyle,
                color = Color.Black
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

        // Display password
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = paddings)
                .border(
                    width = 1.dp,
                    color = LightGrey,
                    shape = RoundedCornerShape(buttonCornerRadius)
                )
                .padding(paddings) // Padding inside the border
        ) {
            Text(
                text = user?.password?.let { maskPassword(it) }.orEmpty(),
                style = regularTextStyle,
                color = Color.Black
            )
        }

        Text(
            text = stringResource(R.string.about_us),
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold,
            style = regularTextStyle,
            color = MediumBlue,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = paddings, vertical = paddings)
        )

        Spacer(modifier = Modifier.height(height))

        Button(
            onClick = {
                settingViewModel.logout {
                    navController.navigate("login_screen") {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
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
                text = stringResource(R.string.logout),
                style = buttonTextStyle,
            )
        }
    }
}

// Function to mask the password with asterisks
fun maskPassword(password: String): String {
    return "*".repeat(password.length)
}

@Preview(showBackground = true)
@Composable
fun SettingScreenPreview() {
    // Implementasi UserRepository mock untuk preview
    val previewViewModel =
        viewModel<SettingViewModel>(factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
                    return SettingViewModel(object : UserRepository {
                        override suspend fun insertUser(user: User) {
                            // Mock implementation
                        }

                        override suspend fun login(email: String, password: String): Boolean {
                            return true // Mock login success
                        }

                        override suspend fun getUserById(userId: Int): User {
                            return User(
                                username = "user",
                                email = "user@example.com",
                                password = "password123"
                            )
                        }

                        override fun saveLoginState(isLoggedIn: Boolean) {
                            // Mock implementation
                        }

                        override fun isLoggedIn(): Boolean {
                            return true // Mock logged in state
                        }

                        override suspend fun logout() {
                            // Mock implementation
                        }

                        suspend fun getUserByEmail(email: String): User? {
                            return User(username = "user", email = email, password = "password123")
                        }

                        suspend fun getLoggedInUser(): User? {
                            return User(
                                username = "user",
                                email = "user@example.com",
                                password = "password123"
                            )
                        }
                    }) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        })

    SettingScreen(
        navController = rememberNavController(),
        routesViewModel = RoutesViewModel(),
        settingViewModel = previewViewModel
    )
}
