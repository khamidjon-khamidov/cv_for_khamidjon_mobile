package com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.Education

class Converters {

    @TypeConverter
    fun fromJSONToListEducation(json: String): List<Education> {
        val gson = Gson()
        val listType = object : TypeToken<List<Education>>() {}.type

        return gson.fromJson(json, listType) as List<Education>
    }

    @TypeConverter
    fun fromListEducatinToJson(list: List<Education>): String{
        val gson = GsonBuilder().create()

        return gson.toJson(list)
    }

    @TypeConverter
    fun fromListStringToString(list: List<String>) =
        list.joinToString()


    @TypeConverter
    fun fromStringToListString(string: String) : List<String> =
        string.split(",").map { it.trim() }

}