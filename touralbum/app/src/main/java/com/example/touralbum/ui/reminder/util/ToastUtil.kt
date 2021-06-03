package com.example.touralbum.ui.reminder.util

import android.widget.Toast
import com.example.touralbum.ui.reminder.MemoApplication

/**
 * @author jsbintask@gmail.com
 * @date 2018/4/25 17:08
 */
object ToastUtil {
    fun showToastShort(msg: String?) {
        Toast.makeText(MemoApplication.context, msg, Toast.LENGTH_SHORT).show()
    }

    fun showToastShort(res: Int) {
        Toast.makeText(
            MemoApplication.context,
            MemoApplication.context!!.getString(res),
            Toast.LENGTH_SHORT
        ).show()
    }
}