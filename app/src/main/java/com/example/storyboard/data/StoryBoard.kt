package com.example.storyboard.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters


@Entity(tableName = "storyboard")
data class StoryBoard(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var name: String = "",

    @TypeConverters(PanelListConverter::class)
    var panels: List<Panel> = emptyList()
)
