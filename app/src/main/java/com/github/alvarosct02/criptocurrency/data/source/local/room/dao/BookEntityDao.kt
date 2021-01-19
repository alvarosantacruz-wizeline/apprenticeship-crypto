package com.github.alvarosct02.criptocurrency.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.alvarosct02.criptocurrency.data.source.local.room.entities.BookEntity

@Dao
interface BookEntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: BookEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entityList: List<BookEntity>)

    @Query("SELECT * FROM BookEntity")
    suspend fun getAll(): List<BookEntity>

    @Query("SELECT * FROM BookEntity WHERE book = :book")
    suspend fun getById(book: String): BookEntity

    @Query("DELETE FROM BookEntity")
    suspend fun deleteAll()

}
