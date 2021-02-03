package com.github.alvarosct02.criptocurrency.data.source.remote

import com.github.alvarosct02.criptocurrency.data.models.Book
import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.source.remote.retrofit.CurrenciesService
import kotlinx.coroutines.delay

class CurrenciesRetrofitSource(
    private val service: CurrenciesService
) : CurrenciesRemoteSource {

    override suspend fun getAllBooks(): List<Book> {
        delay(1000)
        return service.listAvailableBooks().payload
    }

    override suspend fun getTickerByBook(book: String): Ticker {
        delay(1000)
        return service.getBookDetail(book).payload
    }

    override suspend fun getOrdersByBook(book: String): BookOrders {
        delay(1000)
        return service.getBookOrders(book).payload
    }

}