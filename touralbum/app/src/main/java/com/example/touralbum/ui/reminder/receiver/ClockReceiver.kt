package com.example.touralbum.ui.reminder.receiver

import android.content.*
import android.util.Log
import com.example.touralbum.ui.reminder.activity.ClockActivity
import com.example.touralbum.ui.reminder.dao.EventDao
import com.example.touralbum.ui.reminder.util.WakeLockUtil

class ClockReceiver : BroadcastReceiver() {
    private val mEventDao: EventDao = EventDao.instance
    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "onReceive: " + intent.action)
        WakeLockUtil.wakeUpAndUnlock()
        postToClockActivity(context, intent)
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

    companion object {
        private const val TAG = "ClockReceiver"
        const val EXTRA_EVENT_ID = "extra.event.id"
        const val EXTRA_EVENT_REMIND_TIME = "extra.event.remind.time"
        const val EXTRA_EVENT = "extra.event"
    }

    init {
        Log.d(TAG, "ClockReceiver: Constructor")
    }
}