package com.example.touralbum.common_memo;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.example.touralbum.MemoApplication;

public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = DBOpenHelper.class.getSimpleName();
    private static final int VERSION = 1;
    private static final String DB_NAME = "memo.db";

    public DBOpenHelper() {
        super(MemoApplication.getContext(), DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + ColumnContacts.EVENT_TABLE_NAME + "( "
                    + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ColumnContacts.EVENT_TITLE_COLUMN + " text, "
                    + ColumnContacts.EVENT_CONTENT_COLUMN + " text, "
                    + ColumnContacts.EVENT_CREATED_TIME_COLUMN + " datetime, "
                    + ColumnContacts.EVENT_UPDATED_TIME_COLUMN + " datetime, "
                    + ColumnContacts.EVENT_REMIND_TIME_COLUMN + " datetime, "
                    + ColumnContacts.EVENT_IS_IMPORTANT_COLUMN + " INTEGER, "
                    + ColumnContacts.EVENT_IS_CLOCKED + " INTEGER"
        + ")");

        String sql = "INSERT INTO " + ColumnContacts.EVENT_TABLE_NAME + " VALUES(NULL, ?, ?, ?, ?, ?, ?, ?)";
        db.beginTransaction();
        db.execSQL(sql, new Object[]{"jsbintask->memo",
                "Memo是一个小巧方便带有闹铃功能的记事本app，主要使用butterknife和recycleview，clockmanager构建\n" +
                        "git地址：https://github.com/jsbintask22/memo.git",
                "2018-04-25 17:28:23",
                "2018-04-25 17:28",
                "2018-04-25 17:28",
                0, 0});
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
