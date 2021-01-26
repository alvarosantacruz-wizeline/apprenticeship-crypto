package com.github.alvarosct02.criptocurrency

import com.github.alvarosct02.criptocurrency.data.DefaultCurrenciesRepository
import com.github.alvarosct02.criptocurrency.data.source.local.CurrenciesLocalSource
import com.github.alvarosct02.criptocurrency.data.source.local.CurrenciesRoomSource
import com.github.alvarosct02.criptocurrency.data.source.local.room.AppDatabase
import com.github.alvarosct02.criptocurrency.data.source.remote.CurrenciesRemoteSource
import com.github.alvarosct02.criptocurrency.data.source.remote.CurrenciesRetrofitSource
import com.github.alvarosct02.criptocurrency.data.source.remote.retrofit.CurrenciesService
import com.github.alvarosct02.criptocurrency.data.source.remote.retrofit.RetrofitApiClient
import com.github.alvarosct02.criptocurrency.features.currencyDetail.CurrencyDetailViewModel
import com.github.alvarosct02.criptocurrency.features.currencyList.CurrencyListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {

    single {
        AppDatabase.init(get())
        AppDatabase.getInstance()
    }

    single {
        RetrofitApiClient(BuildConfig.BASE_BITSO_URL).getRetrofitClient()
    }

    single<CurrenciesService> {
        val retrofit: Retrofit = get()
        retrofit.create(CurrenciesService::class.java)
    }

    single<CurrenciesLocalSource> {
        CurrenciesRoomSource(appDatabase = get())
    }

    single<CurrenciesRemoteSource> {
        CurrenciesRetrofitSource(service = get())
    }

    single {
        DefaultCurrenciesRepository(local = get(), api = get())
    }

//    ViewModels
    viewModel { CurrencyDetailViewModel(get()) }
    viewModel { CurrencyListViewModel(get()) }
}