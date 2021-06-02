package com.example.touralbum.eventContent


import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Base64
import android.widget.AdapterView
import android.widget.EditText
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.touralbum.Event
import com.example.touralbum.R
import java.io.ByteArrayInputStream
import java.io.File


class EventContent : AppCompatActivity() {

    private val funcBtnList = ArrayList<FuncBtn>()
    private lateinit var event : Event
    private lateinit var albumAdapter : AlbumAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_content)

        //取事件数据
        val eventTitle = intent.getStringExtra("eventName")
        event.getData(this,eventTitle!!)

        //标题栏
        supportActionBar?.hide()
        val toolbar : Toolbar = findViewById(R.id.toolbar_event_content)
        toolbar.title = event.title
        toolbar.setNavigationOnClickListener {
            finish()
        }

        //相册列表
        val layoutManager = GridLayoutManager(this,2)
        val recyclerView : RecyclerView = findViewById(R.id.albumList)
        recyclerView.layoutManager = layoutManager
        albumAdapter = AlbumAdapter(event.albumList,event.title)
        recyclerView.adapter = albumAdapter

        //按钮列表
        initFuncBtn()
        val gridView :GridView = findViewById(R.id.option_button)
        val funcBtnAdapter = FuncBtnAdapter(this,funcBtnList)
        gridView.adapter = funcBtnAdapter
        gridView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> {//事件信息
                    val inputDialog = AlertDialog.Builder(this)
                    inputDialog.apply {
                        setTitle("事件信息")
                        setMessage("title: ${event.title}\ndate: ${event.date}\nmembers: ${event.member}\ndestination: ${event.dest}")
                        setPositiveButton("确定") { dialog, which -> }
                    }
                }
                1 -> {//创建新相册
                    val editText = EditText(this)
                    val inputDialog = AlertDialog.Builder(this)
                    inputDialog.setTitle("请输入新相册名").setView(editText)
                    inputDialog.setPositiveButton("确定") { dialog, which ->
                        val newName = editText.text.toString()
                        val drawable =
                            ContextCompat.getDrawable(this, R.drawable.ic_baseline_photo_24)
                        event.albumList.add(Album(newName, drawableToBitmap(drawable!!)))
                        val prefs =
                            getSharedPreferences("${event.title}_${newName}", Context.MODE_PRIVATE)
                        val editor = prefs.edit()
                        editor.putString("albumName", newName)
                        editor.putInt("photoCount", 0)
                        editor.apply()
                        albumAdapter.notifyDataSetChanged()
                        Toast.makeText(this, "创建成功", Toast.LENGTH_SHORT).show()
                    }
                    inputDialog.setNegativeButton("取消") { dialog, which ->
                        Toast.makeText(this, "取消了", Toast.LENGTH_SHORT).show()
                    }
                    inputDialog.show()
                }
                2 -> {//旅行日记
                    Toast.makeText(this, "diary", Toast.LENGTH_SHORT).show()
                    //todo 跳转到对应的日记页面
                }
                3 -> {//删除事件
                    val inputDialog = AlertDialog.Builder(this)
                    inputDialog.apply {
                        setTitle("确定删除？")
                        setMessage("删除之后不可恢复")
                        setPositiveButton("确定") { dialog, wgich ->
                            for (album in event.albumList) {
                                val file =
                                    File("/data/data/${packageName}/shared_prefs/${event.title}_${album.albumName}")
                                if (file.exists()) {
                                    file.delete()
                                }
                            }
                            val file =
                                File("/data/data/${packageName}/shared_prefs/event_${event.title}")
                            if (file.exists()) {
                                file.delete()
                            }
                            val prefs = getSharedPreferences("events",Context.MODE_PRIVATE)
                            val editor = prefs.edit()
                            val count = prefs.getInt("eventCount",0)
                            var i=1
                            while(i<=count){
                                val title = prefs.getString("$i","")
                                if(title == event.title){
                                    var j = i
                                    while(j<count){
                                        val t = prefs.getString("${j+1}","")
                                        editor.putString("$j",t)
                                        j++
                                    }
                                    editor.remove("$count")
                                    editor.apply()
                                    finish()
                                }
                                i++
                            }
                            editor.apply()
                            finish()
                        }
                        setNegativeButton("取消") { dialog, wgich -> }
                    }
                }
            }
        }
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        val w = drawable.intrinsicWidth
        val h = drawable.intrinsicHeight
        // 取 drawable 的颜色格式
        val config = if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
        val bitmap = Bitmap.createBitmap(w, h, config)
        //建立对应 bitmap 的画布
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, w, h)
        // 把 drawable 内容画到画布中
        drawable.draw(canvas)
        return bitmap
    }

    private fun initFuncBtn() {
        funcBtnList.add(FuncBtn(R.drawable.ic_event_info,"event info"))
        funcBtnList.add(FuncBtn(R.drawable.ic_add_album,"add album"))
        funcBtnList.add(FuncBtn(R.drawable.ic_event_diary,"diary"))
        funcBtnList.add(FuncBtn(R.drawable.ic_delete,"delete event"))
    }
}