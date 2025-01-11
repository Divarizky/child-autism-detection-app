package com.application.divarizky.autismdetection.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.application.divarizky.autismdetection.data.local.AppDatabase
import com.application.divarizky.autismdetection.data.model.User
import com.application.divarizky.autismdetection.utils.Validator

interface UserRepository {
    suspend fun insertUser(user: User)
    suspend fun login(emailOrUsername: String, password: String): Boolean
    suspend fun getUserById(userId: Int): User?
    suspend fun getLoggedInUser(): User?
    fun saveLoginState(isLoggedIn: Boolean)
    fun isLoggedIn(): Boolean
    suspend fun logout()
    suspend fun resetPassword(email: String, newPassword: String): Boolean // Tambahkan fungsi resetPassword
}

// UserRepository implementation
class UserRepositoryImpl(context: Context) : UserRepository {

    private val userDao = AppDatabase.getDatabase(context).userDao()
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)

    override suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    // Login menggunakan email atau username
    override suspend fun login(emailOrUsername: String, password: String): Boolean {
        val user = if (Validator.isValidEmail(emailOrUsername)) {
            userDao.loginByEmail(emailOrUsername, password)
        } else {
            userDao.loginByUsername(emailOrUsername, password)
        }
        return if (user != null) {
            userDao.logoutAllUsers()
            userDao.updateUser(user.copy(isLoggedIn = true)) // Menandai status pengguna menjadi login
            saveLoginState(true) // Menyimpan status login
            true
        } else {
            saveLoginState(false) // Gagal login
            false
        }
    }

    override suspend fun getUserById(userId: Int): User? {
        return userDao.getUserById(userId)
    }

    override fun saveLoginState(isLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean("isLoggedIn", isLoggedIn).apply()
    }

    override fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    override suspend fun logout() {
        userDao.logoutAllUsers()
        saveLoginState(false)
    }

    // Function to reset user password
    override suspend fun resetPassword(email: String, newPassword: String): Boolean {
        val user = userDao.getUserByEmail(email) // Cari user berdasarkan email
        return if (user != null) {
            val updatedUser = user.copy(password = newPassword) // Update password user
            userDao.updateUser(updatedUser) // Simpan perubahan di database
            true // Berhasil mengubah password
        } else {
            false // Gagal jika email tidak ditemukan
        }
    }

    override suspend fun getLoggedInUser(): User? {
        return userDao.getLoggedInUser()
    }

    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }
}
