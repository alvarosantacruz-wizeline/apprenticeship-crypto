package com.github.alvarosct02.criptocurrency.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Trade(
    @SerializedName("tid") @PrimaryKey var tid: Long = 0L,
    @SerializedName("book") var book: String = "",
    @SerializedName("created_at") var createdAt: String = "",
    @SerializedName("amount") var amount: String = "",
    @SerializedName("maker_side") var makerSide: String = "",
    @SerializedName("price") var price: String = "",
)
