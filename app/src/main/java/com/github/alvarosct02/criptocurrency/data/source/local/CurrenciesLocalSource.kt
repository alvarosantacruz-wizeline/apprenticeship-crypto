package com.github.alvarosct02.criptocurrency.data.source.local

import androidx.lifecycle.LiveData
import com.github.alvarosct02.criptocurrency.data.Resource
import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.models.TickerHistory
import com.github.alvarosct02.criptocurrency.data.models.TickerWithHistory

interface CurrenciesLocalSource {

    fun observeAllTickers(): LiveData<Resource<List<Ticker>>>

    fun observeAllTickersWithHistory(): LiveData<Resource<List<TickerWithHistory>>>

    suspend fun saveAllTickers(books: List<Ticker>)

    suspend fun saveAllTickersHistory(history: List<TickerHistory>)

    fun observeTickerByBook(book: String): LiveData<Resource<Ticker>>

    fun observeTickerHistoryByBook(book: String): LiveData<Resource<List<TickerHistory>>>

    suspend fun saveBookTicker(ticker: Ticker)

    fun observeOrdersByBook(book: String): LiveData<Resource<BookOrders>>

    suspend fun saveBookOrders(bookOrders: BookOrders)
}
