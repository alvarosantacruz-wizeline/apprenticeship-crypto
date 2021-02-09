package com.github.alvarosct02.criptocurrency.data

import androidx.lifecycle.LiveData
import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker

interface CurrenciesRepository {

    fun getAllTickers(): LiveData<Resource<List<Ticker>>>

    fun getTickerByBook(book: String): LiveData<Resource<Ticker>>

    fun getOrdersByBook(book: String): LiveData<Resource<BookOrders>>

    suspend fun refreshAllTickers(): List<Ticker>

    suspend fun refreshTickerByBook(book: String): Ticker

    suspend fun refreshOrdersByBook(book: String): BookOrders
}
