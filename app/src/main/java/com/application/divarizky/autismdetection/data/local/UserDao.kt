package com.application.divarizky.autismdetection.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.application.divarizky.autismdetection.data.model.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): User?

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: Int): User?
}
