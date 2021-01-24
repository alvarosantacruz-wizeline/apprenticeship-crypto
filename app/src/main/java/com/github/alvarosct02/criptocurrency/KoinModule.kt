package com.github.alvarosct02.criptocurrency

import com.github.alvarosct02.criptocurrency.data.CurrenciesRepository
import com.github.alvarosct02.criptocurrency.data.source.local.CurrenciesRoomSource
import com.github.alvarosct02.criptocurrency.data.source.remote.CurrenciesRetrofitSource
import com.github.alvarosct02.criptocurrency.data.source.remote.retrofit.CurrenciesService
import com.github.alvarosct02.criptocurrency.data.source.remote.retrofit.RetrofitApiClient
import com.github.alvarosct02.criptocurrency.features.currencyDetail.CurrencyDetailViewModel
import com.github.alvarosct02.criptocurrency.features.currencyList.CurrencyListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        RetrofitApiClient(BuildConfig.BASE_BITSO_URL).getRetrofitClient()
            .create(CurrenciesService::class.java)
    }

    single {
        CurrenciesRepository(
            local = CurrenciesRoomSource(context = get()),
            api = CurrenciesRetrofitSource(service = get())
        )
    }

//    ViewModels
    viewModel { CurrencyDetailViewModel(get()) }
    viewModel { CurrencyListViewModel(get()) }
}