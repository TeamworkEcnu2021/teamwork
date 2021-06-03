package com.example.touralbum.ui.reminder.dao

import android.content.ContentValues
import android.provider.BaseColumns
import com.example.touralbum.ui.reminder.Constants.EventClockFlag
import com.example.touralbum.ui.reminder.common.ColumnContacts
import com.example.touralbum.ui.reminder.common.DBTemplate
import com.example.touralbum.ui.reminder.entity.Event
import com.example.touralbum.ui.reminder.util.DateTimeUtil
import java.util.*

class EventDao private constructor() {
    private val mTemplate = DBTemplate<Event>()
    private val mCallback = EventCallback()
    fun findAll(): List<Event?>? {
        val sql =
            "SELECT * FROM " + ColumnContacts.EVENT_TABLE_NAME + " ORDER BY " + ColumnContacts.EVENT_IS_IMPORTANT_COLUMN + " DESC, " + ColumnContacts.EVENT_CREATED_TIME_COLUMN + " DESC"
        return mTemplate.query(sql, mCallback)
    }

    fun findAllWithNOClocked(): List<Event?>? {
        val sql =
            "SELECT * FROM " + ColumnContacts.EVENT_TABLE_NAME + " WHERE " + ColumnContacts.EVENT_IS_CLOCKED + " = " + EventClockFlag.Companion.NONE
        return mTemplate.query(sql, mCallback)
    }

    fun updateEventClocked(id: Int?): Int {
        val contentValues = ContentValues()
        contentValues.put(ColumnContacts.EVENT_IS_CLOCKED, EventClockFlag.Companion.CLOCKED)
        return mTemplate.update(
            ColumnContacts.EVENT_TABLE_NAME,
            contentValues,
            BaseColumns._ID + " = ?",
            Integer.toString(
                id!!
            )
        )
    }

    fun findById(id: Int?): Event? {
        val sql =
            "SELECT * FROM " + ColumnContacts.EVENT_TABLE_NAME + " WHERE " + BaseColumns._ID + " = ?"
        return mTemplate.queryOne(sql, mCallback, Integer.toString(id!!))
    }

    fun remove(ids: List<Int?>?): Int {
        val whereConditions = StringBuilder(BaseColumns._ID + " IN(")
        for (id in ids!!) {
            whereConditions.append(id).append(",")
        }
        whereConditions.deleteCharAt(whereConditions.length - 1).append(")")
        return mTemplate.remove(ColumnContacts.EVENT_TABLE_NAME, whereConditions.toString())
    }

    fun create(event: Event): Int {
        return mTemplate.create(
            ColumnContacts.EVENT_TABLE_NAME,
            generateContentValues(event, false)
        )
            .toInt()
    }

    fun update(event: Event): Int {
        return mTemplate.update(
            ColumnContacts.EVENT_TABLE_NAME,
            generateContentValues(event, true),
            BaseColumns._ID + "  = ?",
            Integer.toString(
                event.getmId()!!
            )
        )
    }

    val latestEventId: Int
        get() = mTemplate.getLatestId(ColumnContacts.EVENT_TABLE_NAME)

    private fun generateContentValues(event: Event, isUpdate: Boolean): ContentValues {
        val contentValues = ContentValues()
        contentValues.put(ColumnContacts.EVENT_TITLE_COLUMN, event.getmTitle())
        contentValues.put(ColumnContacts.EVENT_CONTENT_COLUMN, event.getmContent())
        if (!isUpdate) {
            contentValues.put(
                ColumnContacts.EVENT_CREATED_TIME_COLUMN,
                DateTimeUtil.dateToStr(Date())
            )
        } else {
            contentValues.put(ColumnContacts.EVENT_CREATED_TIME_COLUMN, event.getmCreatedTime())
        }
        contentValues.put(ColumnContacts.EVENT_IS_CLOCKED, event.getmIsClocked())
        contentValues.put(ColumnContacts.EVENT_UPDATED_TIME_COLUMN, DateTimeUtil.dateToStr(Date()))
        contentValues.put(ColumnContacts.EVENT_REMIND_TIME_COLUMN, event.getmRemindTime())
        contentValues.put(ColumnContacts.EVENT_IS_IMPORTANT_COLUMN, event.getmIsImportant())
        return contentValues
    }

    companion object {
        val instance = EventDao()
    }
}