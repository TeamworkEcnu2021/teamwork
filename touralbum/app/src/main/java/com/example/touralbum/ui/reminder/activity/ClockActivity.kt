package com.example.touralbum.ui.reminder.activity

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Vibrator
import android.view.LayoutInflater
import android.widget.*
import com.example.touralbum.R
import com.example.touralbum.ui.reminder.base.BaseActivity
import com.example.touralbum.ui.reminder.entity.Event
import com.example.touralbum.ui.reminder.manager.EventManager
import com.example.touralbum.ui.reminder.service.ClockService
import com.example.touralbum.ui.reminder.util.AlertDialogUtil

class ClockActivity : BaseActivity() {
    private var mediaPlayer: MediaPlayer? = null
    private var mVibrator: Vibrator? = null
    private val mEventManger: EventManager = EventManager.instance
    private var event: Event? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setListener() {}
    override fun initView() {}
    private fun clock() {
        mediaPlayer!!.start()
        val pattern = longArrayOf(1500, 1000)
        mVibrator!!.vibrate(pattern, 0)
        //获取自定义布局
        val inflate = LayoutInflater.from(this).inflate(R.layout.dialog_alarm_layout_memo, null)
        val textView = inflate.findViewById<TextView>(R.id.tv_event)
        textView.text =
            String.format(getString(R.string.clock_event_msg_template), event!!.getmTitle())
        val btnConfirm = inflate.findViewById<Button>(R.id.btn_confirm)
        val alertDialog = AlertDialogUtil.showDialog(this, inflate)
        btnConfirm.setOnClickListener {
            mediaPlayer!!.stop()
            mVibrator!!.cancel()
            alertDialog!!.dismiss()
            finish()
        }
        alertDialog!!.show()
    }

    override fun onStart() {
        super.onStart()
        clock()
    }

    override fun initData() {
        mediaPlayer = MediaPlayer.create(applicationContext, R.raw.clock)
        mVibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        val intent = intent
        event = intent.getParcelableExtra(ClockService.Companion.EXTRA_EVENT)
        if (event == null) {
            finish()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        clock()
    }

    override val contentView: Int
        get() = R.layout.activity_clock_memo

    companion object {
        private const val TAG = "ClockActivity"
        const val EXTRA_CLOCK_EVENT = "clock.event"
    }
}