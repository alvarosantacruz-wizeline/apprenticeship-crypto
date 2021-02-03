package com.github.alvarosct02.criptocurrency.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.alvarosct02.criptocurrency.data.CurrenciesRepository
import com.github.alvarosct02.criptocurrency.data.DefaultCurrenciesRepository
import com.github.alvarosct02.criptocurrency.shared.observeForTesting
import com.github.alvarosct02.criptocurrency.ui.currencyDetail.CurrencyDetailViewModel
import com.github.alvarosct02.criptocurrency.shared.FakeCurrenciesLocalSource
import com.github.alvarosct02.criptocurrency.shared.FakeCurrenciesRemoteSource
import com.github.alvarosct02.criptocurrency.shared.TestCoroutineRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.spy

@ExperimentalCoroutinesApi
class CurrencyDetailViewModelTest {

    private val local = spy(FakeCurrenciesLocalSource())
    private val remote = spy(FakeCurrenciesRemoteSource())
    private lateinit var repository: CurrenciesRepository
    private lateinit var viewModel: CurrencyDetailViewModel

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        remote.createService()
        repository = DefaultCurrenciesRepository(
            local = local,
            api = remote
        )
        viewModel = CurrencyDetailViewModel(repository)
    }

    @After
    fun tearDown() {
        remote.stopService()
    }

    @Test
    fun `ticker should return the elements from the api`() = testCoroutineRule.runBlockingTest {

        viewModel.setBook("BTC-USD")
        val subject = viewModel.ticker
        subject.observeForTesting {
            assertThat(subject.value?.isLoading).isTrue()
            advanceTimeBy(500)
            assertThat(subject.value?.data?.high).isEqualTo("670855.71")
        }
    }

    @Test
    fun `orders should return the elements from the api`() = testCoroutineRule.runBlockingTest {

        viewModel.setBook("BTC-USD")
        val subject = viewModel.orders
        subject.observeForTesting {
            assertThat(subject.value?.isLoading).isTrue()
            advanceTimeBy(500)
            assertThat(subject.value?.data?.asks?.size).isGreaterThan(5)
        }
    }

}
