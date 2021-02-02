package com.github.alvarosct02.criptocurrency.shared

import com.github.alvarosct02.criptocurrency.data.CurrenciesRepository
import com.github.alvarosct02.criptocurrency.data.DefaultCurrenciesRepository
import com.github.alvarosct02.criptocurrency.data.source.local.CurrenciesLocalSource
import com.github.alvarosct02.criptocurrency.data.source.remote.CurrenciesRemoteSource
import com.github.alvarosct02.criptocurrency.ui.currencyDetail.CurrencyDetailViewModel
import com.github.alvarosct02.criptocurrency.ui.currencyList.CurrencyListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val fakeAppModule = module {

    single<CurrenciesLocalSource> {
        FakeCurrenciesLocalSource()
    }

    single<CurrenciesRemoteSource> {
        FakeCurrenciesRemoteSource()
    }

    single<CurrenciesRepository> {
        DefaultCurrenciesRepository(local = get(), api = get())
    }

//    ViewModels
    viewModel { CurrencyDetailViewModel(get()) }
    viewModel { CurrencyListViewModel(get()) }
}