package com.example.touralbum.ui.diary

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.example.touralbum.R
import com.example.touralbum.ui.diary.note.Note
import com.example.touralbum.ui.diary.note.NoteDataBaseHelper
import com.example.touralbum.ui.diary.note.NoteInfo
import java.text.SimpleDateFormat
import java.util.*

class CreateDairy : AppCompatActivity() {
    private lateinit var bt_done: Button
    private lateinit var bt_back: Button
    private lateinit var tv_now: TextView
    private lateinit var et_title: EditText
    private lateinit var et_content: EditText

    //记录当前编辑的笔记对象，用于对比是否修改
    private var currentNote: NoteInfo? = null

    //记录是否是插入（更新/编辑）状态
    private var insertFlag = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_dairy)

        supportActionBar?.hide()
        initView()
        setListener()
        val bundle = intent.extras
        //点击ListView里的一个items时跳转
        if (bundle != null) {
            currentNote = bundle.getSerializable("noteInfo") as NoteInfo?
            et_title.setText(currentNote!!.title)
            et_content.setText(currentNote!!.content)
            insertFlag = false
        }
    }

    //初始化视图
    private fun initView() {
        //获取完成按钮
        bt_done = findViewById(R.id.dairy_done)
        //获取回退按钮
        bt_back = findViewById(R.id.back_to_dairy)
        //获取文本框
        tv_now = findViewById(R.id.tv_now)
        et_content = findViewById(R.id.edit_content)
        et_title = findViewById(R.id.title_input)
        val date = Date()
        @SuppressLint("SimpleDateFormat") val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        tv_now.text = sdf.format(date)
    }

    //设置监听器
    private fun setListener() {
        //设置监听并跳转到上个页面
        bt_back.setOnClickListener { v: View? -> finish() }
        //设置监听并跳转到完成界面
        bt_done.setOnClickListener {
            if (et_title.text.toString() == "" || et_content.text.toString() == "") {
                Toast.makeText(this@CreateDairy, "保存失败！标题或内容不可为空", Toast.LENGTH_SHORT).show()
            } else {
                saveNote()
                finish()
            }
        }
    }

    //保存笔记到数据库，判断是新建还是更新
    private fun saveNote() {
        val dbHepler: NoteDataBaseHelper = DiaryFragment.dbHelper
        val values = ContentValues()
        values.put(Note.title, et_title.text.toString())
        values.put(Note.content, et_content.text.toString())
        values.put(Note.time, tv_now.text.toString())
        if (insertFlag) {
            Note.insertNote(dbHepler, values)
        } else {
            Note.updateNote(dbHepler, currentNote!!.id!!.toInt(), values)
        }
    }

    //overrider onbackpress函数，修改内容时提示保存，否则不保存直接返回
    override fun onBackPressed() {
        var display = false
        if (insertFlag) {
            if (et_title.text.toString() != "" &&
                et_content.text.toString() != ""
            ) {
                display = true
            }
        } else {
            if (et_title.text.toString() != currentNote!!.title ||
                et_content.text.toString() != currentNote!!.content
            ) {
                display = true
            }
        }
        if (display) {
            val title = "警告"
            AlertDialog.Builder(this@CreateDairy)
                .setTitle(title)
                .setMessage("是否保存当前内容?")
                .setPositiveButton(R.string.btn_confirm) { dialog, which ->
                    saveNote()
                    Toast.makeText(this@CreateDairy, R.string.save_succ, Toast.LENGTH_LONG).show()
                    //更新当前Note对象的值 防止选择保存后按返回仍显示此警告对话框
                    currentNote!!.title = et_title.text.toString()
                    currentNote!!.content = et_content.text.toString()
                }
                .setNegativeButton(R.string.btn_cancel) { dialog, which ->
                    val intent = Intent(this@CreateDairy, DiaryFragment::class.java)
                    startActivity(intent)
                }.create().show()
        } else {
            val intent = Intent(this@CreateDairy, DiaryFragment::class.java)
            startActivity(intent)
        }
    }
}