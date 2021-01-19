package com.github.alvarosct02.criptocurrency

import android.app.Application

class App() : Application() {

    override fun onCreate() {
        super.onCreate()
        ServiceLocator.provideCurrenciesRepository(this)
    }
}