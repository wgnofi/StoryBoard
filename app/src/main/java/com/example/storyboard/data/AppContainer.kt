package com.example.storyboard.data

import android.content.Context

interface AppContainer {
    val storyBoardRepository: StoryBoardRepository
}

class DefaultAppContainer(private val context: Context): AppContainer {
    override val storyBoardRepository: StoryBoardRepository by lazy {
        OfflineStoryBoardRepository(StoryBoardDatabase.getDatabase(context).storyBoardDao())
    }
}