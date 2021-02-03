package com.github.alvarosct02.criptocurrency.data.models

import com.google.gson.annotations.SerializedName

data class BookOrders(
    @SerializedName("book") val book: String = "",
    @SerializedName("bids") val bids: List<BookOrder> = listOf(),
    @SerializedName("asks") val asks: List<BookOrder> = listOf(),
    @SerializedName("sequence") val sequence: String = "",
    @SerializedName("updated_at") val updatedAt: String = "",
)

data class BookOrder(
    @SerializedName("book") val book: String = "",
    @SerializedName("price") val price: String = "",
    @SerializedName("amount") val amount: String = "",
)
