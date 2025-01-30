package com.application.divarizky.autismdetection.viewmodel

import android.content.Context
import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.divarizky.autismdetection.data.repository.UserRepository
import com.application.divarizky.autismdetection.utils.ErrorHandler.Companion.handleLoginError
import com.application.divarizky.autismdetection.utils.Validator
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository,
    private val validator: Validator
) : ViewModel() {

    private val _emailOrUsername = MutableLiveData("")
    val emailOrUsername: LiveData<String> = _emailOrUsername

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    private val _errorMessages = MutableLiveData<Map<Field, String?>>(emptyMap())
    val errorMessages: LiveData<Map<Field, String?>> = _errorMessages

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: MutableLiveData<Boolean> = _loginSuccess

    private val _isPasswordVisible = MutableLiveData(false)
    val isPasswordVisible: LiveData<Boolean> = _isPasswordVisible

    fun togglePasswordVisibility() {
        _isPasswordVisible.value = !(_isPasswordVisible.value ?: false)
    }

    private var isValidationAttempted = false

    var scrollState: ScrollState by mutableStateOf(ScrollState(0))

    fun updateScrollState(newScrollState: ScrollState) {
        scrollState = newScrollState
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    enum class Field { EMAIL_OR_USERNAME, PASSWORD }

    fun onEmailOrUsernameChange(newInput: String) {
        _emailOrUsername.value = newInput
        if (isValidationAttempted) validateFields()
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
        if (isValidationAttempted) validateFields()
    }

    private fun validateFields(): Boolean {
        val errors = mutableMapOf<Field, String?>()

        // Validasi apakah input adalah email atau username
        val input = _emailOrUsername.value.orEmpty()
        if (input.isEmpty() || (!validator.isValidEmail(input) && !validator.isValidUsername(input))) {
            errors[Field.EMAIL_OR_USERNAME] = "Please enter a valid email address or username."
        }

        if (!validator.isValidPassword(_password.value.orEmpty())) {
            errors[Field.PASSWORD] = "Invalid password. Must be at least 8 characters and contain at least one digit."
        }

        _errorMessages.value = errors
        return errors.isEmpty()
    }

    fun login(context: Context) {
        isValidationAttempted = true
        if (!validateFields()) {
            handleLoginError(context, IllegalArgumentException("Validation failed"))
            return
        }

        setLoading(true)
        viewModelScope.launch {
            try {
                val isSuccess = userRepository.login(
                    emailOrUsername = _emailOrUsername.value.orEmpty(),
                    password = _password.value.orEmpty()
                )
                setLoading(false)
                _loginSuccess.value = isSuccess

                if (!isSuccess) {
                    handleLoginError(context, Exception("Invalid credentials or user not found"))
                }
            } catch (e: Exception) {
                setLoading(false)
                handleLoginError(context, e)
                _loginSuccess.value = false
            }
        }
    }

    fun checkLoginStatus(): Boolean {
        setLoading(true)
        val isLoggedIn = userRepository.isLoggedIn()
        setLoading(false)
        return isLoggedIn
    }
}
