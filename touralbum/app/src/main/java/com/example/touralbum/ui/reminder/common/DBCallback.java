package com.example.touralbum.ui.reminder.common;


import android.database.Cursor;

public interface DBCallback <T> {
    T cursorToInstance(Cursor cursor);
}
