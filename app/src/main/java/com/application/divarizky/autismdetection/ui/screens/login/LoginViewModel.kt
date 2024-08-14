package com.application.divarizky.autismdetection.ui.screens.login

import android.content.Context
import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.divarizky.autismdetection.data.model.User
import com.application.divarizky.autismdetection.data.repository.UserRepository
import com.application.divarizky.autismdetection.utils.ErrorHandler.Companion.handleLoginError
import com.application.divarizky.autismdetection.utils.Validator
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository, private val validator: Validator) : ViewModel() {

    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    private val _errorMessages = MutableLiveData<Map<Field, String?>>()
    val errorMessages: LiveData<Map<Field, String?>> = _errorMessages

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> = _loginSuccess

    private var isValidationAttempted = false

    var scrollState: ScrollState by mutableStateOf(ScrollState(0))

    fun updateScrollState(newScrollState: ScrollState) {
        scrollState = newScrollState
    }

    enum class Field { EMAIL, PASSWORD }

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
        if (isValidationAttempted) validateFields()
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
        if (isValidationAttempted) validateFields()
    }

    private fun validateFields(): Boolean {
        val errors = mutableMapOf<Field, String?>()

        if (!validator.isValidEmail(_email.value ?: "")) {
            errors[Field.EMAIL] = "Invalid email. Please enter a valid email address."
        }

        if (!validator.isValidPassword(_password.value ?: "")) {
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

        _isLoading.value = true
        viewModelScope.launch {
            try {
                val isSuccess = userRepository.login(_email.value ?: "", _password.value ?: "")
                _isLoading.value = false
                _loginSuccess.value = isSuccess
                if (isSuccess == false) {
                    handleLoginError(context, Exception("Invalid credentials or user not found"))
                }
            } catch (e: Exception) {
                _isLoading.value = false
                handleLoginError(context, e)
                _loginSuccess.value = false
            }
        }
    }

    fun getUserById(userId: Int, onResult: (User?) -> Unit) {
        viewModelScope.launch {
            val user = userRepository.getUserById(userId)
            onResult(user)
        }
    }
}
