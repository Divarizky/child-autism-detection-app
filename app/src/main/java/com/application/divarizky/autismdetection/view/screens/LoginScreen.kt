package com.application.divarizky.autismdetection.view.screens

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.application.divarizky.autismdetection.R
import com.application.divarizky.autismdetection.view.components.AppLogo
import com.application.divarizky.autismdetection.view.components.CustomButton
import com.application.divarizky.autismdetection.view.components.CustomTextField
import com.application.divarizky.autismdetection.view.theme.Black
import com.application.divarizky.autismdetection.view.theme.Dimens.appNameTextStyle
import com.application.divarizky.autismdetection.view.theme.Dimens.buttonCornerRadius
import com.application.divarizky.autismdetection.view.theme.Dimens.buttonHeight
import com.application.divarizky.autismdetection.view.theme.Dimens.buttonTextStyle
import com.application.divarizky.autismdetection.view.theme.Dimens.largeTextStyle
import com.application.divarizky.autismdetection.view.theme.Dimens.paddings
import com.application.divarizky.autismdetection.view.theme.Dimens.regularTextStyle
import com.application.divarizky.autismdetection.view.theme.Dimens.smallTextStyle
import com.application.divarizky.autismdetection.view.theme.Dimens.titleTextStyle
import com.application.divarizky.autismdetection.view.theme.MediumBlue
import com.application.divarizky.autismdetection.view.theme.NunitoSansFamily
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
        val email by viewModel.email.observeAsState("")
        val password by viewModel.password.observeAsState("")
        val errorMessages by viewModel.errorMessages.observeAsState(emptyMap())
        val loginSuccess by viewModel.loginSuccess.observeAsState(false)

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
                email = email,
                onEmailChange = { viewModel.onEmailChange(it) },
                password = password,
                onPasswordChange = { viewModel.onPasswordChange(it) },
                errorMessages = errorMessages,
                onLoginClick = {
                    viewModel.login(context)
                },
                isLoading = isLoading,
                scrollState = viewModel.scrollState,
                onScrollStateChange = { viewModel.updateScrollState(it) },
                onSignUpClick = {
                    navController.navigate("sign_up_screen")
                }
            )
        }
    }
}

@Composable
fun LoginScreenContent(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    errorMessages: Map<LoginViewModel.Field, String?>,
    isLoading: Boolean,
    scrollState: ScrollState,
    onScrollStateChange: (ScrollState) -> Unit,
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    val scrollStateInternal = rememberScrollState(scrollState.value)
    onScrollStateChange(scrollStateInternal)

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

                AppLogo(
                    logoResourceId = R.drawable.ic_logo,
                    logoSize = 45.dp,
                    text = stringResource(R.string.app),
                    textStyle = appNameTextStyle,
                    textColor = MediumBlue,
                    spacing = 8.dp
                )

                Spacer(modifier = Modifier.height(40.dp))

                LoginSection(
                    email = email,
                    onEmailChange = onEmailChange,
                    password = password,
                    onPasswordChange = onPasswordChange,
                    errorMessages = errorMessages,
                    onLoginClick = onLoginClick
                )
                Spacer(modifier = Modifier.height(16.dp))
                SignUpSection(onSignUpClick = onSignUpClick)
            }
        }
    }
}

@Composable
fun LoginSection(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    errorMessages: Map<LoginViewModel.Field, String?>,
    onLoginClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        // Sign in title text
        Text(
            text = stringResource(R.string.login_title),
            style = titleTextStyle,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Sign in subtitle text
        Text(
            text = stringResource(R.string.login_subtitle),
            style = regularTextStyle,
            fontWeight = FontWeight.Bold,
            color = LightGray
        )

        Spacer(modifier = Modifier.height(paddings))

        // Email TextField
        CustomTextField(
            value = email,
            onValueChange = onEmailChange,
            label = "Email",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        errorMessages[LoginViewModel.Field.EMAIL]?.let { error ->
            Text(text = error, color = Red, style = smallTextStyle)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Password TextField dengan menerapkan PasswordVisualTransformation
        CustomTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = "Password",
            isPassword = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
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
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = NunitoSansFamily,
                fontSize = buttonTextStyle.fontSize,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun SignUpSection(onSignUpClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = stringResource(id = R.string.create_account),
            style = regularTextStyle,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            modifier = Modifier
                .clickable { onSignUpClick() },
            text = stringResource(id = R.string.sign_up),
            style = TextStyle(
                fontFamily = NunitoSansFamily,
                fontSize = regularTextStyle.fontSize,
                color = MediumBlue,
                fontWeight = FontWeight.Bold
            )
        )
    }
}
