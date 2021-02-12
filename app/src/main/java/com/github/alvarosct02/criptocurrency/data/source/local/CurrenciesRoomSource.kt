package com.github.alvarosct02.criptocurrency.data.source.local

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.github.alvarosct02.criptocurrency.data.ErrorType
import com.github.alvarosct02.criptocurrency.data.Resource
import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.models.TickerHistory
import com.github.alvarosct02.criptocurrency.data.models.TickerWithHistory
import com.github.alvarosct02.criptocurrency.data.source.local.room.AppDatabase
import java.util.Calendar

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

    override fun observeAllTickersWithHistory(): LiveData<Resource<List<TickerWithHistory>>> {
        return Transformations.map(appDatabase.tickerDao().observeAllWithHistory()) { result ->
            if (result.isNullOrEmpty()) {
                Resource.Error(errorType = ErrorType.Unknown)
            } else {
                Resource.Success(result)
            }
        }
    }

    @WorkerThread
    override suspend fun saveAllTickers(books: List<Ticker>) {
        appDatabase.tickerDao().insertAll(books)
    }

    override suspend fun saveAllTickersHistory(history: List<TickerHistory>) {
        appDatabase.tickerHistoryDao().insertAll(history)
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

    override fun observeTickerHistoryByBook(book: String): LiveData<Resource<List<TickerHistory>>> {
        val time24HoursAgoInMillis = Calendar.getInstance().timeInMillis - 24 * 60 * 60 * 1000
        return Transformations.map(appDatabase.tickerHistoryDao().observeById(book, time24HoursAgoInMillis.toString())) { result ->
            if (result == null) {
                Resource.Error(errorType = ErrorType.Unknown)
            } else {
                Resource.Success(result)
            }
        }
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
    override suspend fun saveBookOrders(bookOrders: BookOrders) {
        appDatabase.ordersDao().insert(bookOrders)
    }
}
