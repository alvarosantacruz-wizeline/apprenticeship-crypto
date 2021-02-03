package com.github.alvarosct02.criptocurrency.data.source.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.github.alvarosct02.criptocurrency.data.models.BookOrder
import com.github.alvarosct02.criptocurrency.data.models.BookOrders
import com.github.alvarosct02.criptocurrency.data.source.local.room.MyTypeConverters

@Entity
data class BookOrdersEntity(
    @PrimaryKey var book: String = "",
    var sequence: String = "",
    var updatedAt: String = "",
    @TypeConverters(MyTypeConverters::class) var bids: List<BookOrder> = listOf(),
    @TypeConverters(MyTypeConverters::class) var asks: List<BookOrder> = listOf(),
) {
    companion object {
        fun fromModel(model: BookOrders): BookOrdersEntity {
            return BookOrdersEntity(
                book = model.book,
                sequence = model.sequence,
                updatedAt = model.updatedAt,
                bids = model.bids,
                asks = model.asks,
            )
        }

        fun toModel(entity: BookOrdersEntity): BookOrders {
            return BookOrders(
                book = entity.book,
                sequence = entity.sequence,
                updatedAt = entity.updatedAt,
                bids = entity.bids,
                asks = entity.asks,
            )
        }
    }
}
