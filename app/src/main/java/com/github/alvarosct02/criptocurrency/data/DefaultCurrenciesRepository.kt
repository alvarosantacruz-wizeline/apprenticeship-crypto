package com.github.alvarosct02.criptocurrency.data

import androidx.lifecycle.liveData
import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.models.TickerHistory
import com.github.alvarosct02.criptocurrency.data.models.Trade
import com.github.alvarosct02.criptocurrency.data.source.local.CurrenciesLocalSource
import com.github.alvarosct02.criptocurrency.data.source.remote.CurrenciesRemoteSource
import kotlinx.coroutines.delay

class DefaultCurrenciesRepository(
    private val remote: CurrenciesRemoteSource,
    private val local: CurrenciesLocalSource
) : CurrenciesRepository {

    override fun getAllTickers() = liveData<Resource<List<Ticker>>> {
        emit(Resource.Loading())
        delay(200)
        emitSource(local.observeAllTickers())
        try {
            refreshAllTickers()
        } catch (e: Exception) {
            emit(Resource.Error(errorType = ErrorType.Unknown))
        }
    }

    override fun getTickerHistoryByBook(book: String, shouldRefresh: Boolean) =
        liveData<Resource<List<TickerHistory>>> {
            emit(Resource.Loading())
            emitSource(local.observeTickerHistoryByBook(book))
            try {
                if (shouldRefresh) refreshTickerHistoryByBook(book)
            } catch (e: Exception) {
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
        emitSource(local.observeOrdersByBook(book))
        try {
            refreshOrdersByBook(book)
        } catch (e: Exception) {
            emit(Resource.Error(errorType = ErrorType.Unknown))
        }
    }

    override fun getTradesByBook(book: String) = liveData<Resource<List<Trade>>> {
        emit(Resource.Loading())
        delay(200)
        emitSource(local.observeTradesByBook(book))
        try {
            refreshTradesByBook(book)
        } catch (e: Exception) {
            emit(Resource.Error(errorType = ErrorType.Unknown))
        }
    }

    override suspend fun refreshTickerHistoryByBook(book: String): List<TickerHistory>? {
        return try {
            val tickerHistory = remote.getTickerHistoryByBook(book)
            local.saveAllTickersHistory(tickerHistory)
            tickerHistory
        } catch (e: Exception) {
            print("ASCT" + e.message)
            e.printStackTrace()
            null
        }
    }

    override suspend fun refreshAllTickers(): List<Ticker>? {
        return try {
            val books = remote.getAllBooks()
            local.saveAllTickers(books)
            books
        } catch (e: Exception) {
            print("ASCT" + e.message)
            e.printStackTrace()
            null
        }
    }

    @Throws
    override suspend fun refreshTickerByBook(book: String): Ticker? {
        return try {
            val ticker = remote.getTickerByBook(book)
            local.saveBookTicker(ticker)
            ticker
        } catch (e: Exception) {
            print("ASCT" + e.message)
            e.printStackTrace()
            null
        }
    }

    override suspend fun refreshOrdersByBook(book: String): BookOrders? {
        return try {
            val orders = remote.getOrdersByBook(book)
            local.saveBookOrders(orders.copy(book = book))
            orders
        } catch (e: Exception) {
            print("ASCT" + e.message)
            e.printStackTrace()
            null
        }
    }

    override suspend fun refreshTradesByBook(book: String): List<Trade>? {
        return try {
            val trades = remote.getTrades(book)
            local.saveTrades(trades)
            trades
        } catch (e: Exception) {
            print("ASCT" + e.message)
            e.printStackTrace()
            null
        }
    }
}
