package com.example.touralbum.ui.reminder.manager

import android.os.*
import android.util.Log
import com.example.touralbum.R
import com.example.touralbum.ui.reminder.Constants
import com.example.touralbum.ui.reminder.dao.EventDao
import com.example.touralbum.ui.reminder.entity.Event
import com.example.touralbum.ui.reminder.exception.MemoException
import com.example.touralbum.ui.reminder.util.DateTimeUtil
import com.example.touralbum.ui.reminder.util.StringUtil
import com.example.touralbum.ui.reminder.util.ToastUtil
import java.util.*

class EventManager private constructor() {
    private val mEventDao: EventDao = EventDao.instance
    var events: List<Event?>? = ArrayList()
        private set
    var deletedIds: List<Int?>? = ArrayList()
    fun findAll(): List<Event?>? {
        events = mEventDao.findAll()
        return events
    }

    fun flushData() {
        events = mEventDao.findAll()
    }

    fun removeEvents(handler: Handler, ids: List<Int?>?) {
        Thread {
            try {
                val result = mEventDao.remove(ids)
                val message = Message()
                message.what = Constants.HANDLER_SUCCESS
                message.obj = result
                message.target = handler
                message.sendToTarget()
            } catch (e: Exception) {
                Log.e(TAG, "run: ", e)
                handler.obtainMessage(Constants.HANDLER_FAILED, MemoException(e)).sendToTarget()
            }
        }.start()
    }

    fun removeEvent(id: Int?): Boolean {
        return mEventDao.remove(listOf(id)) != 0
    }

    fun saveOrUpdate(event: Event): Boolean {
        return try {
            if (event.getmId() != null) {
                mEventDao.update(event)
            } else {
                mEventDao.create(event)
            }
            true
        } catch (e: Exception) {
            Log.e(TAG, "saveOrUpdate: ", e)
            false
        }
    }

    val latestEventId: Int
        get() = mEventDao.latestEventId

    fun getOne(id: Int?): Event? {
        return mEventDao.findById(id)
    }

    fun checkEventField(event: Event?): Boolean {
        if (event == null) {
            return false
        }
        if (StringUtil.isBlank(event.getmTitle())) {
            ToastUtil.showToastShort(R.string.event_can_not_empty)
            return false
        }
        if (StringUtil.isBlank(event.getmContent())) {
            ToastUtil.showToastShort(R.string.content_can_not_empty)
            return false
        }
        if (StringUtil.isBlank(event.getmRemindTime())) {
            ToastUtil.showToastShort(R.string.remind_time_can_not_empty)
            return false
        }
        if (DateTimeUtil.str2Date(event.getmRemindTime()) == null) {
            ToastUtil.showToastShort(R.string.invalid_remind_time_format)
            return false
        }
        if (Date().time > DateTimeUtil.str2Date(event.getmRemindTime())!!.time) {
            ToastUtil.showToastShort(R.string.remind_time_deprecated)
            return false
        }
        return true
    }

    companion object {
        const val TAG = "EventManager"
        val instance = EventManager()
    }
}