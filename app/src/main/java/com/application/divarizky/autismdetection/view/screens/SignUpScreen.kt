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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import com.application.divarizky.autismdetection.MyApp
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
import com.application.divarizky.autismdetection.view.theme.MediumBlue
import com.application.divarizky.autismdetection.utils.Validator
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

    val onSignUpSuccess: () -> Unit = {
        viewModel.validateAndSignUp()
        if (errorMessages.isEmpty()) {
            navController.navigate("login_screen")
        }
    }

    SignUpScreenContent(
        username = username,
        onUsernameChange = { viewModel.updateUsername(it) }, // Pass the update function here
        email = email,
        onEmailChange = { viewModel.updateEmail(it) }, // Pass the update function here
        password = password,
        onPasswordChange = { viewModel.updatePassword(it) }, // Pass the update function here
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
            onUsernameChange = onUsernameChange, // Pass the lambda function down
            email = email,
            onEmailChange = onEmailChange, // Pass the lambda function down
            password = password,
            onPasswordChange = onPasswordChange, // Pass the lambda function down
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
    errorMessages: Map<SignUpViewModel.Field, String?>,
    onSignUpSuccess: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.sign_up_title),
            style = largeTextStyle,
            fontWeight = FontWeight.Bold,
            color = Black
        )

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
            onValueChange = onUsernameChange, // Use the lambda function passed down
            label = "Username",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text) // Set keyboard type for username
        )
        errorMessages[SignUpViewModel.Field.USERNAME]?.let { error ->
            Text(text = error, color = Red, style = smallTextStyle)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Email TextField
        CustomTextField(
            value = email,
            onValueChange = onEmailChange, // Use the lambda function passed down
            label = "Email",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email) // Set keyboard type for email
        )
        errorMessages[SignUpViewModel.Field.EMAIL]?.let { error ->
            Text(text = error, color = Red, style = smallTextStyle)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Password TextField
        CustomTextField(
            value = password,
            onValueChange = onPasswordChange, // Use the lambda function passed down
            label = "Password",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), // Set keyboard type for email
            isPassword = true
        )
        errorMessages[SignUpViewModel.Field.PASSWORD]?.let { error ->
            Text(text = error, color = Red, style = smallTextStyle)
        }

        Spacer(modifier = Modifier.height(40.dp))

        CustomButton(
            text = stringResource(R.string.sign_up_button_text),
            onClick = onSignUpSuccess,
            buttonCornerRadius = buttonCornerRadius,
            buttonHeight = buttonHeight,
            containerColor = MediumBlue,
            textStyle = buttonTextStyle
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
        errorMessages = emptyMap(),
        scrollState = rememberScrollState(0),
        onScrollStateChange = {},
        onSignUpSuccess = {}
    )
}
