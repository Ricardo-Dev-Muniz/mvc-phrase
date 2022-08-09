package com.app.co.core.app

import android.app.Application
import com.app.co.core.module.App
import com.app.co.core.module.Utilities
import org.koin.core.context.loadKoinModules

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        koin()
    }

    private fun koin() {
        Utilities.loadKoin(applicationContext)
        loadKoinModules(App.modules())
    }
}