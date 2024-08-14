package com.application.divarizky.autismdetection.data.repository

import android.content.Context
import com.application.divarizky.autismdetection.data.local.AppDatabase
import com.application.divarizky.autismdetection.data.model.User

// Interface untuk UserRepository
interface UserRepository {
    suspend fun insertUser(user: User)
    suspend fun login(email: String, password: String): Boolean?
    suspend fun getUserById(userId: Int): User?
}

// Implementasi untuk UserRepository
class UserRepositoryImpl(private val context: Context) : UserRepository {

    private val userDao = AppDatabase.getDatabase(context).userDao()

    override suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    override suspend fun login(email: String, password: String): Boolean {
        val user = userDao.login(email, password)
        return if (user != null) {
            userDao.logoutAllUsers()
            userDao.updateUser(user.copy(isLoggedIn = true))
            true
        } else {
            false
        }
    }

    override suspend fun getUserById(userId: Int): User? {
        return userDao.getUserById(userId)
    }

    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    suspend fun getLoggedInUser(): User? {
        return userDao.getLoggedInUser()
    }

    suspend fun logout() {
        userDao.logoutAllUsers()
    }
}