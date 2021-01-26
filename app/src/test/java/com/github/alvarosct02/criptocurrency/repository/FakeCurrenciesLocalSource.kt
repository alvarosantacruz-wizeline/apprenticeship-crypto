package com.github.alvarosct02.criptocurrency.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.github.alvarosct02.criptocurrency.data.models.Book
import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.source.local.CurrenciesLocalSource
import org.koin.test.mock.declareMock
import org.mockito.Mock

class FakeCurrenciesLocalSource : CurrenciesLocalSource {

    private var booksList: List<Book>? = null
    private var tickerMap: MutableMap<String,Ticker> = mutableMapOf()
    private var ordersMap: MutableMap<String,BookOrders> = mutableMapOf()

    override fun observeAllBooks(): LiveData<List<Book>> {
        TODO("Not yet implemented")
    }

    @WorkerThread
    override suspend fun getAllBooks(): List<Book>? {
        return booksList
    }

    @WorkerThread
    override suspend fun saveAllBooks(books: List<Book>) {
        this.booksList = books
    }

    override fun observeTickerByBook(book: String): LiveData<Ticker> {
        TODO("Not yet implemented")
    }

    @WorkerThread
    override suspend fun getTickerByBook(book: String): Ticker? {
        return tickerMap[book]
    }

    @WorkerThread
    override suspend fun saveBookTicker(ticker: Ticker) {
        this.tickerMap[ticker.book] = ticker
    }

    override fun observeOrdersByBook(book: String): LiveData<BookOrders> {
        TODO("Not yet implemented")
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