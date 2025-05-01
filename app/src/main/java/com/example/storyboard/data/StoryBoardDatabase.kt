package com.example.storyboard.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [StoryBoard::class], version = 1, exportSchema = false)
@TypeConverters(PanelListConverter::class)
abstract class StoryBoardDatabase: RoomDatabase() {
    abstract fun storyBoardDao(): StoryBoardDao

    companion object {
        @Volatile
        private var Instance: StoryBoardDatabase? = null

        fun getDatabase(context: Context): StoryBoardDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    StoryBoardDatabase::class.java,
                    "storyboard_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}