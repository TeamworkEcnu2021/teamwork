package com.example.touralbum.ui.diary.note;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;

public class Note implements NoteDataBaseHelper.TableCreateInterface{
    //定义表名
    public static String tableName = "Note";
    //定义各字段
    public static String _id = "_id";
    public static String title = "title";
    public static String content = "content";
    public static String time = "date";
    //私有化构造方法
    private Note(){}
    //初始化实例
    private static final Note note = new Note();
    //只提供一个实例
    public static Note getInstance(){
        return note;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "
                + Note.tableName
                + " (  "
                + "_id integer primary key autoincrement, "
                + Note.title + " TEXT, "
                + Note.content + " TEXT, "
                + Note.time + " TEXT "
                + ");";
        db.execSQL( sql );
    }
    //实现表的更新
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if ( oldVersion < newVersion ) {
            String sql = "DROP TABLE IF EXISTS " + Note.tableName;
            db.execSQL(sql);
            this.onCreate(db);
        }
    }
    //插入
    public static void insertNote(NoteDataBaseHelper dbHelper, ContentValues userValues){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert(Note.tableName, null, userValues);
        db.close();
    }
    //删除一条笔记
    public  static void deleteNote(NoteDataBaseHelper dbHelper, int _id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Note.tableName, Note._id + "=?", new String[]{_id + ""});
        db.close();
    }
    //删除所有笔记
    public static void deleteAllNote(NoteDataBaseHelper dbHelper){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Note.tableName, null, null);
        db.close();
    }
    //修改
    public static void updateNote(NoteDataBaseHelper dbHelper, int _id, ContentValues infoValues){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.update(Note.tableName, infoValues, Note._id + "=?",new String[]{_id + ""});
        db.close();
    }
    public static HashMap<String, Object>getNote(NoteDataBaseHelper dbHelper, int _id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        HashMap<String, Object> NoteMap = new HashMap<String, Object>();
        @SuppressLint("Recycle") Cursor cursor = db.query( Note.tableName, null, Note._id + " =? ", new String[]{ _id + "" }, null, null, null);
        cursor.moveToFirst();
        NoteMap.put(Note.title, cursor.getLong(cursor.getColumnIndex(Note.title)));
        NoteMap.put(Note.content, cursor.getString(cursor.getColumnIndex(Note.content)));
        NoteMap.put(Note.time, cursor.getString(cursor.getColumnIndex(Note.time)));

        return NoteMap;
    }
    //获得查询指向Note表的游标
    public static Cursor getAllNotes(NoteDataBaseHelper dbHelper){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(Note.tableName, null,null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }
}
