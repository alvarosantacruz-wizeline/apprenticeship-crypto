package com.github.alvarosct02.criptocurrency.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.alvarosct02.criptocurrency.data.CurrenciesRepository
import com.github.alvarosct02.criptocurrency.data.DefaultCurrenciesRepository
import com.github.alvarosct02.criptocurrency.shared.FakeCurrenciesLocalSource
import com.github.alvarosct02.criptocurrency.shared.FakeCurrenciesRemoteSource
import com.github.alvarosct02.criptocurrency.shared.TestCoroutineRule
import com.github.alvarosct02.criptocurrency.shared.observeForTesting
import com.github.alvarosct02.criptocurrency.ui.currencyList.CurrencyListViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.spy

@ExperimentalCoroutinesApi
class CurrencyListViewModelTest {

    private val local = spy(FakeCurrenciesLocalSource())
    private val remote = spy(FakeCurrenciesRemoteSource())
    private lateinit var repository: CurrenciesRepository
    private lateinit var viewModel: CurrencyListViewModel

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
        viewModel = CurrencyListViewModel(repository)
    }

    @After
    fun tearDown() {
        remote.stopService()
    }

    @Test
    fun `items should return the elements from the api`() = testCoroutineRule.runBlockingTest {

        val subject = viewModel.items
        subject.observeForTesting {
            assertThat(subject.value?.isLoading).isTrue()
            advanceTimeBy(500)
            assertThat(subject.value?.data).hasSize(25)
        }
    }

}
