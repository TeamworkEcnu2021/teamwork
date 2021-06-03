package com.example.touralbum.ui.reminder.common

import android.database.Cursor

interface DBCallback<T> {
    fun cursorToInstance(cursor: Cursor): T
}