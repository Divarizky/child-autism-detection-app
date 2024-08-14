package com.application.divarizky.autismdetection.data.repository

import android.content.Context
import com.application.divarizky.autismdetection.data.local.AppDatabase
import com.application.divarizky.autismdetection.data.model.User

// Interface untuk UserRepository
interface UserRepository {
    suspend fun insertUser(user: User)
    suspend fun login(email: String, password: String): User?
    suspend fun getUserById(userId: Int): User?
}

// Implementasi untuk UserRepository
class UserRepositoryImpl(private val context: Context) : UserRepository {

    private val userDao = AppDatabase.getDatabase(context).userDao()

    override suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }

    override suspend fun login(email: String, password: String): User? {
        return userDao.login(email, password)
    }

    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    override suspend fun getUserById(userId: Int): User? {
        return userDao.getUserById(userId)
    }
}