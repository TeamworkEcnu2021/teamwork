package com.example.touralbum.ui.events

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.touralbum.R

class CreateEvent : AppCompatActivity() {

    private lateinit var newEvent : Event

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)

        //标题栏
        supportActionBar?.hide()

        //事件成员
        val eventMember = findViewById<EditText>(R.id.trip_member)
        eventMember.setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
            val tripMember = eventMember.text.toString()
            newEvent.member = tripMember
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(eventMember.windowToken, 0)
            Toast.makeText(this@CreateEvent, "编辑成功", Toast.LENGTH_SHORT).show()
            false
        }

        //创建事件
        val createBtn = findViewById<ImageButton>(R.id.create_btn)
        createBtn.setOnClickListener { v: View? ->
            saveAndCreate(newEvent)
            Toast.makeText(this@CreateEvent, "创建成功", Toast.LENGTH_LONG).show()
            val intent = Intent()
            intent.putExtra("newEventTitle", newEvent.title)
            setResult(RESULT_OK, intent)
            finish()
        }

        //事件名称
        val eventName = findViewById<EditText>(R.id.trip_title)
        eventName.setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
            val tripTitle = eventName.text.toString()
            newEvent.title = tripTitle
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(eventName.windowToken, 0)
            Toast.makeText(this@CreateEvent, tripTitle, Toast.LENGTH_SHORT).show()
            false
        }

        //事件时间
        val time = findViewById<EditText>(R.id.trip_date)
        time.setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
            val tripDate = time.text.toString()
            newEvent.date = tripDate
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(time.windowToken, 0)
            Toast.makeText(this@CreateEvent, tripDate, Toast.LENGTH_SHORT).show()
            false
        }

        //事件地点
        val location = findViewById<EditText>(R.id.location)
        location.setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
            val loca = location.text.toString()
            newEvent.dest = loca
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(location.windowToken, 0)
            location.inputType = InputType.TYPE_NULL
            Toast.makeText(this@CreateEvent, loca, Toast.LENGTH_SHORT).show()
            false
        }

        //返回键
        val backWard = findViewById<ImageButton>(R.id.bt_back)
        backWard.setOnClickListener { v: View? -> finish() }
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
    }
}