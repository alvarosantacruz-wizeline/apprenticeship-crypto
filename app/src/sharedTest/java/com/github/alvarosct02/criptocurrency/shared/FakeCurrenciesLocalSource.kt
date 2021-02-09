package com.github.alvarosct02.criptocurrency.shared

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.alvarosct02.criptocurrency.data.ErrorType
import com.github.alvarosct02.criptocurrency.data.Resource
import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.source.local.CurrenciesLocalSource
import kotlin.collections.set

class FakeCurrenciesLocalSource : CurrenciesLocalSource {

    private var booksList: List<Book>? = null
    private var tickerMap: MutableMap<String, Ticker> = mutableMapOf()
    private var ordersMap: MutableMap<String, BookOrders> = mutableMapOf()

    private var booksLiveData = MutableLiveData<Resource<List<Book>>>()
    private var tickerLiveData = MutableLiveData<Resource<Ticker>>()
    private var ordersLiveData = MutableLiveData<Resource<BookOrders>>()

    override fun observeAllBooks(): LiveData<Resource<List<Book>>> {
        return booksLiveData
    }

    @WorkerThread
    override suspend fun getAllBooks(): List<Book>? {
        val result = booksList
        booksLiveData.postValue(
            result?.let { Resource.Success(it) }
                ?: Resource.Error(errorType = ErrorType.Unknown)
        )
        return result
    }

    @WorkerThread
    override suspend fun saveAllBooks(books: List<Book>) {
        this.booksList = books
        val result = books
        booksLiveData.postValue(
            result?.let { Resource.Success(it) }
                ?: Resource.Error(errorType = ErrorType.Unknown)
        )
    }

    override fun observeTickerByBook(book: String): LiveData<Resource<Ticker>> {
        return tickerLiveData
    }

    @WorkerThread
    override suspend fun getTickerByBook(book: String): Ticker? {
        val result = tickerMap[book]
        tickerLiveData.postValue(
            result?.let { Resource.Success(it) }
                ?: Resource.Error(errorType = ErrorType.Unknown)
        )
        return result
    }

    @WorkerThread
    override suspend fun saveBookTicker(ticker: Ticker) {
        this.tickerMap[ticker.book] = ticker
        val result = ticker
        tickerLiveData.postValue(
            result?.let { Resource.Success(it) }
                ?: Resource.Error(errorType = ErrorType.Unknown)
        )
    }

    override fun observeOrdersByBook(book: String): LiveData<Resource<BookOrders>> {
        return ordersLiveData
    }

    @WorkerThread
    override suspend fun getOrdersByBook(book: String): BookOrders? {
        val result = ordersMap[book]
        ordersLiveData.postValue(
            result?.let { Resource.Success(it) }
                ?: Resource.Error(errorType = ErrorType.Unknown)
        )
        return result
    }

    @WorkerThread
    override suspend fun saveBookOrders(bookOrders: BookOrders) {
        this.ordersMap[bookOrders.book] = bookOrders
        val result = bookOrders
        ordersLiveData.postValue(
            result?.let { Resource.Success(it) }
                ?: Resource.Error(errorType = ErrorType.Unknown)
        )
    }
}
