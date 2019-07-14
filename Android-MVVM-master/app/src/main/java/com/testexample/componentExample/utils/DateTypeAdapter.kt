package com.testexample.componentExample.utils

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.util.*

class DateTypeAdapter: TypeAdapter<Date>() {
    override fun write(out: JsonWriter?, value: Date?) {
        if (value == null)
        else
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun read(`inputValue`: JsonReader?): Date? {
        if (inputValue != null) {
            return Date(inputValue.nextLong() * 1000);
        }
        else return null;
    }

}