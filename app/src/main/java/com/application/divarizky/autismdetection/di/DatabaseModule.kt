package com.application.divarizky.autismdetection.di

import android.content.Context
import androidx.room.Room
import com.application.divarizky.autismdetection.data.local.AppDatabase
import com.application.divarizky.autismdetection.data.local.UserDao

class DatabaseModule(context: Context) {
    private val appDatabase: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java, "database-name"
    ).build()

    fun provideUserDao(): UserDao = appDatabase.userDao()
}