package com.example.touralbum.ui.reminder.util

/**
 * @author jsbintask@gmail.com
 * @date 2018/4/26 15:00
 */
object StringUtil {
    fun getMultiNumber(number: Int): String {
        return if (number < 10) "0$number" else Integer.toString(number)
    }

    fun getLocalMonth(month: Int): String {
        return getMultiNumber(month + 1)
    }

    fun isBlank(src: String?): Boolean {
        return src == null || src.trim { it <= ' ' }.length == 0
    }
}