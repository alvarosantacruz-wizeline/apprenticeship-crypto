package com.github.alvarosct02.criptocurrency.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.alvarosct02.criptocurrency.BuildConfig
import com.github.alvarosct02.criptocurrency.data.source.local.room.dao.BookEntityDao
import com.github.alvarosct02.criptocurrency.data.source.local.room.dao.BookOrdersEntityDao
import com.github.alvarosct02.criptocurrency.data.source.local.room.dao.TickerEntityDao
import com.github.alvarosct02.criptocurrency.data.source.local.room.entities.BookEntity
import com.github.alvarosct02.criptocurrency.data.source.local.room.entities.BookOrdersEntity
import com.github.alvarosct02.criptocurrency.data.source.local.room.entities.TickerEntity

@Database(
    entities = [BookEntity::class, BookOrdersEntity::class, TickerEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(MyTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookEntityDao(): BookEntityDao
    abstract fun bookOrdersEntityDao(): BookOrdersEntityDao
    abstract fun tickerEntityDao(): TickerEntityDao

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