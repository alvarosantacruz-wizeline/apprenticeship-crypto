package com.github.alvarosct02.criptocurrency.data

import androidx.lifecycle.LiveData
import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.models.TickerHistory
import com.github.alvarosct02.criptocurrency.data.models.Trade

interface CurrenciesRepository {

    fun getAllTickers(): LiveData<Resource<List<Ticker>>>

    fun getTickerHistoryByBook(book: String, shouldRefresh: Boolean = false): LiveData<Resource<List<TickerHistory>>>

    fun getTickerByBook(book: String): LiveData<Resource<Ticker>>

    fun getOrdersByBook(book: String): LiveData<Resource<BookOrders>>

    fun getTradesByBook(book: String): LiveData<Resource<List<Trade>>>

    suspend fun refreshAllTickers(): List<Ticker>?

    suspend fun refreshTickerByBook(book: String): Ticker?

    suspend fun refreshTickerHistoryByBook(book: String): List<TickerHistory>?

    suspend fun refreshOrdersByBook(book: String): BookOrders?

    suspend fun refreshTradesByBook(book: String): List<Trade>?
}
