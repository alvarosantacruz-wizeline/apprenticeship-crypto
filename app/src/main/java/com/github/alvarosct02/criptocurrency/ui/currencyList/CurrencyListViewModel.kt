package com.github.alvarosct02.criptocurrency.ui.currencyList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.github.alvarosct02.criptocurrency.Event
import com.github.alvarosct02.criptocurrency.data.CurrenciesRepository
import com.github.alvarosct02.criptocurrency.data.UIState
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrencyListViewModel @Inject constructor(
    private val currenciesRepository: CurrenciesRepository
) : ViewModel() {

    val items = Transformations.map(currenciesRepository.getAllTickers()) {
        UIState.fromResource(it)
    }

    suspend fun refreshAllTickers() {
        currenciesRepository.refreshAllTickers()
    }

    private val _openBookEvent = MutableLiveData<Event<Ticker>>()
    val openBookEvent: LiveData<Event<Ticker>> = _openBookEvent

    fun onBookSelected(book: Ticker) {
        _openBookEvent.postValue(Event(book))
    }
}
