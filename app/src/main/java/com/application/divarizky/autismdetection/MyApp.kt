package com.application.divarizky.autismdetection

import android.app.Application
import com.application.divarizky.autismdetection.di.AppModule
import com.application.divarizky.autismdetection.di.AppModuleImpl

class MyApp: Application() {

    companion object {
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
    }
}