package com.github.alvarosct02.criptocurrency.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.source.remote.retrofit.BitsoWrapper
import com.github.alvarosct02.criptocurrency.shared.CriptoDataRule
import com.github.alvarosct02.criptocurrency.shared.TestCoroutineRule
import com.github.alvarosct02.criptocurrency.shared.getOrAwaitValue
import com.github.alvarosct02.criptocurrency.ui.currencyList.CurrencyListViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

@Ignore
@ExperimentalCoroutinesApi
class CurrencyListViewModelTest {

    private lateinit var viewModel: CurrencyListViewModel

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val criptoDataRule = CriptoDataRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        viewModel = CurrencyListViewModel(criptoDataRule.repository)
    }

    @Test
    fun `items should return the elements from the api`() = testCoroutineRule.runBlockingTest {
        criptoDataRule.apply {
            val tickers = listOf<Ticker>(mock(Ticker::class.java), mock(Ticker::class.java))
            val response = BitsoWrapper(success = true, payload = tickers)
            Mockito.`when`(service.listAvailableBooks()).thenReturn(response)
            Mockito.`when`(tickerDao.insertAll(tickers)).thenReturn(Unit)
            Mockito.`when`(tickerDao.observeAll()).thenReturn(MutableLiveData(tickers))

            val subject = viewModel.items
            assertThat(subject.getOrAwaitValue().isLoading).isTrue()
            assertThat(subject.getOrAwaitValue().data).isEqualTo(tickers)
        }
    }
}
