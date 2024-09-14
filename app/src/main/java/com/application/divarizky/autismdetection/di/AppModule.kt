package com.application.divarizky.autismdetection.di

import android.content.Context
import com.application.divarizky.autismdetection.data.repository.UserRepository
import com.application.divarizky.autismdetection.data.repository.UserRepositoryImpl

interface AppModule {
    val databaseModule: DatabaseModule
    val userRepository: UserRepository
    val context: Context
}

class AppModuleImpl(private val appContext: Context) : AppModule {
    override val context: Context
        get() = appContext
    override val databaseModule: DatabaseModule by lazy { DatabaseModule(appContext) }
    override val userRepository: UserRepository by lazy { UserRepositoryImpl(appContext) }
}

