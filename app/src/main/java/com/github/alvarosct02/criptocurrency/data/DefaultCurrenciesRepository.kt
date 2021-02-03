package com.github.alvarosct02.criptocurrency.data

import androidx.lifecycle.liveData
import com.github.alvarosct02.criptocurrency.data.models.Book
import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.source.local.CurrenciesLocalSource
import com.github.alvarosct02.criptocurrency.data.source.remote.CurrenciesRemoteSource
import kotlinx.coroutines.delay

class DefaultCurrenciesRepository(
    private val api: CurrenciesRemoteSource,
    private val local: CurrenciesLocalSource
) : CurrenciesRepository {

    override fun getAllBooks() = liveData<Resource<List<Book>>> {
        emit(Resource.Loading())
        delay(500)
        emitSource(local.observeAllBooks())
        try {
            refreshAllBooks()
        } catch (e: Exception) {
            print("ASCT" + e.message)
            e.printStackTrace()
            emit(Resource.Error(errorType = ErrorType.Unknown))
        }
    }

    override fun getTickerByBook(book: String) = liveData<Resource<Ticker>> {
        emit(Resource.Loading())
        delay(200)
        emitSource(local.observeTickerByBook(book))
        try {
            refreshTickerByBook(book)
        } catch (e: Exception) {
            emit(Resource.Error(errorType = ErrorType.Unknown))
        }
    }

    override fun getOrdersByBook(book: String) = liveData<Resource<BookOrders>> {
        emit(Resource.Loading())
        delay(200)
        emitSource(local.observeOrdersByBook(book))
        try {
            refreshOrdersByBook(book)
        } catch (e: Exception) {
            emit(Resource.Error(errorType = ErrorType.Unknown))
        }
    }

    override suspend fun refreshAllBooks(): List<Book> {
        val books = api.getAllBooks()
        local.saveAllBooks(books)
        return books
    }

    override suspend fun refreshTickerByBook(book: String): Ticker {
        val ticker = api.getTickerByBook(book)
        local.saveBookTicker(ticker)
        return ticker
    }

    override suspend fun refreshOrdersByBook(book: String): BookOrders {
        val orders = api.getOrdersByBook(book)
        local.saveBookOrders(orders.copy(book = book))
        return orders
    }
}
