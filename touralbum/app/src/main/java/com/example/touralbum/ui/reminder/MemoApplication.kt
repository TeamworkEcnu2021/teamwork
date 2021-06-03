package com.example.touralbum.ui.reminder

import android.app.Application
import android.content.Context

class MemoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        var context: Context? = null
            private set
    }
}