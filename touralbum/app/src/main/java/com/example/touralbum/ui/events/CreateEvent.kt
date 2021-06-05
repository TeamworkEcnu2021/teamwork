package com.example.touralbum.ui.events

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.touralbum.R

class CreateEvent : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)
        Log.d("d","创建事件活动")

        //标题栏
        supportActionBar?.hide()

        //返回键
        val backWard = findViewById<ImageButton>(R.id.bt_back)
        backWard.setOnClickListener { v: View? -> finish() }
        Log.d("d","设置返回键")

        //创建事件
        val createBtn = findViewById<ImageButton>(R.id.create_btn)
        createBtn.setOnClickListener { v: View? ->
            val eventMember = findViewById<EditText>(R.id.trip_member)
            val eventName = findViewById<EditText>(R.id.trip_title)
            val time = findViewById<EditText>(R.id.trip_date)
            val location = findViewById<EditText>(R.id.location)
            val title = eventName.text.toString()
            val date = time.text.toString()
            val member = eventMember.text.toString()
            val dest = location.text.toString()
            val newEvent = Event(title,date,member,dest)
            saveAndCreate(newEvent)
            val intent = Intent()
            intent.putExtra("newEventTitle", newEvent.title)
            setResult(RESULT_OK, intent)
            Log.d("d","设置intent中的返回信息事件名并finish")
            finish()
        }
    }

    //创建并保存事件
    private fun saveAndCreate(event : Event){
        var editor = getSharedPreferences("event_${event.title}", Context.MODE_PRIVATE).edit()
        editor.putString("title", event.title)
        editor.putString("date",event.date)
        editor.putString("member",event.member)
        editor.putString("dest",event.dest)
        editor.putInt("albumCount",event.albumList.size)
        editor.apply()
        val prefs = getSharedPreferences("events",Context.MODE_PRIVATE)
        editor = prefs.edit()
        val count = prefs.getInt("eventCount",0)
        editor.putInt("eventCount",count+1)
        editor.putString("${count+1}",event.title)
        editor.apply()
        Log.d("d","把事件数据保存在名为event_事件名的sp中，将events中数量修改，并添加到最后")
    }
}