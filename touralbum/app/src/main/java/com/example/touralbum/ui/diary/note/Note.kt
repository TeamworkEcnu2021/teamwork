package com.example.touralbum.ui.diary.note

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.touralbum.ui.diary.note.NoteDataBaseHelper.TableCreateInterface
import java.util.*

class Note  //私有化构造方法
private constructor() : TableCreateInterface {
    override fun onCreate(db: SQLiteDatabase) {
        val sql = ("CREATE TABLE "
                + tableName
                + " (  "
                + "_id integer primary key autoincrement, "
                + title + " TEXT, "
                + content + " TEXT, "
                + time + " TEXT "
                + ");")
        db.execSQL(sql)
    }

    //实现表的更新
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < newVersion) {
            val sql = "DROP TABLE IF EXISTS " + tableName
            db.execSQL(sql)
            onCreate(db)
        }
    }

    companion object {
        //定义表名
        var tableName = "Note"

        //定义各字段
        var _id = "_id"
        var title = "title"
        var content = "content"
        var time = "date"

        //只提供一个实例
        //初始化实例
        val instance = Note()

        //插入
        fun insertNote(dbHelper: NoteDataBaseHelper?, userValues: ContentValues?) {
            val db = dbHelper!!.writableDatabase
            db.insert(tableName, null, userValues)
            db.close()
        }

        //删除一条笔记
        fun deleteNote(dbHelper: NoteDataBaseHelper?, _id: Int) {
            val db = dbHelper!!.writableDatabase
            db.delete(tableName, Companion._id + "=?", arrayOf(_id.toString() + ""))
            db.close()
        }

        //删除所有笔记
        fun deleteAllNote(dbHelper: NoteDataBaseHelper) {
            val db = dbHelper.writableDatabase
            db.delete(tableName, null, null)
            db.close()
        }

        //修改
        fun updateNote(dbHelper: NoteDataBaseHelper?, _id: Int, infoValues: ContentValues?) {
            val db = dbHelper!!.writableDatabase
            db.update(tableName, infoValues, Companion._id + "=?", arrayOf(_id.toString() + ""))
            db.close()
        }

        fun getNote(dbHelper: NoteDataBaseHelper, _id: Int): HashMap<String, Any> {
            val db = dbHelper.writableDatabase
            val NoteMap = HashMap<String, Any>()
            @SuppressLint("Recycle") val cursor = db.query(
                tableName,
                null,
                Companion._id + " =? ",
                arrayOf(_id.toString() + ""),
                null,
                null,
                null
            )
            cursor.moveToFirst()
            NoteMap[title] =
                cursor.getLong(cursor.getColumnIndex(title))
            NoteMap[content] =
                cursor.getString(cursor.getColumnIndex(content))
            NoteMap[time] =
                cursor.getString(cursor.getColumnIndex(time))
            return NoteMap
        }

        //获得查询指向Note表的游标
        fun getAllNotes(dbHelper: NoteDataBaseHelper?): Cursor {
            val db = dbHelper!!.readableDatabase
            val cursor = db.query(tableName, null, null, null, null, null, null)
            cursor.moveToFirst()
            return cursor
        }
    }
}