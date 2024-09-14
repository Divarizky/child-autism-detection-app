package com.application.divarizky.autismdetection.ui.screens.login

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.application.divarizky.autismdetection.MyApp
import com.application.divarizky.autismdetection.R
import com.application.divarizky.autismdetection.ui.components.AppLogo
import com.application.divarizky.autismdetection.ui.components.CustomButton
import com.application.divarizky.autismdetection.ui.components.CustomTextField
import com.application.divarizky.autismdetection.ui.theme.*
import com.application.divarizky.autismdetection.ui.theme.Dimens.appNameTextStyle
import com.application.divarizky.autismdetection.ui.theme.Dimens.buttonCornerRadius
import com.application.divarizky.autismdetection.ui.theme.Dimens.buttonHeight
import com.application.divarizky.autismdetection.ui.theme.Dimens.buttonTextStyle
import com.application.divarizky.autismdetection.ui.theme.Dimens.largeTextStyle
import com.application.divarizky.autismdetection.ui.theme.Dimens.paddings
import com.application.divarizky.autismdetection.ui.theme.Dimens.regularTextStyle
import com.application.divarizky.autismdetection.ui.theme.Dimens.smallTextStyle
import com.application.divarizky.autismdetection.utils.Validator
import com.application.divarizky.autismdetection.utils.viewModelFactory

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel(factory = viewModelFactory {
        LoginViewModel(MyApp.appModule.userRepository, Validator)
    })
) {
    val context = LocalContext.current

    // State for showing the loading indicator
    val isLoading by viewModel.isLoading.observeAsState(false)

    // Check if the user is already logged in
    if (viewModel.checkLoginState()) {
        if (isLoading) {
            // Display loading indicator while navigating to home screen
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        // Start navigation after a slight delay to ensure the UI can show the loading indicator
        LaunchedEffect(Unit) {
            viewModel.setLoading(true)
            navController.navigate("home_screen") {
                popUpTo("login_screen") { inclusive = true }
            }
            viewModel.setLoading(false)
        }
    } else {
        val email by viewModel.email.observeAsState("")
        val password by viewModel.password.observeAsState("")
        val errorMessages by viewModel.errorMessages.observeAsState(emptyMap())
        val loginSuccess by viewModel.loginSuccess.observeAsState(false)

        // Navigate to home screen if login is successful
        if (loginSuccess!!) {
            if (isLoading) {
                // Display loading indicator while navigating to home screen
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            LaunchedEffect(Unit) {
                viewModel.setLoading(true)
                navController.navigate("home_screen") {
                    popUpTo("login_screen") { inclusive = true }
                }
                viewModel.setLoading(false) // Hide loading indicator
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
                    navController.navigate("signup_screen")
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
                Spacer(modifier = Modifier.height(8.dp))
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
            style = largeTextStyle,
            fontWeight = FontWeight.Bold,
            color = Black
        )

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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email) // Set keyboard type for email
        )
        errorMessages[LoginViewModel.Field.EMAIL]?.let { error ->
            Text(text = error, color = Red, style = smallTextStyle)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Password TextField with PasswordVisualTransformation
        CustomTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = "Password",
            isPassword = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password) // Set keyboard type for password
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
            textStyle = buttonTextStyle
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
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(6.dp))
        ClickableText(
            text = AnnotatedString(stringResource(id = R.string.sign_up)),
            onClick = { onSignUpClick() },
            style = TextStyle.Default.copy(
                fontWeight = FontWeight.Bold,
                fontFamily = NunitoSansFamily,
                color = MediumBlue
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreenContent(
        email = "test@example.com",
        onEmailChange = {},
        password = "password",
        onPasswordChange = {},
        errorMessages = emptyMap(),
        isLoading = false,
        scrollState = rememberScrollState(0),
        onScrollStateChange = {},
        onLoginClick = {},
        onSignUpClick = {}
    )
}
