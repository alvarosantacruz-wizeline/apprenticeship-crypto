package com.github.alvarosct02.criptocurrency.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.alvarosct02.criptocurrency.data.DefaultCurrenciesRepository
import com.github.alvarosct02.criptocurrency.data.models.Book
import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.source.remote.CurrenciesRetrofitSource
import com.github.alvarosct02.criptocurrency.data.source.remote.retrofit.BitsoWrapper
import com.github.alvarosct02.criptocurrency.data.source.remote.retrofit.CurrenciesService
import com.github.alvarosct02.criptocurrency.shared.FakeCurrenciesLocalSource
import com.github.alvarosct02.criptocurrency.shared.TestCoroutineRule
import com.github.alvarosct02.criptocurrency.shared.observeForTesting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CurrencyRepositoryTest {

    private lateinit var repository: DefaultCurrenciesRepository
    private val service = mock(CurrenciesService::class.java)
    private val local = spy(FakeCurrenciesLocalSource())

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        repository = DefaultCurrenciesRepository(
            api = CurrenciesRetrofitSource(service),
            local = local
        )
    }

    @Test
    fun `when availableBooks service is success then store and return from local`() =
        testCoroutineRule.runBlockingTest {
            val bookList = emptyList<Book>()
            val response = BitsoWrapper(success = true, payload = bookList)
            `when`(service.listAvailableBooks()).thenReturn(response)

            repository.getAllBooks().observeForTesting {
                advanceTimeBy(2000)
            }

            verify(service, times(1)).listAvailableBooks()
            verify(local, times(1)).saveAllBooks(bookList)
            verify(local, times(1)).observeAllBooks()
        }

    @Test
    fun `when availableBooks service fails then do not store and return from local`() =
        testCoroutineRule.runBlockingTest {
            val bookList = emptyList<Book>()
            val response = BitsoWrapper(success = true, payload = bookList)
            `when`(service.listAvailableBooks()).thenReturn(null)

            repository.getAllBooks().observeForTesting {
                advanceTimeBy(2000)
            }

            verify(service, times(1)).listAvailableBooks()
            verify(local, never()).saveAllBooks(bookList)
            verify(local, times(1)).observeAllBooks()
        }

    @Test
    fun `when ticker service is success then store and return from local`() =
        testCoroutineRule.runBlockingTest {
            val bookId = "BTC_MXN"
            val ticker = Ticker(book = bookId)
            val response = BitsoWrapper(success = true, payload = ticker)
            `when`(service.getBookDetail(bookId)).thenReturn(response)

            repository.getTickerByBook(bookId).observeForTesting {
                advanceTimeBy(2000)
            }

            verify(service, times(1)).getBookDetail(bookId)
            verify(local, times(1)).saveBookTicker(ticker)
            verify(local, times(1)).observeTickerByBook(bookId)
        }


    @Test
    fun `when ticker service fails then do not store and return from local`() =
        testCoroutineRule.runBlockingTest {
            val bookId = "BTC_MXN"
            val ticker = Ticker(book = bookId)
            `when`(service.getBookDetail(bookId)).thenReturn(null)

            repository.getTickerByBook(bookId).observeForTesting {
                advanceTimeBy(2000)
            }

            verify(service, times(1)).getBookDetail(bookId)
            verify(local, never()).saveBookTicker(ticker)
            verify(local, times(1)).observeTickerByBook(bookId)
        }

    @Test
    fun `when orders service is success then store and return from local`() =
        testCoroutineRule.runBlockingTest {
            val bookId = "BTC_MXN"
            val orders = BookOrders(book = bookId)
            val response = BitsoWrapper(success = true, payload = orders)
            `when`(service.getBookOrders(bookId)).thenReturn(response)

            repository.getOrdersByBook(bookId).observeForTesting {
                advanceTimeBy(2000)
            }

            verify(service, times(1)).getBookOrders(bookId)
            verify(local, times(1)).saveBookOrders(orders)
            verify(local, times(1)).observeOrdersByBook(bookId)
        }

    @Test
    fun `when orders service fails then do not store and return from local`() =
        testCoroutineRule.runBlockingTest {
            val bookId = "BTC_MXN"
            val orders = BookOrders(book = bookId)
            `when`(service.getBookOrders(bookId)).thenReturn(null)

            repository.getOrdersByBook(bookId).observeForTesting {
                advanceTimeBy(2000)
            }

            verify(service, times(1)).getBookOrders(bookId)
            verify(local, never()).saveBookOrders(orders)
            verify(local, times(1)).observeOrdersByBook(bookId)
        }
}