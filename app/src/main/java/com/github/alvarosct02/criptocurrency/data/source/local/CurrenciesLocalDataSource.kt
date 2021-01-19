package com.github.alvarosct02.criptocurrency.data.source.local

import android.content.Context
import com.github.alvarosct02.criptocurrency.data.models.Book
import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.CurrenciesSource
import com.github.alvarosct02.criptocurrency.data.source.local.room.AppDatabase
import com.github.alvarosct02.criptocurrency.data.source.local.room.entities.BookEntity
import com.github.alvarosct02.criptocurrency.data.source.local.room.entities.BookOrdersEntity
import com.github.alvarosct02.criptocurrency.data.source.local.room.entities.TickerEntity

class CurrenciesLocalDataSource(context: Context) : CurrenciesSource {

    //    TODO: Pending Refactor with DI
    private val appDatabase: AppDatabase by lazy {
        AppDatabase.init(context)
        AppDatabase.getInstance()
    }

    override suspend fun getAllBooks(): List<Book> {
        return appDatabase.bookEntityDao().getAll().map { BookEntity.toModel(it) }
    }

    override suspend fun saveAllBooks(books: List<Book>) {
        val bookEntityList = books.map { BookEntity.fromModel(it) }
        appDatabase.bookEntityDao().insertAll(bookEntityList)
    }

    override suspend fun getTickerByBook(book: String): Ticker {
        val entity = appDatabase.tickerEntityDao().getById(book)
        return TickerEntity.toModel(entity)
    }

    override suspend fun saveBookTicker(ticker: Ticker) {
        val entity = TickerEntity.fromModel(ticker)
        appDatabase.tickerEntityDao().insert(entity)
    }

    override suspend fun getOrdersByBook(book: String): BookOrders {
        val entity = appDatabase.bookOrdersEntityDao().getById(book)
        return BookOrdersEntity.toModel(entity)
    }

    override suspend fun saveBookOrders(bookOrders: BookOrders) {
        val entity = BookOrdersEntity.fromModel(bookOrders)
        appDatabase.bookOrdersEntityDao().insert(entity)
    }

}