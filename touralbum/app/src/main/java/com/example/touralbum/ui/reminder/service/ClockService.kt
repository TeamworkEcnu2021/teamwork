package com.example.touralbum.ui.reminder.service

import android.app.*
import android.content.*
import android.os.IBinder
import android.util.Log
import com.example.touralbum.ui.reminder.activity.ClockActivity
import com.example.touralbum.ui.reminder.dao.EventDao
import com.example.touralbum.ui.reminder.util.WakeLockUtil

class ClockService : Service() {
    private val mEventDao: EventDao = EventDao.instance
    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: onStartCommand")
        WakeLockUtil.wakeUpAndUnlock()
        postToClockActivity(applicationContext, intent)
        return super.onStartCommand(intent, flags, startId)
    }

    private fun postToClockActivity(context: Context, intent: Intent) {
        val i = Intent()
        i.setClass(context, ClockActivity::class.java)
        i.putExtra(EXTRA_EVENT_ID, intent.getIntExtra(EXTRA_EVENT_ID, -1))
        val event = mEventDao.findById(intent.getIntExtra(EXTRA_EVENT_ID, -1))
            ?: return
        i.putExtra(EXTRA_EVENT_REMIND_TIME, intent.getStringExtra(EXTRA_EVENT_REMIND_TIME))
        i.putExtra(EXTRA_EVENT, event)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(i)
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: ")
        super.onDestroy()
    }

    companion object {
        private const val TAG = "ClockService"
        const val EXTRA_EVENT_ID = "extra.event.id"
        const val EXTRA_EVENT_REMIND_TIME = "extra.event.remind.time"
        const val EXTRA_EVENT = "extra.event"
    }

    init {
        Log.d(TAG, "ClockService: Constructor")
    }
}