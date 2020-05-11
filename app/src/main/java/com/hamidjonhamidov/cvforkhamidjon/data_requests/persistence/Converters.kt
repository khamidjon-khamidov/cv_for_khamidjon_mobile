package com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.Education
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.Honor

class Converters {

//    ************* About Me ****************
    // JSON to List<Education>
    @TypeConverter
    fun fromJSONToListEducation(json: String): List<Education> {
        val gson = Gson()
        val listType = object : TypeToken<List<Education>>() {}.type

        return gson.fromJson(json, listType) as List<Education>
    }

    // List<Education> to JSON
    @TypeConverter
    fun fromListEducatinToJson(list: List<Education>): String{
        val gson = GsonBuilder().create()

        return gson.toJson(list)
    }

//  ************** Skills ****************
    // List<String> to String
    @TypeConverter
    fun fromListStringToString(list: List<String>) =
        list.joinToString()

    // String to List<String
    @TypeConverter
    fun fromStringToListString(string: String) : List<String> =
        string.split(",").map { it.trim() }


//  *********** Achievements ************

    // JSON to List<Honor>
    @TypeConverter
    fun fromJSONToListHonor(json: String): List<Honor> {
        val gson = Gson()
        val listType = object : TypeToken<List<Honor>>() {}.type

        return gson.fromJson(json, listType) as List<Honor>
    }

    // List<Honor> to JSON
    @TypeConverter
    fun fromListHonorToJson(list: List<Honor>): String{
        val gson = GsonBuilder().create()

        return gson.toJson(list)
    }
}



















