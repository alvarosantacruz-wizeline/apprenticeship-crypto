package com.github.alvarosct02.criptocurrency.di

import android.content.Context
import com.github.alvarosct02.criptocurrency.BuildConfig
import com.github.alvarosct02.criptocurrency.data.CurrenciesRepository
import com.github.alvarosct02.criptocurrency.data.DefaultCurrenciesRepository
import com.github.alvarosct02.criptocurrency.data.source.local.CurrenciesLocalSource
import com.github.alvarosct02.criptocurrency.data.source.local.CurrenciesRoomSource
import com.github.alvarosct02.criptocurrency.data.source.local.room.AppDatabase
import com.github.alvarosct02.criptocurrency.data.source.remote.CurrenciesRemoteSource
import com.github.alvarosct02.criptocurrency.data.source.remote.CurrenciesRetrofitSource
import com.github.alvarosct02.criptocurrency.data.source.remote.retrofit.CurrenciesService
import com.github.alvarosct02.criptocurrency.data.source.remote.retrofit.RetrofitApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideRetrofitClient(): Retrofit {
        return RetrofitApiClient(BuildConfig.BASE_BITSO_URL).getRetrofitClient()
    }

    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        AppDatabase.init(appContext)
        return AppDatabase.getInstance()
    }

    @Provides
    fun provideCurrenciesService(retrofit: Retrofit): CurrenciesService {
        return retrofit.create(CurrenciesService::class.java)
    }

    @Provides
    fun provideCurrenciesLocalSource(appDatabase: AppDatabase): CurrenciesLocalSource {
        return CurrenciesRoomSource(
            tickerDao = appDatabase.tickerDao(),
            tradeDao = appDatabase.tradeDao(),
            tickerHistoryDao = appDatabase.tickerHistoryDao(),
            ordersDao = appDatabase.ordersDao(),
        )
    }

    @Provides
    fun provideCurrenciesRemoteSource(currenciesService: CurrenciesService): CurrenciesRemoteSource {
        return CurrenciesRetrofitSource(service = currenciesService)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideCurrenciesRepository(
        local: CurrenciesLocalSource,
        remote: CurrenciesRemoteSource
    ): CurrenciesRepository {
        return DefaultCurrenciesRepository(local = local, remote = remote)
    }
}
