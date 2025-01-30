package com.application.divarizky.autismdetection.view.screens

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import com.application.divarizky.autismdetection.view.theme.Dimens.regularTextStyle
import com.application.divarizky.autismdetection.view.theme.Dimens.smallTextStyle
import com.application.divarizky.autismdetection.view.theme.Dimens.titleTextStyle
import com.application.divarizky.autismdetection.view.theme.MediumBlue
import com.application.divarizky.autismdetection.view.theme.NunitoSansFamily
import com.application.divarizky.autismdetection.view.theme.White
import com.application.divarizky.autismdetection.viewmodel.SignUpViewModel

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel,
    navController: NavController
) {
    val username by viewModel.username.observeAsState("")
    val email by viewModel.email.observeAsState("")
    val password by viewModel.password.observeAsState("")
    val errorMessages by viewModel.errorMessages.observeAsState(emptyMap())
    val isPasswordVisible by viewModel.isPasswordVisible.observeAsState(false)

    val onSignUpSuccess: () -> Unit = {
        viewModel.validateAndSignUp()
        if (errorMessages.isEmpty()) {
            navController.navigate("login_screen")
        }
    }

    SignUpScreenContent(
        username = username,
        onUsernameChange = { viewModel.updateUsername(it) },
        email = email,
        onEmailChange = { viewModel.updateEmail(it) },
        password = password,
        onPasswordChange = { viewModel.updatePassword(it) },
        isPasswordVisible = isPasswordVisible,
        onTogglePasswordVisibility = { viewModel.togglePasswordVisibility() },
        errorMessages = errorMessages,
        scrollState = viewModel.scrollState,
        onScrollStateChange = { viewModel.updateScrollState(it) },
        onSignUpSuccess = onSignUpSuccess
    )
}

@Composable
fun SignUpScreenContent(
    username: String,
    onUsernameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    errorMessages: Map<SignUpViewModel.Field, String?>,
    scrollState: ScrollState,
    onScrollStateChange: (ScrollState) -> Unit,
    onSignUpSuccess: () -> Unit
) {
    val scrollStateInternal = rememberScrollState(scrollState.value)
    onScrollStateChange(scrollStateInternal)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(paddings)
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

        SignUpSection(
            username = username,
            onUsernameChange = onUsernameChange,
            email = email,
            onEmailChange = onEmailChange,
            password = password,
            onPasswordChange = onPasswordChange,
            isPasswordVisible = isPasswordVisible,
            onTogglePasswordVisibility = onTogglePasswordVisibility,
            errorMessages = errorMessages,
            onSignUpSuccess = onSignUpSuccess
        )
    }
}

@Composable
fun SignUpSection(
    username: String,
    onUsernameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    errorMessages: Map<SignUpViewModel.Field, String?>,
    onSignUpSuccess: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.sign_up_title),
            style = titleTextStyle,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = stringResource(R.string.sign_up_subtitle),
            style = regularTextStyle,
            fontWeight = FontWeight.Bold,
            color = LightGray
        )

        Spacer(modifier = Modifier.height(paddings))

        // Username TextField
        CustomTextField(
            value = username,
            onValueChange = onUsernameChange,
            label = stringResource(R.string.username_hint),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        errorMessages[SignUpViewModel.Field.USERNAME]?.let { error ->
            Text(text = error, color = Red, style = smallTextStyle)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Email TextField
        CustomTextField(
            value = email,
            onValueChange = onEmailChange,
            label = stringResource(R.string.email_hint),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        errorMessages[SignUpViewModel.Field.EMAIL]?.let { error ->
            Text(text = error, color = Red, style = smallTextStyle)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Password TextField with visibility toggle
        CustomTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = stringResource(R.string.password_hint),
            isPassword = !isPasswordVisible,
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
        errorMessages[SignUpViewModel.Field.PASSWORD]?.let { error ->
            Text(text = error, color = Red, style = smallTextStyle)
        }

        Spacer(modifier = Modifier.height(40.dp))

        CustomButton(
            text = stringResource(R.string.button_text),
            onClick = onSignUpSuccess,
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

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreenContent(
        username = "",
        onUsernameChange = {},
        email = "",
        onEmailChange = {},
        password = "",
        onPasswordChange = {},
        isPasswordVisible = false,
        onTogglePasswordVisibility = {},
        errorMessages = emptyMap(),
        scrollState = rememberScrollState(0),
        onScrollStateChange = {},
        onSignUpSuccess = {}
    )
}
