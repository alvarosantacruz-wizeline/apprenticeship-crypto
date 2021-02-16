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
import com.github.alvarosct02.criptocurrency.data.models.Trade
import com.github.alvarosct02.criptocurrency.data.source.local.room.dao.OrdersDao
import com.github.alvarosct02.criptocurrency.data.source.local.room.dao.TickerDao
import com.github.alvarosct02.criptocurrency.data.source.local.room.dao.TickerHistoryDao
import com.github.alvarosct02.criptocurrency.data.source.local.room.dao.TradeDao
import java.util.Calendar

class CurrenciesRoomSource(
    private val tickerDao: TickerDao,
    private val tradeDao: TradeDao,
    private val tickerHistoryDao: TickerHistoryDao,
    private val ordersDao: OrdersDao,
) : CurrenciesLocalSource {

    override fun observeAllTickers(): LiveData<Resource<List<Ticker>>> {
        return Transformations.map(tickerDao.observeAll()) { result ->
            if (result.isNullOrEmpty()) {
                Resource.Error(errorType = ErrorType.Unknown)
            } else {
                Resource.Success(result)
            }
        }
    }

    override fun observeAllTickersWithHistory(): LiveData<Resource<List<TickerWithHistory>>> {
        return Transformations.map(tickerDao.observeAllWithHistory()) { result ->
            if (result.isNullOrEmpty()) {
                Resource.Error(errorType = ErrorType.Unknown)
            } else {
                Resource.Success(result)
            }
        }
    }

    @WorkerThread
    override suspend fun saveAllTickers(books: List<Ticker>) {
        tickerDao.insertAll(books)
    }

    @WorkerThread
    override suspend fun saveAllTickersHistory(history: List<TickerHistory>) {
        tickerHistoryDao.insertAll(history)
    }

    override fun observeTickerByBook(book: String): LiveData<Resource<Ticker>> {
        return Transformations.map(tickerDao.observeById(book)) { result ->
            if (result == null) {
                Resource.Error(errorType = ErrorType.Unknown)
            } else {
                Resource.Success(result)
            }
        }
    }

    override fun observeTickerHistoryByBook(book: String): LiveData<Resource<List<TickerHistory>>> {
        val time24HoursAgoInMillis = Calendar.getInstance().timeInMillis - 24 * 60 * 60 * 1000
        return Transformations.map(
            tickerHistoryDao.observeById(book, time24HoursAgoInMillis.toString())
        ) { result ->
            if (result == null) {
                Resource.Error(errorType = ErrorType.Unknown)
            } else {
                Resource.Success(result)
            }
        }
    }

    @WorkerThread
    override suspend fun saveBookTicker(ticker: Ticker) {
        tickerDao.insert(ticker)
    }

    override fun observeOrdersByBook(book: String): LiveData<Resource<BookOrders>> {
        return Transformations.map(ordersDao.observeById(book)) { result ->
            if (result == null) {
                Resource.Error(errorType = ErrorType.Unknown)
            } else {
                Resource.Success(result)
            }
        }
    }

    @WorkerThread
    override suspend fun saveBookOrders(bookOrders: BookOrders) {
        ordersDao.insert(bookOrders)
    }

    override fun observeTradesByBook(book: String): LiveData<Resource<List<Trade>>> {
        return Transformations.map(tradeDao.observeById(book)) { result ->
            if (result == null) {
                Resource.Error(errorType = ErrorType.Unknown)
            } else {
                Resource.Success(result)
            }
        }
    }

    @WorkerThread
    override suspend fun saveTrades(trades: List<Trade>) {
        tradeDao.insertAll(trades)
    }
}
