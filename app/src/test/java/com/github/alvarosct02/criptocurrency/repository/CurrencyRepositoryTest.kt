package com.github.alvarosct02.criptocurrency.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.models.Trade
import com.github.alvarosct02.criptocurrency.data.source.remote.retrofit.BitsoWrapper
import com.github.alvarosct02.criptocurrency.shared.CriptoDataRule
import com.github.alvarosct02.criptocurrency.shared.TestCoroutineRule
import com.github.alvarosct02.criptocurrency.shared.observeForTesting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.never
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CurrencyRepositoryTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val criptoDataRule = CriptoDataRule()

    @Test
    fun `when availableBooks service is success then store and return from local`() =
        testCoroutineRule.runBlockingTest {
            criptoDataRule.apply {
                val bookList = emptyList<Ticker>()
                val response = BitsoWrapper(success = true, payload = bookList)
                `when`(service.listAvailableBooks()).thenReturn(response)
                `when`(tickerDao.observeAll()).thenReturn(MutableLiveData(bookList))

                repository.getAllTickers().observeForTesting {
                    advanceTimeBy(2000)
                }

                verify(service, times(1)).listAvailableBooks()
                verify(local, times(1)).saveAllTickers(bookList)
                verify(local, times(1)).observeAllTickers()
            }
        }

    @Test
    fun `when availableBooks service fails then do not store and return from local`() =
        testCoroutineRule.runBlockingTest {
            criptoDataRule.apply {
                val bookList = emptyList<Ticker>()
                val response = BitsoWrapper(success = true, payload = bookList)
                `when`(service.listAvailableBooks()).thenReturn(null)
                `when`(tickerDao.observeAll()).thenReturn(MutableLiveData(bookList))

                repository.getAllTickers().observeForTesting {
                    advanceTimeBy(2000)
                }

                verify(service, times(1)).listAvailableBooks()
                verify(local, never()).saveAllTickers(bookList)
                verify(local, times(1)).observeAllTickers()
            }
        }

    @Test
    fun `when ticker service is success then store and return from local`() =
        testCoroutineRule.runBlockingTest {
            criptoDataRule.apply {
                val bookId = "BTC_MXN"
                val ticker = Ticker(book = bookId)
                val response = BitsoWrapper(success = true, payload = ticker)
                `when`(service.getBookDetail(bookId)).thenReturn(response)
                `when`(tickerDao.observeById(bookId)).thenReturn(MutableLiveData(ticker))

                repository.getTickerByBook(bookId).observeForTesting {
                    advanceTimeBy(2000)
                }

                verify(service, times(1)).getBookDetail(bookId)
                verify(local, times(1)).saveBookTicker(ticker)
                verify(local, times(1)).observeTickerByBook(bookId)
            }
        }

    @Test
    fun `when ticker service fails then do not store and return from local`() =
        testCoroutineRule.runBlockingTest {
            criptoDataRule.apply {
                val bookId = "BTC_MXN"
                val ticker = Ticker(book = bookId)
                `when`(service.getBookDetail(bookId)).thenReturn(null)
                `when`(tickerDao.observeById(bookId)).thenReturn(MutableLiveData(ticker))

                repository.getTickerByBook(bookId).observeForTesting {
                    advanceTimeBy(2000)
                }

                verify(service, times(1)).getBookDetail(bookId)
                verify(local, never()).saveBookTicker(ticker)
                verify(local, times(1)).observeTickerByBook(bookId)
            }
        }

    @Test
    fun `when orders service is success then store and return from local`() =
        testCoroutineRule.runBlockingTest {
            criptoDataRule.apply {
                val bookId = "BTC_MXN"
                val trades = listOf<Trade>()
                val response = BitsoWrapper(success = true, payload = trades)
                `when`(service.getTrades(bookId)).thenReturn(response)
                `when`(tradeDao.observeById(bookId)).thenReturn(MutableLiveData(trades))

                repository.getTradesByBook(bookId).observeForTesting {
                    advanceTimeBy(2000)
                }

                verify(service, times(1)).getTrades(bookId)
                verify(local, times(1)).saveTrades(trades)
                verify(local, times(1)).observeTradesByBook(bookId)
            }
        }

    @Test
    fun `when orders service fails then do not store and return from local`() =
        testCoroutineRule.runBlockingTest {
            criptoDataRule.apply {
                val bookId = "BTC_MXN"
                val trades = listOf<Trade>()
                `when`(service.getTrades(bookId)).thenReturn(null)
                `when`(tradeDao.observeById(bookId)).thenReturn(MutableLiveData(trades))

                repository.getTradesByBook(bookId).observeForTesting {
                    advanceTimeBy(2000)
                }

                verify(service, times(1)).getTrades(bookId)
                verify(local, never()).saveTrades(trades)
                verify(local, times(1)).observeTradesByBook(bookId)
            }
        }
}
