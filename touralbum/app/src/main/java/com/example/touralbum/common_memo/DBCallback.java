package com.example.touralbum.common_memo;


import android.database.Cursor;

public interface DBCallback <T> {
    T cursorToInstance(Cursor cursor);
}
