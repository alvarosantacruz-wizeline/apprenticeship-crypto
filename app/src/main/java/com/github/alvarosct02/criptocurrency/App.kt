package com.github.alvarosct02.criptocurrency

import android.app.Application
import com.github.alvarosct02.criptocurrency.data.repository.CurrenciesRepository

class App(): Application() {

    val currenciesRepository: CurrenciesRepository
        get() = ServiceLocator.provideCurrenciesRepository(this)

    override fun onCreate() {
        super.onCreate()
    }
}