package com.github.alvarosct02.criptocurrency.ui.currencyDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.github.alvarosct02.criptocurrency.data.CurrenciesRepository
import com.github.alvarosct02.criptocurrency.data.UIState
import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrencyDetailViewModel @Inject constructor(
    private val currenciesRepository: CurrenciesRepository
) : ViewModel() {

    private val _bookId = MutableLiveData<String>()

    val ticker: LiveData<UIState<Ticker>> = _bookId.switchMap { book ->
        if (book.isBlank()) liveData { emit(UIState.Loading<Ticker>()) }
        else Transformations.map(currenciesRepository.getTickerByBook(book)) {
            UIState.fromResource(it)
        }
    }

    val orders: LiveData<UIState<BookOrders>> = _bookId.switchMap { book ->
        if (book.isBlank()) liveData { emit(UIState.Loading<BookOrders>()) }
        else Transformations.map(currenciesRepository.getOrdersByBook(book)) {
            UIState.fromResource(it)
        }
    }

    fun setBook(book: String) {
        _bookId.postValue(book)
    }
}
