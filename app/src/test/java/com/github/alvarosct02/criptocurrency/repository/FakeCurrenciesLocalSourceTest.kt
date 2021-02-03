package com.github.alvarosct02.criptocurrency.repository

import com.github.alvarosct02.criptocurrency.data.models.Book
import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.source.local.CurrenciesLocalSource
import com.github.alvarosct02.criptocurrency.shared.FakeCurrenciesLocalSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FakeCurrenciesLocalSourceTest {

    companion object {
        const val BOOK_1_ID = "Book_1_Id"
        const val BOOK_2_ID = "Book_2_Id"
        val BOOK_1 = Book(book = BOOK_1_ID)
        val TICKER = Ticker(book = BOOK_1_ID)
        val ORDERS = BookOrders(book = BOOK_1_ID)
    }

    private val sourceLocal: CurrenciesLocalSource = FakeCurrenciesLocalSource()

    @Test
    fun `get books for the first time`() = runBlockingTest {
        assertNull(sourceLocal.getAllBooks())
    }

    @Test
    fun `get books after saving`() = runBlockingTest {
        val books = listOf(BOOK_1)
        sourceLocal.saveAllBooks(books)
        val savedBooks = sourceLocal.getAllBooks()
        assertEquals(books.size, savedBooks?.size ?: 0)
    }

    @Test
    fun `get ticker for the first time`() = runBlockingTest {
        assertNull(sourceLocal.getTickerByBook(BOOK_1_ID))
    }

    @Test
    fun `get ticker after saving another`() = runBlockingTest {
        sourceLocal.saveBookTicker(TICKER)
        val savedTicker = sourceLocal.getTickerByBook(BOOK_2_ID)
        assertNull(savedTicker)
    }

    @Test
    fun `get ticker after saving same book recently`() = runBlockingTest {
        sourceLocal.saveBookTicker(TICKER)
        val savedTicker = sourceLocal.getTickerByBook(BOOK_1_ID)
        assertNotNull(savedTicker)
    }

    @Test
    fun `get ticker after saving same book previously`() = runBlockingTest {
        sourceLocal.saveBookTicker(TICKER)
        sourceLocal.saveBookTicker(TICKER.copy(book = BOOK_2_ID))
        val savedTicker = sourceLocal.getTickerByBook(BOOK_1_ID)
        assertNotNull(savedTicker)
    }

    @Test
    fun `get orders after saving another`() = runBlockingTest {
        sourceLocal.saveBookOrders(ORDERS)
        val savedTicker = sourceLocal.getOrdersByBook(BOOK_2_ID)
        assertNull(savedTicker)
    }

    @Test
    fun `get orders after saving same book recently`() = runBlockingTest {
        sourceLocal.saveBookOrders(ORDERS)
        val savedTicker = sourceLocal.getOrdersByBook(BOOK_1_ID)
        assertNotNull(savedTicker)
    }

    @Test
    fun `get orders after saving same book previously`() = runBlockingTest {
        sourceLocal.saveBookOrders(ORDERS)
        sourceLocal.saveBookOrders(ORDERS.copy(book = BOOK_2_ID))
        val savedTicker = sourceLocal.getOrdersByBook(BOOK_1_ID)
        assertNotNull(savedTicker)
    }
}
