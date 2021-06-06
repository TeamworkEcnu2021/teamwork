package com.example.touralbum.ui.reminder.util;

import android.widget.Toast;

import com.example.touralbum.ui.reminder.MemoApplication;

public class ToastUtil {
    public static void showToastShort(String msg) {
        Toast.makeText(MemoApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showToastShort(int res) {
        Toast.makeText(MemoApplication.getContext(), MemoApplication.getContext().getString(res), Toast.LENGTH_SHORT).show();
    }
}
