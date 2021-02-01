package com.github.alvarosct02.criptocurrency.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.github.alvarosct02.criptocurrency.data.ErrorType
import com.github.alvarosct02.criptocurrency.data.Resource
import com.github.alvarosct02.criptocurrency.data.models.Book
import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.source.local.CurrenciesLocalSource
import kotlin.collections.set

class FakeCurrenciesLocalSource : CurrenciesLocalSource {

    private var booksList: List<Book>? = null
    private var tickerMap: MutableMap<String, Ticker> = mutableMapOf()
    private var ordersMap: MutableMap<String, BookOrders> = mutableMapOf()

    override fun observeAllBooks(): LiveData<Resource<List<Book>>> {
        return liveData<Resource<List<Book>>>  {
            emit(getAllBooks()?.let { Resource.Success(it) }
                ?: Resource.Error(errorType = ErrorType.Unknown))
        }
    }

    @WorkerThread
    override suspend fun getAllBooks(): List<Book>? {
        return booksList
    }

    @WorkerThread
    override suspend fun saveAllBooks(books: List<Book>) {
        this.booksList = books
    }

    override fun observeTickerByBook(book: String): LiveData<Resource<Ticker>> {
        return liveData<Resource<Ticker>>  {
            emit(getTickerByBook(book)?.let { Resource.Success(it) }
                ?: Resource.Error(errorType = ErrorType.Unknown))
        }
    }

    @WorkerThread
    override suspend fun getTickerByBook(book: String): Ticker? {
        return tickerMap[book]
    }

    @WorkerThread
    override suspend fun saveBookTicker(ticker: Ticker) {
        this.tickerMap[ticker.book] = ticker
    }

    override fun observeOrdersByBook(book: String): LiveData<Resource<BookOrders>> {
        return liveData<Resource<BookOrders>> {
            emit(getOrdersByBook(book)?.let { Resource.Success(it) }
                ?: Resource.Error(errorType = ErrorType.Unknown))
        }
    }

    @WorkerThread
    override suspend fun getOrdersByBook(book: String): BookOrders? {
        return ordersMap[book]
    }

    @WorkerThread
    override suspend fun saveBookOrders(bookOrders: BookOrders) {
        this.ordersMap[bookOrders.book] = bookOrders
    }

}