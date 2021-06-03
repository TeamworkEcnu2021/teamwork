package com.example.touralbum.ui.diary.note

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper

class NoteDataBaseHelper(context: Context?, name: String?, factory: CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, name, factory, version) {
    //表创建接口
    interface TableCreateInterface {
        //创建表
        fun onCreate(db: SQLiteDatabase)

        //更新表
        fun onUpgrade(db: SQLiteDatabase, oleVersion: Int, newVersion: Int)
    }

    override fun onCreate(db: SQLiteDatabase) {
        Note.instance.onCreate(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Note.instance.onUpgrade(db, oldVersion, newVersion)
    }
}