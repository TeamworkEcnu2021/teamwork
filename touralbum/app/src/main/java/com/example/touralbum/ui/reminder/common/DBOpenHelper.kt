package com.example.touralbum.ui.reminder.common

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.touralbum.ui.reminder.MemoApplication

class DBOpenHelper :
    SQLiteOpenHelper(MemoApplication.context, DB_NAME, null, VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS " + ColumnContacts.EVENT_TABLE_NAME + "( "
                    + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ColumnContacts.EVENT_TITLE_COLUMN + " text, "
                    + ColumnContacts.EVENT_CONTENT_COLUMN + " text, "
                    + ColumnContacts.EVENT_CREATED_TIME_COLUMN + " datetime, "
                    + ColumnContacts.EVENT_UPDATED_TIME_COLUMN + " datetime, "
                    + ColumnContacts.EVENT_REMIND_TIME_COLUMN + " datetime, "
                    + ColumnContacts.EVENT_IS_IMPORTANT_COLUMN + " INTEGER, "
                    + ColumnContacts.EVENT_IS_CLOCKED + " INTEGER"
                    + ")"
        )
        val sql =
            "INSERT INTO " + ColumnContacts.EVENT_TABLE_NAME + " VALUES(NULL, ?, ?, ?, ?, ?, ?, ?)"
        db.beginTransaction()
        db.execSQL(
            sql, arrayOf<Any>(
                "jsbintask->memo",
                """
                Memo是一个小巧方便带有闹铃功能的记事本app，主要使用butterknife和recycleview，clockmanager构建
                git地址：https://github.com/jsbintask22/memo.git
                """.trimIndent(),
                "2018-04-25 17:28:23",
                "2018-04-25 17:28",
                "2018-04-25 17:28",
                0, 0
            )
        )
        db.setTransactionSuccessful()
        db.endTransaction()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    companion object {
        private val TAG = DBOpenHelper::class.java.simpleName
        private const val VERSION = 1
        private const val DB_NAME = "memo.db"
    }
}