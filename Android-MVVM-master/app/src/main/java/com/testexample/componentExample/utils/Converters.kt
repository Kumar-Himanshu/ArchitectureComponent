package com.testexample.componentExample.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.util.*

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        if(null == date){
            return " ".toLongOrNull()
        }else{
            return date!!.time!!.toLong()
        }
    }

    @TypeConverter
    fun listToString(value: List<String>?): String {

        return Gson().toJson(value)
    }

    @TypeConverter
    fun stringToList(value: String): List<String>? {

        val objects = Gson().fromJson(value, Array<String>::class.java) as Array<String>
        val list = objects.toList()
        return list
    }
}