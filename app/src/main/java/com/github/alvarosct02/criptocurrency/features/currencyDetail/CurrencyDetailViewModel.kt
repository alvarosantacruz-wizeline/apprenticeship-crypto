package com.github.alvarosct02.criptocurrency.features.currencyDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.alvarosct02.criptocurrency.data.DefaultCurrenciesRepository
import com.github.alvarosct02.criptocurrency.data.Resource
import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrencyDetailViewModel(
    private val currenciesRepository: DefaultCurrenciesRepository
) : ViewModel() {

    private val _ticker = MutableLiveData<Resource<Ticker>>()
    val ticker = _ticker

    private val _orders = MutableLiveData<Resource<BookOrders>>()
    val orders = _orders


    fun getBookTicker(book: String) = viewModelScope.launch() {
        _ticker.postValue(Resource.Loading(null))
        try {
            _ticker.postValue(currenciesRepository.getTickerByBook(book))
        } catch (e: Exception) {
            _ticker.postValue(Resource.Error(e.message ?: "", data = null))
        }
    }

    fun getBookOrders(book: String) = viewModelScope.launch {
        _orders.postValue(Resource.Loading(null))
        try {
            _orders.postValue(currenciesRepository.getOrdersByBook(book))
        } catch (e: Exception) {
            _orders.postValue(Resource.Error(e.message ?: "", data = null))
        }
    }
}
