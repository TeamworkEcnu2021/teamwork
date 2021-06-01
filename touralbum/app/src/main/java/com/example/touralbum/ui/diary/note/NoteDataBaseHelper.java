package com.example.touralbum.ui.diary.note;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class NoteDataBaseHelper extends SQLiteOpenHelper {

    public NoteDataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //表创建接口
    public interface TableCreateInterface{
        //创建表
        void onCreate(SQLiteDatabase db);
        //更新表
        void onUpgrade(SQLiteDatabase db, int oleVersion, int newVersion);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Note.getInstance().onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Note.getInstance().onUpgrade(db, oldVersion, newVersion);
    }
}
