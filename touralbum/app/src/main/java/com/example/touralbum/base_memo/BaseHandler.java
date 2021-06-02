package com.example.touralbum.base_memo;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

import com.example.touralbum.Constants;
import com.example.touralbum.exception_memo.MemoException;

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
