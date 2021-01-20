package com.github.alvarosct02.criptocurrency.features.currencyList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.alvarosct02.criptocurrency.Event
import com.github.alvarosct02.criptocurrency.ServiceLocator
import com.github.alvarosct02.criptocurrency.data.models.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrencyListViewModel : ViewModel() {

    //    TODO: Pending Refactor with DI
    private val currenciesRepository = ServiceLocator.currenciesRepository!!

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _currencyList = MutableLiveData<List<Book>>()
    val items: LiveData<List<Book>> = _currencyList

    private val _openBookEvent = MutableLiveData<Event<Book>>()
    val openBookEvent: LiveData<Event<Book>> = _openBookEvent


    fun getAvailableBooks() = viewModelScope.launch(Dispatchers.IO) {

        try {
            _currencyList.postValue(currenciesRepository.getAllBooks())
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            _isLoading.postValue(false)
        }
    }

    fun onBookSelected(book: Book) {
        _openBookEvent.postValue(Event(book))
    }
}