package com.github.alvarosct02.criptocurrency.data

import androidx.lifecycle.LiveData
import com.github.alvarosct02.criptocurrency.data.models.Book
import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker

interface CurrenciesRepository {

    fun getAllBooks(): LiveData<Resource<List<Book>>>

    fun getTickerByBook(book: String): LiveData<Resource<Ticker>>

    fun getOrdersByBook(book: String): LiveData<Resource<BookOrders>>

    suspend fun refreshAllBooks(): List<Book>

    suspend fun refreshTickerByBook(book: String): Ticker

    suspend fun refreshOrdersByBook(book: String): BookOrders
}
