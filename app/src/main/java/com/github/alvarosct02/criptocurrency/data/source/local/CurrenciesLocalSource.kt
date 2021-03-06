package com.github.alvarosct02.criptocurrency.data.source.local

import androidx.lifecycle.LiveData
import com.github.alvarosct02.criptocurrency.data.Resource
import com.github.alvarosct02.criptocurrency.data.models.Book
import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker

interface CurrenciesLocalSource {

    fun observeAllBooks(): LiveData<Resource<List<Book>>>

    suspend fun getAllBooks(): List<Book>?

    suspend fun saveAllBooks(books: List<Book>)

    fun observeTickerByBook(book: String): LiveData<Resource<Ticker>>

    suspend fun getTickerByBook(book: String): Ticker?

    suspend fun saveBookTicker(ticker: Ticker)

    fun observeOrdersByBook(book: String): LiveData<Resource<BookOrders>>

    suspend fun getOrdersByBook(book: String): BookOrders?

    suspend fun saveBookOrders(bookOrders: BookOrders)

}