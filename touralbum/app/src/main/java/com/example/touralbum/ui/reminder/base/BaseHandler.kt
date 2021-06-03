package com.example.touralbum.ui.reminder.base

import android.os.Handler
import android.os.Message
import com.example.touralbum.ui.reminder.Constants
import com.example.touralbum.ui.reminder.exception.MemoException
import java.lang.ref.WeakReference

class BaseHandler(baseActivity: BaseActivity) : Handler() {
    private val mReference: WeakReference<BaseActivity>
    override fun handleMessage(msg: Message) {
        if (msg.what == Constants.HANDLER_SUCCESS) {
            if (mReference.get() is HandlerResultCallBack) {
                val callBack = mReference.get() as HandlerResultCallBack?
                callBack!!.handlerSuccess(msg)
            }
        } else if (msg.what == Constants.HANDLER_FAILED) {
            if (mReference.get() is HandlerResultCallBack) {
                val callBack = mReference.get() as HandlerResultCallBack?
                callBack!!.handlerFailed(msg.obj as MemoException)
            }
        }
    }

    interface HandlerResultCallBack {
        fun handlerSuccess(msg: Message?)
        fun handlerFailed(e: MemoException?)
    }

    init {
        mReference = WeakReference(baseActivity)
    }
}