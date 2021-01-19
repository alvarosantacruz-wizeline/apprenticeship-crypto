package com.github.alvarosct02.criptocurrency.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.alvarosct02.criptocurrency.data.source.local.room.entities.BookOrdersEntity

@Dao
interface BookOrdersEntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: BookOrdersEntity)

    @Query("SELECT * FROM BookOrdersEntity WHERE book = :book")
    suspend fun getById(book: String): BookOrdersEntity

    @Query("DELETE FROM BookOrdersEntity")
    suspend fun deleteAll()

}
