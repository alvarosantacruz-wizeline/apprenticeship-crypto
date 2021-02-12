package com.github.alvarosct02.criptocurrency.ui.currencyList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.alvarosct02.criptocurrency.Event
import com.github.alvarosct02.criptocurrency.data.CurrenciesRepository
import com.github.alvarosct02.criptocurrency.data.UIState
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.models.TickerHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class CurrencyListViewModel @Inject constructor(
    private val currenciesRepository: CurrenciesRepository
) : ViewModel() {

    var tickerHistoryRefreshed = false

    val items = Transformations.map(currenciesRepository.getAllTickers()) {
        refreshAllTickerHistoryOnlyOnce()
        UIState.fromResource(it)
    }

    val tickerHistoryMap: MutableMap<String, List<TickerHistory>> = mutableMapOf()

    suspend fun refreshAllTickers() {
        currenciesRepository.refreshAllTickers()
    }

    private fun refreshAllTickerHistoryOnlyOnce() {
        if (tickerHistoryRefreshed) return
        val bookList: List<Ticker> = items.value?.data ?: listOf()
        if (bookList.isEmpty()) return
        tickerHistoryRefreshed = true
        viewModelScope.launch {
            bookList.forEach {
                currenciesRepository.refreshTickerHistoryByBook(it.book)
            }
        }
    }

    fun tickerHistoryByBook(book: String): LiveData<UIState<List<TickerHistory>>> {
        val resourceLiveData = currenciesRepository.getTickerHistoryByBook(book, shouldRefresh = false)
        return Transformations.map(resourceLiveData) {
            UIState.fromResource(it)
        }
    }

    private val _openBookEvent = MutableLiveData<Event<Ticker>>()
    val openBookEvent: LiveData<Event<Ticker>> = _openBookEvent

    fun onBookSelected(book: Ticker) {
        _openBookEvent.postValue(Event(book))
    }
}
