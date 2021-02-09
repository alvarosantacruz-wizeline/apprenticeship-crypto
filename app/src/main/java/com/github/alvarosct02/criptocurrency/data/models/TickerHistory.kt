package com.github.alvarosct02.criptocurrency.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class TickerHistory(
    @SerializedName("book") @PrimaryKey var book: String = "",
    @SerializedName("bucket_start_time") var bucketStartTime: String = "",
    @SerializedName("first_rate") var firstRate: String = "",
    @SerializedName("last_rate") var lastRate: String = "",
    @SerializedName("min_rate") var minRate: String = "",
    @SerializedName("max_rate") var maxRate: String = "",
    @SerializedName("vwap") var vwap: String = "",
    @SerializedName("volume") var volume: String = "",
    @SerializedName("trade_count") var tradeCount: Int = 0,
)
