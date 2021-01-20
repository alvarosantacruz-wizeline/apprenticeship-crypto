package com.github.alvarosct02.criptocurrency.data.source.remote

import com.github.alvarosct02.criptocurrency.BuildConfig
import com.github.alvarosct02.criptocurrency.data.models.Book
import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.source.remote.retrofit.CurrenciesService
import com.github.alvarosct02.criptocurrency.data.source.remote.retrofit.RetrofitApiClient
import retrofit2.create

class CurrenciesRetrofitSource() : CurrenciesRemoteSource {

    //    TODO: Pending Refactor with DI
    private val currencyService by lazy {
        RetrofitApiClient(BuildConfig.BASE_BITSO_URL).getRetrofitClient()
            .create<CurrenciesService>()
    }

    override suspend fun getAllBooks(): List<Book> {
        return currencyService.listAvailableBooks().payload
    }

    override suspend fun getTickerByBook(book: String): Ticker {
        return currencyService.getBookDetail(book).payload
    }

    override suspend fun getOrdersByBook(book: String): BookOrders {
        return currencyService.getBookOrders(book).payload
    }

}