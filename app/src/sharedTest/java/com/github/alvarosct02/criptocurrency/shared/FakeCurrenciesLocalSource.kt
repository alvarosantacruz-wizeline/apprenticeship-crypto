package com.github.alvarosct02.criptocurrency.shared

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.alvarosct02.criptocurrency.data.ErrorType
import com.github.alvarosct02.criptocurrency.data.Resource
import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.models.TickerHistory
import com.github.alvarosct02.criptocurrency.data.models.TickerWithHistory
import com.github.alvarosct02.criptocurrency.data.models.Trade
import com.github.alvarosct02.criptocurrency.data.source.local.CurrenciesLocalSource
import kotlin.collections.set

class FakeCurrenciesLocalSource : CurrenciesLocalSource {

    private var booksList: List<Ticker>? = null
    private var tickerMap: MutableMap<String, Ticker> = mutableMapOf()
    private var ordersMap: MutableMap<String, BookOrders> = mutableMapOf()

    private var booksLiveData = MutableLiveData<Resource<List<Ticker>>>()
    private var tradesLiveData = MutableLiveData<Resource<List<Trade>>>()
    private var tickerHistoryLiveData = MutableLiveData<Resource<List<TickerHistory>>>()
    private var tickerLiveData = MutableLiveData<Resource<Ticker>>()
    private var ordersLiveData = MutableLiveData<Resource<BookOrders>>()

    override fun observeAllTickers(): LiveData<Resource<List<Ticker>>> {
        return booksLiveData
    }

    override fun observeAllTickersWithHistory(): LiveData<Resource<List<TickerWithHistory>>> {
        TODO("Not yet implemented")
    }

    @WorkerThread
    override suspend fun saveAllTickers(books: List<Ticker>) {
        this.booksList = books
        val result = books
        booksLiveData.postValue(
            result?.let { Resource.Success(it) }
                ?: Resource.Error(errorType = ErrorType.Unknown)
        )
    }

    override suspend fun saveAllTickersHistory(history: List<TickerHistory>) {
        TODO("Not yet implemented")
    }

    override fun observeTickerByBook(book: String): LiveData<Resource<Ticker>> {
        return tickerLiveData
    }

    override fun observeTickerHistoryByBook(book: String): LiveData<Resource<List<TickerHistory>>> {
        TODO("Not yet implemented")
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
    override suspend fun saveBookOrders(bookOrders: BookOrders) {
        this.ordersMap[bookOrders.book] = bookOrders
        val result = bookOrders
        ordersLiveData.postValue(
            result?.let { Resource.Success(it) }
                ?: Resource.Error(errorType = ErrorType.Unknown)
        )
    }

    override fun observeTradesByBook(book: String): LiveData<Resource<List<Trade>>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveTrades(trades: List<Trade>) {
        TODO("Not yet implemented")
    }
}
