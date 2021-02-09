package com.github.alvarosct02.criptocurrency.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.github.alvarosct02.criptocurrency.data.source.local.room.MyTypeConverters
import com.google.gson.annotations.SerializedName

@Entity
data class BookOrders(
    @SerializedName("book") @PrimaryKey var book: String = "",
    @SerializedName("bids") @TypeConverters(MyTypeConverters::class) var bids: List<BookOrder> = listOf(),
    @SerializedName("asks") @TypeConverters(MyTypeConverters::class) var asks: List<BookOrder> = listOf(),
    @SerializedName("sequence") var sequence: String = "",
    @SerializedName("updated_at") var updatedAt: String = "",
)

data class BookOrder(
    @SerializedName("book") val book: String = "",
    @SerializedName("price") val price: String = "",
    @SerializedName("amount") val amount: String = "",
)
