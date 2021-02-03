package com.github.alvarosct02.criptocurrency

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.NONE) // Will be fix in 2.2.0-alpha-1
            androidContext(this@App)
            modules(appModule)
        }
    }
}
