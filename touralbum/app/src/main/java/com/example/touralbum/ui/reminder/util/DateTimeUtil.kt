package com.example.touralbum.ui.reminder.util

import com.example.touralbum.ui.reminder.Constants
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author jsbintask@gmail.com
 * @date 2018/4/25 15:53
 */
object DateTimeUtil {
    fun dateToStr(date: Date?): String {
        val sdf = SimpleDateFormat(Constants.DEFAULT_TIME_FORMAT, Locale.CHINA)
        return sdf.format(date)
    }

    fun str2Date(src: String?): Date? {
        val sdf = SimpleDateFormat(Constants.DEFAULT_TIME_FORMAT, Locale.CHINA)
        try {
            return sdf.parse(src)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return null
    }

    fun needClock(date: Date, date2: Date): Boolean {
        return if (Math.abs((date.time - date2.time).toDouble() / 1000) <= 1.00001) {
            true
        } else false
    }

    fun canClock(date: Date): Boolean {
        return if (Math.abs(date.time - Date().time) < 5000) {
            true
        } else false
    }
}