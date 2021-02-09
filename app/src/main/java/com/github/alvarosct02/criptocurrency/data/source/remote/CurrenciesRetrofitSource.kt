package com.github.alvarosct02.criptocurrency.data.source.remote

import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.source.remote.retrofit.CurrenciesService

class CurrenciesRetrofitSource(
    private val service: CurrenciesService
) : CurrenciesRemoteSource {

    override suspend fun getAllBooks(): List<Ticker> {
        return service.listAvailableBooks().payload
    }

    override suspend fun getTickerByBook(book: String): Ticker {
        return service.getBookDetail(book).payload
    }

    override suspend fun getOrdersByBook(book: String): BookOrders {
        return service.getBookOrders(book).payload
    }
}
