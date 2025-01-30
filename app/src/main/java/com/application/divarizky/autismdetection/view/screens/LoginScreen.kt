package com.application.divarizky.autismdetection.view.screens

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.application.divarizky.autismdetection.R
import com.application.divarizky.autismdetection.view.components.AppLogo
import com.application.divarizky.autismdetection.view.components.CustomButton
import com.application.divarizky.autismdetection.view.components.CustomTextField
import com.application.divarizky.autismdetection.view.theme.Dimens.appNameTextStyle
import com.application.divarizky.autismdetection.view.theme.Dimens.buttonCornerRadius
import com.application.divarizky.autismdetection.view.theme.Dimens.buttonHeight
import com.application.divarizky.autismdetection.view.theme.Dimens.buttonTextStyle
import com.application.divarizky.autismdetection.view.theme.Dimens.paddings
import com.application.divarizky.autismdetection.view.theme.Dimens.smallTextStyle
import com.application.divarizky.autismdetection.view.theme.MediumBlue
import com.application.divarizky.autismdetection.view.theme.NunitoSansFamily
import com.application.divarizky.autismdetection.view.theme.getDynamicColors
import com.application.divarizky.autismdetection.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    navController: NavController
) {
    val context = LocalContext.current

    val isLoading by viewModel.isLoading.observeAsState(false)

    // Memeriksa apakah pengguna sudah login
    if (viewModel.checkLoginStatus()) {
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        // Mengarahkan pengguna ke halaman utama jika sudah login sebelumnya
        LaunchedEffect(Unit) {
            viewModel.setLoading(true)
            navController.navigate("main") {
                popUpTo(0) // Menghapus seluruh stack hingga root
                launchSingleTop = true
            }
            viewModel.setLoading(false)
        }

    } else {
        val emailOrUsername by viewModel.emailOrUsername.observeAsState("")
        val password by viewModel.password.observeAsState("")
        val errorMessages by viewModel.errorMessages.observeAsState(emptyMap())
        val loginSuccess by viewModel.loginSuccess.observeAsState(false)
        val isPasswordVisible by viewModel.isPasswordVisible.observeAsState(false)

        // Mengarahkan pengguna ke halaman utama jika login sudah berhasil
        if (loginSuccess) {
            if (isLoading) {
                // Menampilkan indikator loading jika pengguna sedang login
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            LaunchedEffect(Unit) {
                viewModel.setLoading(true)
                navController.navigate("main")
                viewModel.setLoading(false)
            }
        } else {
            // Login screen content
            LoginScreenContent(
                emailOrUsername = emailOrUsername,
                onEmailOrUsernameChange = { viewModel.onEmailOrUsernameChange(it) },
                password = password,
                onPasswordChange = { viewModel.onPasswordChange(it) },
                isPasswordVisible = isPasswordVisible,
                onTogglePasswordVisibility = { viewModel.togglePasswordVisibility() },
                errorMessages = errorMessages,
                onLoginClick = {
                    viewModel.login(context)
                },
                isLoading = isLoading,
                scrollState = viewModel.scrollState,
                onScrollStateChange = { viewModel.updateScrollState(it) },
                onForgotPasswordClick = {
                    navController.navigate("forgot_password_screen")
                },
                onSignUpClick = {
                    navController.navigate("sign_up_screen")
                }
            )
        }
    }
}

@Composable
fun LoginSection(
    emailOrUsername: String,
    onEmailOrUsernameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    errorMessages: Map<LoginViewModel.Field, String?>,
    onLoginClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        // Email or Username TextField
        CustomTextField(
            value = emailOrUsername,
            onValueChange = onEmailOrUsernameChange,
            label = stringResource(R.string.email_and_username_hint),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        errorMessages[LoginViewModel.Field.EMAIL_OR_USERNAME]?.let { error ->
            Text(text = error, color = Red, style = smallTextStyle)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Password TextField dengan ikon toggle
        CustomTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = stringResource(R.string.password_hint),
            isPassword = !isPasswordVisible, // Toggle visibility
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = onTogglePasswordVisibility) {
                    Icon(
                        painter = painterResource(
                            id = if (isPasswordVisible) R.drawable.ic_visibility_off else R.drawable.ic_visibility
                        ),
                        contentDescription = stringResource(
                            id = if (isPasswordVisible) R.string.hide_password else R.string.show_password
                        )
                    )
                }
            }
        )
        errorMessages[LoginViewModel.Field.PASSWORD]?.let { error ->
            Text(text = error, color = Red, style = smallTextStyle)
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Login Button
        CustomButton(
            text = stringResource(R.string.login_button_text),
            onClick = onLoginClick,
            buttonCornerRadius = buttonCornerRadius,
            buttonHeight = buttonHeight,
            containerColor = MediumBlue,
            textStyle = TextStyle(
                color = White,
                fontFamily = NunitoSansFamily,
                fontSize = buttonTextStyle.fontSize,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun LoginScreenContent(
    emailOrUsername: String,
    onEmailOrUsernameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    errorMessages: Map<LoginViewModel.Field, String?>,
    isLoading: Boolean,
    scrollState: ScrollState,
    onScrollStateChange: (ScrollState) -> Unit,
    onLoginClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    // Fungsi yang digunakan agar aplikasi dapat menggulirkan layar ketika rotasi layar Landscape
    val scrollStateInternal = rememberScrollState(scrollState.value)
    onScrollStateChange(scrollStateInternal)

    // Mengambil warna dinamis sesuai dengan tema (OS 12+)
    val dynamicColors = getDynamicColors()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(paddings)
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollStateInternal)
            ) {
                Spacer(modifier = Modifier.height(60.dp))

                // Logo aplikasi
                AppLogo(
                    logoResourceId = R.drawable.ic_logo,
                    logoSize = 45.dp,
                    text = stringResource(R.string.app),
                    textStyle = appNameTextStyle,
                    textColor = MediumBlue,
                    spacing = 8.dp
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Bagian Login
                LoginSection(
                    emailOrUsername = emailOrUsername,
                    onEmailOrUsernameChange = onEmailOrUsernameChange,
                    password = password,
                    onPasswordChange = onPasswordChange,
                    isPasswordVisible = isPasswordVisible,
                    onTogglePasswordVisibility = onTogglePasswordVisibility,
                    errorMessages = errorMessages,
                    onLoginClick = onLoginClick
                )

                // "Forgot Password?" link
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.forgot_password),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = NunitoSansFamily,
                        fontWeight = FontWeight.Bold,
                        color = dynamicColors.forgotPasswordTextColor
                    ),
                    modifier = Modifier.clickable { onForgotPasswordClick() }
                )

                // Divider
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Divider(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp),
                        color = LightGray
                    )
                    Text(
                        text = "or",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = NunitoSansFamily,
                            color = LightGray
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    Divider(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp),
                        color = LightGray
                    )
                }

                // "Create an Account" button
                Spacer(modifier = Modifier.height(16.dp))

                // "Create an Account" button
                CustomButton(
                    text = stringResource(R.string.sign_up_button_text),
                    onClick = onSignUpClick,
                    buttonCornerRadius = buttonCornerRadius,
                    buttonHeight = buttonHeight,
                    containerColor = dynamicColors.buttonColor,
                    textStyle = TextStyle(
                        color = dynamicColors.textColor,
                        fontFamily = NunitoSansFamily,
                        fontSize = buttonTextStyle.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}
