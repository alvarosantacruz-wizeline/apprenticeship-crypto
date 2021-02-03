package com.github.alvarosct02.criptocurrency.data.source.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.alvarosct02.criptocurrency.data.models.Book

@Entity
data class BookEntity(
    @PrimaryKey var book: String = "",
    var minimumAmount: String = "",
    var maximumAmount: String = "",
    var minimumPrice: String = "",
    var maximumPrice: String = "",
    var minimumValue: String = "",
    var maximumValue: String = "",
) {

    companion object {
        fun fromModel(model: Book): BookEntity {
            return BookEntity(
                book = model.book,
                minimumAmount = model.minimumAmount,
                maximumAmount = model.maximumAmount,
                minimumPrice = model.minimumPrice,
                maximumPrice = model.maximumPrice,
                minimumValue = model.minimumValue,
                maximumValue = model.maximumValue,
            )
        }

        fun toModel(entity: BookEntity): Book {
            return Book(
                book = entity.book,
                minimumAmount = entity.minimumAmount,
                maximumAmount = entity.maximumAmount,
                minimumPrice = entity.minimumPrice,
                maximumPrice = entity.maximumPrice,
                minimumValue = entity.minimumValue,
                maximumValue = entity.maximumValue,
            )
        }
    }
}
