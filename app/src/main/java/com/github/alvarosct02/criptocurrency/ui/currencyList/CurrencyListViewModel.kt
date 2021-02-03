package com.github.alvarosct02.criptocurrency.ui.currencyList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.alvarosct02.criptocurrency.Event
import com.github.alvarosct02.criptocurrency.data.CurrenciesRepository
import com.github.alvarosct02.criptocurrency.data.UIState
import com.github.alvarosct02.criptocurrency.data.models.Book
import kotlinx.coroutines.launch

class CurrencyListViewModel(
    private val currenciesRepository: CurrenciesRepository
) : ViewModel() {

    val items = Transformations.map(currenciesRepository.getAllBooks()) {
        UIState.fromResource(it)
    }

    private val _openBookEvent = MutableLiveData<Event<Book>>()
    val openBookEvent: LiveData<Event<Book>> = _openBookEvent

    fun refreshAllBooks() = viewModelScope.launch {
        currenciesRepository.refreshAllBooks()
    }

    fun onBookSelected(book: Book) {
        _openBookEvent.postValue(Event(book))
    }
}