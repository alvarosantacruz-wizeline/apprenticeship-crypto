package com.github.alvarosct02.criptocurrency.data

import androidx.lifecycle.LiveData
import com.github.alvarosct02.criptocurrency.data.models.Book
import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.source.local.CurrenciesLocalSource
import com.github.alvarosct02.criptocurrency.data.source.remote.CurrenciesRemoteSource

class DefaultCurrenciesRepository(
    private val api: CurrenciesRemoteSource,
    private val local: CurrenciesLocalSource,
) : CurrenciesRepository {

    override fun observeAllBooks(): LiveData<List<Book>> {
        return local.observeAllBooks()
    }

    override fun observeTickerByBook(book: String): LiveData<Ticker> {
        return local.observeTickerByBook(book)
    }

    override fun observeOrdersByBook(book: String): LiveData<BookOrders> {
        return local.observeOrdersByBook(book)
    }

    override suspend fun getAllBooks(): Resource<List<Book>> {
        try {
            val response = api.getAllBooks()
            local.saveAllBooks(response)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Resource.Success(local.getAllBooks())
    }

    override suspend fun getTickerByBook(book: String): Resource<Ticker> {
        try {
            val response = api.getTickerByBook(book)
            local.saveBookTicker(response)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Resource.Success(local.getTickerByBook(book))
    }

    override suspend fun getOrdersByBook(book: String): Resource<BookOrders> {
        try {
            val response = api.getOrdersByBook(book)
            local.saveBookOrders(response.copy(book = book))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Resource.Success(local.getOrdersByBook(book))
    }

}
