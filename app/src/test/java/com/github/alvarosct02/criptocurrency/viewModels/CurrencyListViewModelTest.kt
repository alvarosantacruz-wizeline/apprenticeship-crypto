package com.github.alvarosct02.criptocurrency.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.github.alvarosct02.criptocurrency.MainCoroutineRule
import com.github.alvarosct02.criptocurrency.data.DefaultCurrenciesRepository
import com.github.alvarosct02.criptocurrency.data.Resource
import com.github.alvarosct02.criptocurrency.data.models.Book
import com.github.alvarosct02.criptocurrency.ui.currencyList.CurrencyListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class CurrencyListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
//        viewModel = CurrencyListViewModel(repository)
//        mockObserver = mock(Observer::class.java) as Observer<Resource<List<Book>>>
//        viewModel.items.observeForever(mockObserver)

    }

    @Test
    fun getActiveTaskFromRepositoryAndLoadIntoView() = runBlockingTest {
//        mainCoroutineRule.pauseDispatcher()

//        viewModel.getAvailableBooks()

        // Then verify that the view was notified

//        verify(mockObserver, times(1)).onChanged(isA(Resource.Loading<List<Book>>::class.java))
////        verify(mockObserver, times(1)).onChanged(isA(Resource.Success<List<Book>>(null).javaClass))
//        verify(mockObserver, times(1)).onChanged(isA(javaClass<Resource.Error<List<Book>>>()))

//        viewModel.items.observeForTesting {
////            mainCoroutineRule.pauseDispatcher()
//            assertThat(viewModel.items.getOrAwaitValue().status, `is`(Status.LOADING))
//
////            mainCoroutineRule.resumeDispatcher()
//            assertThat(viewModel.items.getOrAwaitValue().status, `is`(Status.LOADING))
//        }
    }

}
