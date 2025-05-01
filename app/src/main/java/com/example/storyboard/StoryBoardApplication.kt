package com.example.storyboard

import android.app.Application
import com.example.storyboard.data.AppContainer
import com.example.storyboard.data.DefaultAppContainer

class StoryBoardApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}