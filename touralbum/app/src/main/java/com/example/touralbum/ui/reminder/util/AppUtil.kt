package com.example.touralbum.ui.reminder.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * @author jsbintask@gmail.com
 * @date 2018/4/26 14:38
 */
object AppUtil {
    fun hideSoftInput(context: Context, view: View?) {
        val imm = (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        assert(view != null)
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
    }
}