package com.github.alvarosct02.criptocurrency.features.currencyList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.alvarosct02.criptocurrency.Event
import com.github.alvarosct02.criptocurrency.ServiceLocator
import com.github.alvarosct02.criptocurrency.data.models.Book
import com.github.alvarosct02.criptocurrency.data.source.remote.CurrenciesRemoteDataSource
import kotlinx.coroutines.launch

class CurrencyListViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _currencyList = MutableLiveData<List<Book>>()
    val items: LiveData<List<Book>> = _currencyList

    private val _openBookEvent = MutableLiveData<Event<Book>>()
    val openBookEvent: LiveData<Event<Book>> = _openBookEvent


    //    TODO: Pending Refactor with DI
    private val currenciesRepository = ServiceLocator.currenciesRepository!!


    fun getAvailableBooks() = viewModelScope.launch {

        _isLoading.value = true

        try {
            _currencyList.value = currenciesRepository.getAllBooks()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            _isLoading.value = false
        }
    }

    fun onBookSelected(book: Book) {
        _openBookEvent.value = Event(book)
    }
}