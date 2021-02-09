package com.github.alvarosct02.criptocurrency.data.source.local

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.github.alvarosct02.criptocurrency.data.ErrorType
import com.github.alvarosct02.criptocurrency.data.Resource
import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.source.local.room.AppDatabase

class CurrenciesRoomSource(
    private val appDatabase: AppDatabase
) : CurrenciesLocalSource {

    override fun observeAllTickers(): LiveData<Resource<List<Ticker>>> {
        return Transformations.map(appDatabase.tickerDao().observeAll()) { result ->
            if (result.isNullOrEmpty()) {
                Resource.Error(errorType = ErrorType.Unknown)
            } else {
                Resource.Success(result)
            }
        }
    }

    @WorkerThread
    override suspend fun getAllTickers(): List<Ticker> {
        return appDatabase.tickerDao().getAll()
    }

    @WorkerThread
    override suspend fun saveAllTickers(books: List<Ticker>) {
        appDatabase.tickerDao().insertAll(books)
    }

    override fun observeTickerByBook(book: String): LiveData<Resource<Ticker>> {
        return Transformations.map(appDatabase.tickerDao().observeById(book)) { result ->
            if (result == null) {
                Resource.Error(errorType = ErrorType.Unknown)
            } else {
                Resource.Success(result)
            }
        }
    }

    @WorkerThread
    override suspend fun getTickerByBook(book: String): Ticker {
        return appDatabase.tickerDao().getById(book)
    }

    @WorkerThread
    override suspend fun saveBookTicker(ticker: Ticker) {
        appDatabase.tickerDao().insert(ticker)
    }

    override fun observeOrdersByBook(book: String): LiveData<Resource<BookOrders>> {
        return Transformations.map(appDatabase.ordersDao().observeById(book)) { result ->
            if (result == null) {
                Resource.Error(errorType = ErrorType.Unknown)
            } else {
                Resource.Success(result)
            }
        }
    }

    @WorkerThread
    override suspend fun getOrdersByBook(book: String): BookOrders {
        return appDatabase.ordersDao().getById(book)
    }

    @WorkerThread
    override suspend fun saveBookOrders(bookOrders: BookOrders) {
        appDatabase.ordersDao().insert(bookOrders)
    }
}
