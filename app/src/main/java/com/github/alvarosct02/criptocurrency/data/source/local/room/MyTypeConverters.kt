package com.github.alvarosct02.criptocurrency.data.source.local.room

import androidx.room.TypeConverter
import com.github.alvarosct02.criptocurrency.data.models.BookOrder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MyTypeConverters {

    val gson = Gson()

    @TypeConverter
    fun stringToBookOrderList(data: String?): List<BookOrder> {
        return data?.let {
            val listType = object : TypeToken<List<BookOrder>>() {}.type
            gson.fromJson<List<BookOrder>>(it, listType)
        } ?: listOf()
    }

    @TypeConverter
    fun bookOrderListToString(objList: List<BookOrder>?): String? {
        return objList?.let { gson.toJson(it) }
    }
}
