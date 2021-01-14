package com.github.alvarosct02.criptocurrency

import android.content.Context
import com.github.alvarosct02.criptocurrency.data.source.CurrenciesRepository
import com.github.alvarosct02.criptocurrency.data.source.local.CurrenciesLocalDataSource
import com.github.alvarosct02.criptocurrency.data.source.remote.CurrenciesRemoteDataSource

object ServiceLocator {

    var currenciesRepository: CurrenciesRepository? = null
    fun provideCurrenciesRepository(context: Context): CurrenciesRepository {
        synchronized(this) {
            return currenciesRepository ?: currenciesRepository ?: createCurrenciesRepository(
                context
            )
        }
    }

    private fun createCurrenciesRepository(context: Context): CurrenciesRepository {
        val newRepo = CurrenciesRepository(
            remote = CurrenciesRemoteDataSource(),
            local = CurrenciesLocalDataSource(context),
        )
        currenciesRepository = newRepo
        return newRepo
    }
}