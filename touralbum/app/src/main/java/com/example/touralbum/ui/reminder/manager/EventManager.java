package com.example.touralbum.ui.reminder.manager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.touralbum.ui.reminder.Constants;
import com.example.touralbum.ui.reminder.dao.EventDao;
import com.example.touralbum.ui.reminder.entity.Event;
import com.example.touralbum.ui.reminder.exception.MemoException;
import com.example.touralbum.ui.reminder.util.DateTimeUtil;
import com.example.touralbum.ui.reminder.util.StringUtil;
import com.example.touralbum.ui.reminder.util.ToastUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.example.touralbum.R;

public class EventManager {
    public static final String TAG = "EventManager";
    private static EventManager mEventManager = new EventManager();
    private EventDao mEventDao = EventDao.getInstance();
    public List<Integer> getDeletedIds() {
        return deletedIds;
    }
    public void setDeletedIds(List<Integer> deletedIds) {
        this.deletedIds = deletedIds;
    }
    private List<Event> events = new ArrayList<>();
    private List<Integer> deletedIds = new ArrayList<>();

    private EventManager(){
    }

    public static EventManager getInstance() {
        return mEventManager;
    }

    public List<Event> findAll() {
        events =  mEventDao.findAll();
        return events;
    }

    public void flushData() {
        events = mEventDao.findAll();
    }

    public List<Event> getEvents() {
        return events;
    }

    public void removeEvents(final Handler handler, final List<Integer> ids) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int result = mEventDao.remove(ids);
                    Message message = new Message();
                    message.what = Constants.HANDLER_SUCCESS;
                    message.obj = result;
                    message.setTarget(handler);
                    message.sendToTarget();
                } catch (Exception e) {
                    Log.e(TAG, "run: ", e);
                    handler.obtainMessage(Constants.HANDLER_FAILED, new MemoException(e)).sendToTarget();
                }
            }
        }).start();
    }

    public boolean removeEvent(Integer id) {
        return mEventDao.remove(Collections.singletonList(id)) != 0;
    }

    public boolean saveOrUpdate(Event event) {
        try {
            if (event.getmId() != null) {
                mEventDao.update(event);
            } else {
                mEventDao.create(event);
            }
            return true;
        } catch (Exception e) {
            Log.e(TAG, "saveOrUpdate: ", e);
            return false;
        }
    }

    public int getLatestEventId() {
        return mEventDao.getLatestEventId();
    }

    public Event getOne(Integer id) {
        return mEventDao.findById(id);
    }

    public boolean checkEventField(Event event) {
        if (event == null) {
            return false;
        }
        if (StringUtil.isBlank(event.getmTitle())) {
            ToastUtil.showToastShort(R.string.event_can_not_empty);
            return false;
        }
        if (StringUtil.isBlank(event.getmContent())) {
            ToastUtil.showToastShort(R.string.content_can_not_empty);
            return false;
        }
        if (StringUtil.isBlank(event.getmRemindTime())) {
            ToastUtil.showToastShort(R.string.remind_time_can_not_empty);
            return false;
        }
        if (DateTimeUtil.str2Date(event.getmRemindTime()) == null) {
            ToastUtil.showToastShort(R.string.invalid_remind_time_format);
            return false;
        }
        if (new Date().getTime() > DateTimeUtil.str2Date(event.getmRemindTime()).getTime()) {
            ToastUtil.showToastShort(R.string.remind_time_deprecated);
            return false;
        }

        return true;
    }
}
