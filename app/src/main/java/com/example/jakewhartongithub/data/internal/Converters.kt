package com.example.jakewhartongithub.data.internal

import androidx.room.TypeConverter
import com.example.jakewhartongithub.models.License
import com.example.jakewhartongithub.models.Owner
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * Created by Asmaa Hassan
 */
object Converters {
    @TypeConverter
    fun fromString(value: String?): ArrayList<String> {
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.getType()
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<String?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}


object TypeConvertorClass {
    @TypeConverter
    fun getLicence(longId: String?): License? {
        return if (longId == null) null else License()
    }
    @TypeConverter
    fun getOwner(longId: String?): Owner? {
        return if (longId == null) null else Owner()
    }
}
