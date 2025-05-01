package com.example.storyboard.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PanelListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromPanelList(panels: List<Panel>): String {
        return gson.toJson(panels)
    }

    @TypeConverter
    fun toPanelList(panelsString: String): List<Panel> {
        val listType = object : TypeToken<List<Panel>>() {}.type
        return gson.fromJson(panelsString, listType)
    }
}