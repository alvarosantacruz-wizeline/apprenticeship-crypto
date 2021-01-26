package com.github.alvarosct02.criptocurrency.features.currencyList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.alvarosct02.criptocurrency.Event
import com.github.alvarosct02.criptocurrency.data.DefaultCurrenciesRepository
import com.github.alvarosct02.criptocurrency.data.Resource
import com.github.alvarosct02.criptocurrency.data.models.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrencyListViewModel(
    private val currenciesRepository: DefaultCurrenciesRepository
) : ViewModel() {

    private val _currencyList = MutableLiveData<Resource<List<Book>>>()
    val items: LiveData<Resource<List<Book>>> = _currencyList

    private val _openBookEvent = MutableLiveData<Event<Book>>()
    val openBookEvent: LiveData<Event<Book>> = _openBookEvent


    fun getAvailableBooks() = viewModelScope.launch {
        _currencyList.postValue(Resource.Loading(null))
        try {
            _currencyList.postValue(currenciesRepository.getAllBooks())
        } catch (e: Exception) {
            _currencyList.postValue(Resource.Error(e.message ?: "", data = null))
        }
    }

    fun onBookSelected(book: Book) {
        _openBookEvent.postValue(Event(book))
    }
}