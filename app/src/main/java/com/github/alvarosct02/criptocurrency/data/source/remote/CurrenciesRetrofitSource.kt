package com.github.alvarosct02.criptocurrency.data.source.remote

import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.models.TickerHistory
import com.github.alvarosct02.criptocurrency.data.source.remote.retrofit.CurrenciesService
import java.util.Calendar

class CurrenciesRetrofitSource(
    private val service: CurrenciesService
) : CurrenciesRemoteSource {

    override suspend fun getAllBooks(): List<Ticker> {
        return service.listAvailableBooks().payload
    }

    override suspend fun getTickerByBook(book: String): Ticker {
        return service.getBookDetail(book).payload
    }

    override suspend fun getTickerHistoryByBook(book: String): List<TickerHistory> {
        val timeBucketInMinutes = 30 * 60
        val endInMillis = Calendar.getInstance().timeInMillis
        val startInMillis = endInMillis - 24 * 60 * 60 * 1000
        return service.getTickerHistory(
            book = book,
            timeBucket = timeBucketInMinutes.toLong(),
            start = startInMillis,
            end = endInMillis
        ).payload.map { tickerHistory ->
            tickerHistory.also {
                it.book = book
            }
        }
    }

    override suspend fun getOrdersByBook(book: String): BookOrders {
        return service.getBookOrders(book).payload
    }
}
