package com.example.touralbum

import android.os.Bundle
import android.text.InputType
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

//import android.annotation.SuppressLint;
//import android.view.View;
//import android.widget.Button;
//import android.view.View;
//import android.view.WindowInsets;
//import android.view.KeyEvent;
class CreateEvent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)
        val addButton = findViewById<ImageButton>(R.id.adding)
        supportActionBar?.hide()
        addButton.setOnClickListener { v: View? ->
            //jump to friendlist
            Toast.makeText(this@CreateEvent, "跳转到好友列表", Toast.LENGTH_SHORT).show()
        }
        val visibility = findViewById<RadioGroup>(R.id.visible)
        visibility.setOnCheckedChangeListener { group: RadioGroup?, checkedId: Int ->
            val r = findViewById<RadioButton>(checkedId)
            //r.getText();//获取被选中的单选按钮的值
            Toast.makeText(this@CreateEvent, r.text, Toast.LENGTH_SHORT).show()
        }
        val createBtn = findViewById<ImageButton>(R.id.create_btn)
        createBtn.setOnClickListener { v: View? ->
            //jump to sharedEvent
            Toast.makeText(this@CreateEvent, "创建成功", Toast.LENGTH_LONG).show()
            //todo 跳转到事件内容
        }
        val eventName = findViewById<EditText>(R.id.trip_title)
        eventName.setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
            val tripTitle = eventName.text.toString()
            //save tripTitle
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(eventName.windowToken, 0)
            Toast.makeText(this@CreateEvent, tripTitle, Toast.LENGTH_SHORT).show()
            false
        }
        val time = findViewById<EditText>(R.id.trip_date)
        time.setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
            val tripDate = time.text.toString()
            //save tripDate
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(time.windowToken, 0)
            Toast.makeText(this@CreateEvent, tripDate, Toast.LENGTH_SHORT).show()
            false
        }
        val location = findViewById<EditText>(R.id.location)
        location.setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
            val loca = location.text.toString()
            //save loca
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(location.windowToken, 0)
            location.inputType = InputType.TYPE_NULL
            Toast.makeText(this@CreateEvent, loca, Toast.LENGTH_SHORT).show()
            false
        }
        val backWard = findViewById<ImageButton>(R.id.bt_back)
        backWard.setOnClickListener { v: View? -> finish() }
    }
}