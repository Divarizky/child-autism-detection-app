package com.application.divarizky.autismdetection.utils

import android.util.Patterns

object Validator {

    // Validasi format username (contoh: minimal 3 karakter)
    fun isValidUsername(username: String): Boolean {
        return username.length >= 3
    }

    // Validasi format email
    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Validasi format password (contoh: minimal 8 karakter dan harus mengandung angka)
    fun isValidPassword(password: String): Boolean {
        return password.length >= 8 && password.any { it.isDigit() }
    }
}