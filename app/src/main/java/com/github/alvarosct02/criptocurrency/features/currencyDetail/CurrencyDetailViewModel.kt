package com.github.alvarosct02.criptocurrency.features.currencyDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.alvarosct02.criptocurrency.ServiceLocator
import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrencyDetailViewModel : ViewModel() {

    //    TODO: Split in different loaders
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _ticker = MutableLiveData<Ticker>()
    val ticker: LiveData<Ticker> = _ticker

    private val _orders = MutableLiveData<BookOrders>()
    val orders: LiveData<BookOrders> = _orders


    //    TODO: Pending Refactor with DI
    private val currenciesRepository = ServiceLocator.currenciesRepository!!


    fun getBookTicker(book: String) = viewModelScope.launch(Dispatchers.IO) {

        _isLoading.postValue(true)

        try {
            _ticker.postValue(currenciesRepository.getTickerByBook(book))
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            _isLoading.postValue(false)
        }
    }

    fun getBookOrders(book: String) = viewModelScope.launch(Dispatchers.IO) {

        _isLoading.postValue(true)

        try {
            _orders.postValue(currenciesRepository.getOrdersByBook(book))
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            _isLoading.postValue(false)
        }
    }
}

sealed class CurrencyDetailState(

) {

}