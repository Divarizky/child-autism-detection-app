package com.application.divarizky.autismdetection.viewmodel

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
import com.application.divarizky.autismdetection.utils.Validator
import kotlinx.coroutines.launch

class SignUpViewModel(private val userRepository: UserRepository, private val validator: Validator) : ViewModel() {

    private val _username = MutableLiveData("")
    val username: LiveData<String> = _username

    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    private val _isPasswordVisible = MutableLiveData(false)
    val isPasswordVisible: LiveData<Boolean> = _isPasswordVisible

    fun togglePasswordVisibility() {
        _isPasswordVisible.value = !(_isPasswordVisible.value ?: false)
    }

    fun updateUsername(newUsername: String) {
        _username.value = newUsername
    }

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    private val _errorMessages = MutableLiveData<Map<Field, String?>>()
    val errorMessages: LiveData<Map<Field, String?>> = _errorMessages

    enum class Field { USERNAME, EMAIL, PASSWORD }

    private var isValidationAttempted = false

    var scrollState: ScrollState by mutableStateOf(ScrollState(0))

    // Function to update scroll position
    fun updateScrollState(newScrollState: ScrollState) {
        scrollState = newScrollState
    }

    private fun validateFields() {
        val errors = mutableMapOf<Field, String?>()

        if (!validator.isValidUsername(_username.value ?: "")) {
            errors[Field.USERNAME] = "Invalid username. Must be at least 3 characters long."
        }
        if (!validator.isValidEmail(_email.value ?: "")) {
            errors[Field.EMAIL] = "Invalid email. Please enter a valid email address."
        }
        if (!validator.isValidPassword(_password.value ?: "")) {
            errors[Field.PASSWORD] = "Invalid password. Must be at least 8 characters and contain at least one digit."
        }

        _errorMessages.value = errors
    }

    fun validateAndSignUp() {
        isValidationAttempted = true
        validateFields()

        if (_errorMessages.value.isNullOrEmpty()) {
            viewModelScope.launch {
                val user = User(
                    username = _username.value ?: "",
                    email = _email.value ?: "",
                    password = _password.value ?: ""
                )
                userRepository.insertUser(user)
            }
        }
    }
}
