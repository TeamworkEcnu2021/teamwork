package com.example.touralbum.ui.reminder.dao

import android.database.Cursor
import android.provider.BaseColumns
import com.example.touralbum.ui.reminder.common.ColumnContacts
import com.example.touralbum.ui.reminder.common.DBCallback
import com.example.touralbum.ui.reminder.entity.Event

class EventCallback : DBCallback<Event> {
    override fun cursorToInstance(cursor: Cursor): Event {
        val event = Event()
        event.setmId(cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID)))
        event.setmTitle(cursor.getString(cursor.getColumnIndexOrThrow(ColumnContacts.EVENT_TITLE_COLUMN)))
        event.setmContent(cursor.getString(cursor.getColumnIndexOrThrow(ColumnContacts.EVENT_CONTENT_COLUMN)))
        event.setmCreatedTime(cursor.getString(cursor.getColumnIndexOrThrow(ColumnContacts.EVENT_CREATED_TIME_COLUMN)))
        event.setmUpdatedTime(cursor.getString(cursor.getColumnIndexOrThrow(ColumnContacts.EVENT_UPDATED_TIME_COLUMN)))
        event.setmRemindTime(cursor.getString(cursor.getColumnIndexOrThrow(ColumnContacts.EVENT_REMIND_TIME_COLUMN)))
        event.setmIsImportant(cursor.getInt(cursor.getColumnIndexOrThrow(ColumnContacts.EVENT_IS_IMPORTANT_COLUMN)))
        event.setmIsClocked(cursor.getInt(cursor.getColumnIndexOrThrow(ColumnContacts.EVENT_IS_CLOCKED)))
        return event
    }
}