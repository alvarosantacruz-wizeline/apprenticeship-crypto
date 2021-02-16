package com.github.alvarosct02.criptocurrency.ui.currencyDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.github.alvarosct02.criptocurrency.data.CurrenciesRepository
import com.github.alvarosct02.criptocurrency.data.UIState
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.models.TickerHistory
import com.github.alvarosct02.criptocurrency.data.models.Trade
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class CurrencyDetailViewModel @Inject constructor(
    private val currenciesRepository: CurrenciesRepository
) : ViewModel() {

    private val _bookId = MutableLiveData<String>()

    val ticker: LiveData<UIState<Ticker>> = _bookId.switchMap { book ->
        if (book.isBlank()) MutableLiveData(UIState.Loading<Ticker>())
        else Transformations.map(currenciesRepository.getTickerByBook(book)) {
            UIState.fromResource(it)
        }
    }

    val trades: LiveData<UIState<List<Trade>>> = _bookId.switchMap { book ->
        if (book.isBlank()) MutableLiveData(UIState.Loading<List<Trade>>())
        else Transformations.map(currenciesRepository.getTradesByBook(book)) {
            UIState.fromResource(it)
        }
    }

    val tickerHistory: LiveData<UIState<List<TickerHistory>>> = _bookId.switchMap { book ->
        if (book.isBlank()) MutableLiveData(UIState.Loading<List<TickerHistory>>())
        else Transformations.map(currenciesRepository.getTickerHistoryByBook(book)) {
            UIState.fromResource(it)
        }
    }

    fun refreshTickerHistory() = viewModelScope.launch {
        val bookId = _bookId.value ?: return@launch
        currenciesRepository.refreshTickerHistoryByBook(bookId)
    }

    fun refreshTicker() = viewModelScope.launch {
        val bookId = _bookId.value ?: return@launch
        currenciesRepository.refreshTickerByBook(bookId)
    }

    fun setBook(book: String) {
        _bookId.postValue(book)
    }
}
