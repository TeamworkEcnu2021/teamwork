package com.example.touralbum.ui.reminder.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


public class AppUtil {
    public static void hideSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        assert view != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
