package com.example.touralbum.ui.reminder.manager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.*
import com.example.touralbum.ui.reminder.MemoApplication
import java.util.*

class ClockManager private constructor() {
    fun cancelAlarm(pendingIntent: PendingIntent?) {
        alarmManager.cancel(pendingIntent)
    }

    fun addAlarm(pendingIntent: PendingIntent?, performTime: Date?) {
        cancelAlarm(pendingIntent)
        alarmManager[AlarmManager.RTC_WAKEUP, performTime!!.time] = pendingIntent
    }

    companion object {
        val instance = ClockManager()
        private val alarmManager: AlarmManager
            private get() = MemoApplication.Companion.context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }
}