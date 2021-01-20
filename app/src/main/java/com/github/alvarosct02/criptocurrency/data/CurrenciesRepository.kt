package com.github.alvarosct02.criptocurrency.data

import androidx.lifecycle.LiveData
import com.github.alvarosct02.criptocurrency.data.models.Book
import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.source.local.CurrenciesLocalSource
import com.github.alvarosct02.criptocurrency.data.source.remote.CurrenciesRemoteSource

class CurrenciesRepository(
    private val api: CurrenciesRemoteSource,
    private val local: CurrenciesLocalSource,
) {

    fun observeAllBooks(): LiveData<List<Book>> {
        return local.observeAllBooks()
    }

    fun observeTickerByBook(book: String): LiveData<Ticker> {
        return local.observeTickerByBook(book)
    }

    fun observeOrdersByBook(book: String): LiveData<BookOrders> {
        return local.observeOrdersByBook(book)
    }

    suspend fun getAllBooks(): List<Book> {
        try {
            val response = api.getAllBooks()
            local.saveAllBooks(response)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return local.getAllBooks()
    }

    suspend fun getTickerByBook(book: String): Ticker {
        try {
            val response = api.getTickerByBook(book)
            local.saveBookTicker(response)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return local.getTickerByBook(book)
    }

    suspend fun getOrdersByBook(book: String): BookOrders {
        try {
            val response = api.getOrdersByBook(book)
            local.saveBookOrders(response.copy(book = book))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return local.getOrdersByBook(book)
    }

}