package com.application.divarizky.autismdetection.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.application.divarizky.autismdetection.data.local.AppDatabase
import com.application.divarizky.autismdetection.data.model.User

interface UserRepository {
    suspend fun insertUser(user: User)
    suspend fun login(email: String, password: String): Boolean?
    suspend fun getUserById(userId: Int): User?
    fun saveLoginState(isLoggedIn: Boolean)
    fun isLoggedIn(): Boolean
    suspend fun logout()
}

// UserRepository implementation
class UserRepositoryImpl(context: Context) : UserRepository {

    private val userDao = AppDatabase.getDatabase(context).userDao()
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)

    override suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    override suspend fun login(email: String, password: String): Boolean {
        val user = userDao.login(email, password)
        return if (user != null) {
            userDao.logoutAllUsers()
            userDao.updateUser(user.copy(isLoggedIn = true))
            saveLoginState(true)  // Save login state after successful login
            true
        } else {
            saveLoginState(false)  // If login fails, save login state as false
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

    // Function to handle on logout, save login state as false
    override suspend fun logout() {
        userDao.logoutAllUsers()
        saveLoginState(false)
    }

    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    suspend fun getLoggedInUser(): User? {
        return userDao.getLoggedInUser()
    }
}
