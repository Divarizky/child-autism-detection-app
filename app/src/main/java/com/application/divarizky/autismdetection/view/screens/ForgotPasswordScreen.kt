package com.application.divarizky.autismdetection.view.screens

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
import com.application.divarizky.autismdetection.viewmodel.ForgotPasswordViewModel

@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel,
    navController: NavController
) {
    val email by viewModel.email.observeAsState("")
    val newPassword by viewModel.newPassword.observeAsState("")
    val confirmPassword by viewModel.confirmPassword.observeAsState("")
    val isPasswordVisible by viewModel.isPasswordVisible.observeAsState(false)
    val isConfirmPasswordVisible by viewModel.isConfirmPasswordVisible.observeAsState(false)
    val errorMessages by viewModel.errorMessages.observeAsState(emptyMap())

    val onResetSuccess: () -> Unit = {
        viewModel.validateAndResetPassword()
        if (errorMessages.isEmpty()) {
            navController.navigate("login_screen")
        }
    }

    ForgotPasswordScreenContent(
        email = email,
        onEmailChange = { viewModel.updateEmail(it) },
        newPassword = newPassword,
        onNewPasswordChange = { viewModel.updateNewPassword(it) },
        confirmPassword = confirmPassword,
        onConfirmPasswordChange = { viewModel.updateConfirmPassword(it) },
        isPasswordVisible = isPasswordVisible,
        onTogglePasswordVisibility = { viewModel.togglePasswordVisibility() },
        isConfirmPasswordVisible = isConfirmPasswordVisible,
        onToggleConfirmPasswordVisibility = { viewModel.toggleConfirmPasswordVisibility() },
        errorMessages = errorMessages,
        onResetSuccess = onResetSuccess
    )
}

@Composable
fun ForgotPasswordScreenContent(
    email: String,
    onEmailChange: (String) -> Unit,
    newPassword: String,
    onNewPasswordChange: (String) -> Unit,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    isConfirmPasswordVisible: Boolean,
    onToggleConfirmPasswordVisibility: () -> Unit,
    errorMessages: Map<ForgotPasswordViewModel.Field, String?>,
    onResetSuccess: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(paddings)
            .verticalScroll(rememberScrollState())
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

        ForgotPasswordSection(
            email = email,
            onEmailChange = onEmailChange,
            newPassword = newPassword,
            onNewPasswordChange = onNewPasswordChange,
            confirmPassword = confirmPassword,
            onConfirmPasswordChange = onConfirmPasswordChange,
            isPasswordVisible = isPasswordVisible,
            onTogglePasswordVisibility = onTogglePasswordVisibility,
            isConfirmPasswordVisible = isConfirmPasswordVisible,
            onToggleConfirmPasswordVisibility = onToggleConfirmPasswordVisibility,
            errorMessages = errorMessages,
            onResetSuccess = onResetSuccess
        )
    }
}

@Composable
fun ForgotPasswordSection(
    email: String,
    onEmailChange: (String) -> Unit,
    newPassword: String,
    onNewPasswordChange: (String) -> Unit,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    isConfirmPasswordVisible: Boolean,
    onToggleConfirmPasswordVisibility: () -> Unit,
    errorMessages: Map<ForgotPasswordViewModel.Field, String?>,
    onResetSuccess: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.forgot_password_heading),
            style = titleTextStyle,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = stringResource(R.string.forgot_password_description),
            style = regularTextStyle,
            fontWeight = FontWeight.Normal,
            color = LightGray
        )

        Spacer(modifier = Modifier.height(paddings))

        // Email TextField
        CustomTextField(
            value = email,
            onValueChange = onEmailChange,
            label = stringResource(R.string.email_hint),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        errorMessages[ForgotPasswordViewModel.Field.EMAIL]?.let { error ->
            Text(text = error, color = Red, style = smallTextStyle)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // New Password TextField
        CustomTextField(
            value = newPassword,
            onValueChange = onNewPasswordChange,
            label = stringResource(R.string.new_password_hint),
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
        errorMessages[ForgotPasswordViewModel.Field.NEW_PASSWORD]?.let { error ->
            Text(text = error, color = Red, style = smallTextStyle)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Confirm Password TextField
        CustomTextField(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            label = stringResource(R.string.confirm_password_hint),
            isPassword = !isConfirmPasswordVisible,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = onToggleConfirmPasswordVisibility) {
                    Icon(
                        painter = painterResource(
                            id = if (isConfirmPasswordVisible) R.drawable.ic_visibility_off else R.drawable.ic_visibility
                        ),
                        contentDescription = stringResource(
                            id = if (isConfirmPasswordVisible) R.string.hide_password else R.string.show_password
                        )
                    )
                }
            }
        )
        errorMessages[ForgotPasswordViewModel.Field.CONFIRM_PASSWORD]?.let { error ->
            Text(text = error, color = Red, style = smallTextStyle)
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Reset Password Button
        CustomButton(
            text = stringResource(R.string.reset_password_button_text),
            onClick = onResetSuccess,
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
