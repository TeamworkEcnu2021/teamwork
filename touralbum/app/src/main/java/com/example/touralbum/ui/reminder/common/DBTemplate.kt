package com.example.touralbum.ui.reminder.common

import android.content.ContentValues
import android.provider.BaseColumns
import java.util.*

class DBTemplate<T> {
    private val dbHelper: DBOpenHelper
    fun queryOne(sql: String?, callback: DBCallback<T>, vararg args: String?): T? {
        var t: T? = null
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(sql, args)
        if (cursor != null && cursor.moveToNext()) {
            t = callback.cursorToInstance(cursor)
            cursor.close()
        }
        return t
    }

    fun query(sql: String?, callback: DBCallback<T>, vararg args: String?): List<T?> {
        val list: MutableList<T?> = ArrayList()
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(sql, args)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val t = callback.cursorToInstance(cursor)
                list.add(t)
            }
            cursor.close()
        }
        return list
    }

    fun create(table: String?, values: ContentValues?): Long {
        val db = dbHelper.writableDatabase
        return db.insert(table, null, values)
    }

    fun remove(table: String?, whereConditions: String?, vararg args: String?): Int {
        val db = dbHelper.writableDatabase
        return db.delete(table, whereConditions, args)
    }

    fun getLatestId(table: String): Int {
        val db = dbHelper.readableDatabase
        val sql = "SELECT MAX(" + BaseColumns._ID + ") FROM " + table
        val cursor = db.rawQuery(sql, arrayOf())
        var result = -1
        if (cursor != null && cursor.moveToNext()) {
            result = cursor.getInt(0)
            cursor.close()
        }
        return result
    }

    fun update(
        table: String?,
        contentValues: ContentValues?,
        whereConditions: String?,
        vararg args: String?
    ): Int {
        val db = dbHelper.writableDatabase
        return db.update(table, contentValues, whereConditions, args)
    }

    init {
        dbHelper = DBOpenHelper()
    }
}