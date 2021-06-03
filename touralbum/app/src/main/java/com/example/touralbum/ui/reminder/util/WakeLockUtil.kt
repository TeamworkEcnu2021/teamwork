package com.example.touralbum.ui.reminder.util

import android.annotation.SuppressLint
import android.app.KeyguardManager
import android.content.*
import android.os.PowerManager
import android.view.WindowManager
import com.example.touralbum.ui.reminder.MemoApplication

/**
 * @author jsbintask@gmail.com
 * @date 2018/4/27 13:34
 */
object WakeLockUtil {
    /**
     * 唤醒手机屏幕并解锁
     */
    @SuppressLint("InvalidWakeLockTag")
    fun wakeUpAndUnlock() {
        // 获取电源管理器对象
        val pm = (MemoApplication.context!!.getSystemService(Context.POWER_SERVICE) as PowerManager)
        val screenOn = pm.isInteractive
        if (!screenOn) {
            // 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
            val wl = pm.newWakeLock(
                PowerManager.ACQUIRE_CAUSES_WAKEUP or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                "bright"
            )
            wl.acquire(1000) // 点亮屏幕
            wl.release() // 释放
        }
        // 屏幕解锁
        val keyguardManager = (MemoApplication.context!!.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager)
        val keyguardLock = keyguardManager.newKeyguardLock("unLock")
        // 屏幕锁定
        keyguardLock.reenableKeyguard()
        keyguardLock.disableKeyguard() // 解锁
    }
}