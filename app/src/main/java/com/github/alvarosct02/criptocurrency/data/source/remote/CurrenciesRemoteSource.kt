package com.github.alvarosct02.criptocurrency.data.source.remote

import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.models.TickerHistory

interface CurrenciesRemoteSource {

    suspend fun getAllBooks(): List<Ticker>

    suspend fun getTickerByBook(book: String): Ticker

    suspend fun getTickerHistoryByBook(book: String): List<TickerHistory>

    suspend fun getOrdersByBook(book: String): BookOrders
}
