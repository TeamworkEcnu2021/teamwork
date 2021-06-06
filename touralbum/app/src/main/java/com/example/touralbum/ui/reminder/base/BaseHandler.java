package com.example.touralbum.ui.reminder.base;

import android.os.Handler;
import android.os.Message;

import com.example.touralbum.ui.reminder.Constants;
import com.example.touralbum.ui.reminder.exception.MemoException;

import java.lang.ref.WeakReference;

public class BaseHandler extends Handler {
    private WeakReference<BaseActivity> mReference;

    public BaseHandler(BaseActivity baseActivity) {
        super();
        mReference = new WeakReference<BaseActivity>(baseActivity);
    }

    @Override
    public void handleMessage(Message msg) {
        if (msg.what == Constants.HANDLER_SUCCESS) {
            if (mReference.get() instanceof HandlerResultCallBack) {
                HandlerResultCallBack callBack = (HandlerResultCallBack) mReference.get();
                callBack.handlerSuccess(msg);
            }
        } else if (msg.what == Constants.HANDLER_FAILED) {
            if (mReference.get() instanceof HandlerResultCallBack) {
                HandlerResultCallBack callBack = (HandlerResultCallBack) mReference.get();
                callBack.handlerFailed((MemoException) msg.obj);
            }
        }
    }

    public interface HandlerResultCallBack {
        void handlerSuccess(Message msg);

        void handlerFailed(MemoException e);
    }
}
