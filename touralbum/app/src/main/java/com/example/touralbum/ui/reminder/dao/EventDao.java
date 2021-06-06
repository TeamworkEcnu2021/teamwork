package com.example.touralbum.ui.reminder.dao;

import android.content.ContentValues;
import android.provider.BaseColumns;

import com.example.touralbum.ui.reminder.Constants;
import com.example.touralbum.ui.reminder.common.ColumnContacts;
import com.example.touralbum.ui.reminder.common.DBTemplate;
import com.example.touralbum.ui.reminder.entity.Event;
import com.example.touralbum.ui.reminder.util.DateTimeUtil;

import java.util.Date;
import java.util.List;


public class EventDao {

    private DBTemplate<Event> mTemplate = new DBTemplate<>();
    private EventCallback mCallback = new EventCallback();
    private static EventDao mEventDao = new EventDao();
    private EventDao() {
    }

    public static EventDao getInstance() {
        return mEventDao;
    }

    public List<Event> findAll() {
        String sql = "SELECT * FROM " + ColumnContacts.EVENT_TABLE_NAME + " ORDER BY " + ColumnContacts.EVENT_IS_IMPORTANT_COLUMN + " DESC, " + ColumnContacts.EVENT_CREATED_TIME_COLUMN + " DESC";
        return mTemplate.query(sql, mCallback);
    }

    public List<Event> findAllWithNOClocked() {
        String sql = "SELECT * FROM " + ColumnContacts.EVENT_TABLE_NAME + " WHERE " + ColumnContacts.EVENT_IS_CLOCKED + " = " + Constants.EventClockFlag.NONE;
        return mTemplate.query(sql, mCallback);
    }

    public int updateEventClocked(Integer id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ColumnContacts.EVENT_IS_CLOCKED, Constants.EventClockFlag.CLOCKED);
        return mTemplate.update(ColumnContacts.EVENT_TABLE_NAME, contentValues, BaseColumns._ID + " = ?", Integer.toString(id));
    }

    public Event findById(Integer id) {
        String sql = "SELECT * FROM " + ColumnContacts.EVENT_TABLE_NAME + " WHERE " + BaseColumns._ID + " = ?";
        return mTemplate.queryOne(sql, mCallback, Integer.toString(id));
    }

    public int remove(List<Integer> ids) {
        StringBuilder whereConditions = new StringBuilder(BaseColumns._ID + " IN(");
        for (Integer id : ids) {
            whereConditions.append(id).append(",");
        }
        whereConditions.deleteCharAt(whereConditions.length() - 1).append(")");
        return mTemplate.remove(ColumnContacts.EVENT_TABLE_NAME, whereConditions.toString());
    }

    public int create(Event event) {
        return (int) mTemplate.create(ColumnContacts.EVENT_TABLE_NAME, generateContentValues(event, false));
    }

    public int update(Event event) {
        return mTemplate.update(ColumnContacts.EVENT_TABLE_NAME, generateContentValues(event, true), BaseColumns._ID + "  = ?", Integer.toString(event.getmId()));
    }

    public int getLatestEventId() {
        return mTemplate.getLatestId(ColumnContacts.EVENT_TABLE_NAME);
    }

    private ContentValues generateContentValues(Event event, boolean isUpdate) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ColumnContacts.EVENT_TITLE_COLUMN, event.getmTitle());
        contentValues.put(ColumnContacts.EVENT_CONTENT_COLUMN, event.getmContent());
        if (!isUpdate) {
            contentValues.put(ColumnContacts.EVENT_CREATED_TIME_COLUMN, DateTimeUtil.dateToStr(new Date()));
        } else {
            contentValues.put(ColumnContacts.EVENT_CREATED_TIME_COLUMN, event.getmCreatedTime());
        }
        contentValues.put(ColumnContacts.EVENT_IS_CLOCKED, event.getmIsClocked());
        contentValues.put(ColumnContacts.EVENT_UPDATED_TIME_COLUMN, DateTimeUtil.dateToStr(new Date()));
        contentValues.put(ColumnContacts.EVENT_REMIND_TIME_COLUMN, event.getmRemindTime());
        contentValues.put(ColumnContacts.EVENT_IS_IMPORTANT_COLUMN, event.getmIsImportant());
        return contentValues;
    }
}
