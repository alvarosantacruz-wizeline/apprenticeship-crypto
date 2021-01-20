package com.github.alvarosct02.criptocurrency

import android.content.Context
import com.github.alvarosct02.criptocurrency.data.CurrenciesRepository
import com.github.alvarosct02.criptocurrency.data.source.local.CurrenciesRoomSource
import com.github.alvarosct02.criptocurrency.data.source.remote.CurrenciesRemoteSource
import com.github.alvarosct02.criptocurrency.data.source.remote.CurrenciesRetrofitSource

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
            api = CurrenciesRetrofitSource(),
            local = CurrenciesRoomSource(context),
        )
        currenciesRepository = newRepo
        return newRepo
    }
}