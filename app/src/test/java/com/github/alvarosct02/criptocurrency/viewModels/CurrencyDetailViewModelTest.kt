package com.github.alvarosct02.criptocurrency.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.models.Trade
import com.github.alvarosct02.criptocurrency.data.source.remote.retrofit.BitsoWrapper
import com.github.alvarosct02.criptocurrency.shared.CriptoDataRule
import com.github.alvarosct02.criptocurrency.shared.TestCoroutineRule
import com.github.alvarosct02.criptocurrency.shared.getOrAwaitValue
import com.github.alvarosct02.criptocurrency.ui.currencyDetail.CurrencyDetailViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class CurrencyDetailViewModelTest {

    private lateinit var viewModel: CurrencyDetailViewModel

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val criptoDataRule = CriptoDataRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        viewModel = CurrencyDetailViewModel(criptoDataRule.repository)
    }

    @Test
    fun `ticker should return the elements from the api`() = testCoroutineRule.runBlockingTest {
        criptoDataRule.apply {
            val bookId = "BTC_MXN"
            val ticker = Ticker(book = bookId, high = "670855.71")
            val response = BitsoWrapper(success = true, payload = ticker)
            Mockito.`when`(service.getBookDetail(bookId)).thenReturn(response)
            Mockito.`when`(tickerDao.insert(ticker)).thenReturn(Unit)
            Mockito.`when`(tickerDao.observeById(bookId)).thenReturn(MutableLiveData(ticker))

            viewModel.setBook(bookId)
            val subject = viewModel.ticker
            assertThat(subject.getOrAwaitValue().isLoading).isTrue()
            assertThat(subject.getOrAwaitValue().data).isEqualTo(ticker)
        }
    }

    @Test
    fun `orders should return the elements from the api`() = testCoroutineRule.runBlockingTest {
        criptoDataRule.apply {
            val bookId = "BTC_MXN"
            val trades = listOf<Trade>()
            val response = BitsoWrapper(success = true, payload = trades)
            Mockito.`when`(service.getTrades(bookId)).thenReturn(response)
            Mockito.`when`(tradeDao.insertAll(trades)).thenReturn(Unit)
            Mockito.`when`(tradeDao.observeById(bookId)).thenReturn(MutableLiveData(trades))

            viewModel.setBook(bookId)
            val subject = viewModel.trades
            assertThat(subject.getOrAwaitValue().isLoading).isTrue()
            assertThat(subject.getOrAwaitValue().data).isEqualTo(trades)
        }
    }
}
