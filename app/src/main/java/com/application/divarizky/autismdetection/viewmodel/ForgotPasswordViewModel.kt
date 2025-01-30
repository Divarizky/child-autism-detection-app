package com.application.divarizky.autismdetection.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.divarizky.autismdetection.data.repository.UserRepository
import com.application.divarizky.autismdetection.utils.Validator
import kotlinx.coroutines.launch

open class ForgotPasswordViewModel(private val userRepository: UserRepository, private val validator: Validator) : ViewModel() {

    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _newPassword = MutableLiveData("")
    val newPassword: LiveData<String> = _newPassword

    private val _confirmPassword = MutableLiveData("")
    val confirmPassword: LiveData<String> = _confirmPassword

    private val _isPasswordVisible = MutableLiveData(false)
    val isPasswordVisible: LiveData<Boolean> = _isPasswordVisible

    private val _isConfirmPasswordVisible = MutableLiveData(false)
    val isConfirmPasswordVisible: LiveData<Boolean> = _isConfirmPasswordVisible

    fun togglePasswordVisibility() {
        _isPasswordVisible.value = !(_isPasswordVisible.value ?: false)
    }

    fun toggleConfirmPasswordVisibility() {
        _isConfirmPasswordVisible.value = !(_isConfirmPasswordVisible.value ?: false)
    }

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updateNewPassword(newPassword: String) {
        _newPassword.value = newPassword
    }

    fun updateConfirmPassword(newConfirmPassword: String) {
        _confirmPassword.value = newConfirmPassword
    }

    private val _errorMessages = MutableLiveData<Map<Field, String?>>()
    val errorMessages: LiveData<Map<Field, String?>> = _errorMessages

    enum class Field { EMAIL, NEW_PASSWORD, CONFIRM_PASSWORD }

    fun validateAndResetPassword() {
        val errors = mutableMapOf<Field, String?>()

        if (!validator.isValidEmail(_email.value ?: "")) {
            errors[Field.EMAIL] = "Invalid email. Please enter a valid email address."
        }
        if (!validator.isValidPassword(_newPassword.value ?: "")) {
            errors[Field.NEW_PASSWORD] = "Invalid password. Must be at least 8 characters and contain at least one digit."
        }
        if (_newPassword.value != _confirmPassword.value) {
            errors[Field.CONFIRM_PASSWORD] = "Passwords do not match."
        }

        _errorMessages.value = errors

        if (errors.isEmpty()) {
            viewModelScope.launch {
                userRepository.resetPassword(_email.value ?: "", _newPassword.value ?: "")
            }
        }
    }
}
