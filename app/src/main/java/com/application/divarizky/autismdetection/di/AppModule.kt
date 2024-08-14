package com.application.divarizky.autismdetection.di

import android.content.Context
import com.application.divarizky.autismdetection.data.repository.UserRepository
import com.application.divarizky.autismdetection.data.repository.UserRepositoryImpl

interface AppModule {
    val databaseModule: DatabaseModule
    val userRepository: UserRepository
}

class AppModuleImpl(private val context: Context) : AppModule {
    override val databaseModule: DatabaseModule by lazy { DatabaseModule(context) }
    override val userRepository: UserRepository by lazy { UserRepositoryImpl(context) }
}

