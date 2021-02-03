package com.github.alvarosct02.criptocurrency.di

import com.github.alvarosct02.criptocurrency.data.CurrenciesRepository
import com.github.alvarosct02.criptocurrency.data.DefaultCurrenciesRepository
import com.github.alvarosct02.criptocurrency.data.source.local.CurrenciesLocalSource
import com.github.alvarosct02.criptocurrency.data.source.local.CurrenciesRoomSource
import com.github.alvarosct02.criptocurrency.data.source.local.room.AppDatabase
import com.github.alvarosct02.criptocurrency.data.source.remote.CurrenciesRemoteSource
import com.github.alvarosct02.criptocurrency.data.source.remote.CurrenciesRetrofitSource
import com.github.alvarosct02.criptocurrency.data.source.remote.retrofit.CurrenciesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideCurrenciesService(retrofit: Retrofit): CurrenciesService {
        return retrofit.create(CurrenciesService::class.java)
    }

    @Provides
    fun provideCurrenciesLocalSource(appDatabase: AppDatabase): CurrenciesLocalSource {
        return CurrenciesRoomSource(appDatabase = appDatabase)
    }

    @Provides
    fun provideCurrenciesRemoteSource(currenciesService: CurrenciesService): CurrenciesRemoteSource {
        return CurrenciesRetrofitSource(service = currenciesService)
    }

    @Provides
    fun provideCurrenciesRepository(
        local: CurrenciesLocalSource,
        remote: CurrenciesRemoteSource
    ): CurrenciesRepository {
        return DefaultCurrenciesRepository(local = local, api = remote)
    }
}
