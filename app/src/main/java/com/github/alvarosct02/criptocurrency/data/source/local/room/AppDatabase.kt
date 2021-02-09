package com.github.alvarosct02.criptocurrency.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.alvarosct02.criptocurrency.BuildConfig
import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.models.Ticker
import com.github.alvarosct02.criptocurrency.data.models.TickerHistory
import com.github.alvarosct02.criptocurrency.data.source.local.room.dao.OrdersDao
import com.github.alvarosct02.criptocurrency.data.source.local.room.dao.TickerDao
import com.github.alvarosct02.criptocurrency.data.source.local.room.dao.TickerHistoryDao

@Database(
    entities = [BookOrders::class, Ticker::class, TickerHistory::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(MyTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun ordersDao(): OrdersDao
    abstract fun tickerDao(): TickerDao
    abstract fun tickerHistoryDao(): TickerHistoryDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun init(applicationContext: Context) {
            INSTANCE = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, BuildConfig.DATABASE_NAME
            ).build()
        }

        fun getInstance(): AppDatabase {
            return INSTANCE ?: throw RuntimeException()
        }
    }
}
