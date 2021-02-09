package com.github.alvarosct02.criptocurrency.data.source.local

import androidx.lifecycle.LiveData
import com.github.alvarosct02.criptocurrency.data.Resource
import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker

interface CurrenciesLocalSource {

    fun observeAllTickers(): LiveData<Resource<List<Ticker>>>

    suspend fun getAllTickers(): List<Ticker>?

    suspend fun saveAllTickers(books: List<Ticker>)

    fun observeTickerByBook(book: String): LiveData<Resource<Ticker>>

    suspend fun getTickerByBook(book: String): Ticker?

    suspend fun saveBookTicker(ticker: Ticker)

    fun observeOrdersByBook(book: String): LiveData<Resource<BookOrders>>

    suspend fun getOrdersByBook(book: String): BookOrders?

    suspend fun saveBookOrders(bookOrders: BookOrders)
}
