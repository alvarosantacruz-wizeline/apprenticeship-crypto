package com.github.alvarosct02.criptocurrency.data.source.local.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.alvarosct02.criptocurrency.data.models.BookOrders

@Dao
interface OrdersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: BookOrders)

    @Query("SELECT * FROM BookOrders WHERE book = :book")
    fun observeById(book: String): LiveData<BookOrders?>

    @Query("SELECT * FROM BookOrders WHERE book = :book")
    fun getById(book: String): BookOrders

    @Query("DELETE FROM BookOrders")
    suspend fun deleteAll()
}
