package com.example.touralbum.ui.reminder;

import android.app.Application;
import android.content.Context;

public class MemoApplication extends Application{
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }
}