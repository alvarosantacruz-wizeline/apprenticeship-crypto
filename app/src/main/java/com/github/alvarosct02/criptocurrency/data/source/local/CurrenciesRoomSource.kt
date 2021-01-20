package com.github.alvarosct02.criptocurrency.data.source.local

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.github.alvarosct02.criptocurrency.data.models.Book
import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.source.local.room.AppDatabase
import com.github.alvarosct02.criptocurrency.data.source.local.room.entities.BookEntity
import com.github.alvarosct02.criptocurrency.data.source.local.room.entities.BookOrdersEntity
import com.github.alvarosct02.criptocurrency.data.source.local.room.entities.TickerEntity

class CurrenciesRoomSource(context: Context) : CurrenciesLocalSource {

    //    TODO: Pending Refactor with DI
    private val appDatabase: AppDatabase by lazy {
        AppDatabase.init(context)
        AppDatabase.getInstance()
    }

    override fun observeAllBooks(): LiveData<List<Book>> {
        TODO("Not yet implemented")
    }

    @WorkerThread
    override suspend fun getAllBooks(): List<Book> {
        return appDatabase.bookEntityDao().getAll().map {
            BookEntity.toModel(it)
        }
    }

    @WorkerThread
    override suspend fun saveAllBooks(books: List<Book>) {
        val bookEntityList = books.map { BookEntity.fromModel(it) }
        appDatabase.bookEntityDao().insertAll(bookEntityList)
    }

    override fun observeTickerByBook(book: String): LiveData<Ticker> {
        TODO("Not yet implemented")
    }

    @WorkerThread
    override suspend fun getTickerByBook(book: String): Ticker {
        return TickerEntity.toModel(appDatabase.tickerEntityDao().getById(book))
    }

    @WorkerThread
    override suspend fun saveBookTicker(ticker: Ticker) {
        val entity = TickerEntity.fromModel(ticker)
        appDatabase.tickerEntityDao().insert(entity)
    }

    override fun observeOrdersByBook(book: String): LiveData<BookOrders> {
        TODO("Not yet implemented")
    }

    @WorkerThread
    override suspend fun getOrdersByBook(book: String): BookOrders {
        return BookOrdersEntity.toModel(appDatabase.bookOrdersEntityDao().getById(book))
    }

    @WorkerThread
    override suspend fun saveBookOrders(bookOrders: BookOrders) {
        val entity = BookOrdersEntity.fromModel(bookOrders)
        appDatabase.bookOrdersEntityDao().insert(entity)
    }

}