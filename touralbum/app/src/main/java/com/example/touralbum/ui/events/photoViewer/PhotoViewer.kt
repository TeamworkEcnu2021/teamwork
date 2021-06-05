package com.example.touralbum.ui.events.photoViewer

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.touralbum.ui.events.Event
import com.example.touralbum.R
import com.example.touralbum.ui.events.eventContent.Album
import kotlin.math.abs


class PhotoViewer : AppCompatActivity() {

    private lateinit var event: Event
    private lateinit var album: Album
    private var pos : Int = 1
    private lateinit var photoViewerAdapter : PhotoViewerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_viewer)
        Log.d("d","照片查看器")

        //标题栏
        supportActionBar?.hide()
        val toolbar : Toolbar = findViewById(R.id.toolbar_photo_viewer)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.delete_photo -> {
                    val prefs = getSharedPreferences("${event.title}_${album.albumName}",Context.MODE_PRIVATE)
                    val editor = prefs.edit()
                    editor.remove("${pos+1}")
                    var i = pos+1
                    while(i<album.photoList.size){
                        val string = prefs.getString("${i+1}","").toString()
                        editor.putString("$i",string)
                        i++
                    }
                    editor.remove("$i")
                    val count = prefs.getInt("photoCount",0)
                    editor.putInt("photoCount",count-1)
                    editor.apply()
                    album.photoList.removeAt(pos)
                    if(album.photoList.isEmpty()){
                        finish()
                    }
                    photoViewerAdapter.notifyDataSetChanged()
                }
            }
            true
        }

        //取event和album数据
        val drawable = ContextCompat.getDrawable(this, R.drawable.ic_baseline_photo_24)
        event = Event("","","","")
        album = Album("",drawableToBitmap(drawable!!))
        pos = intent.getIntExtra("position",0)
        val eventName = intent.getStringExtra("eventName").toString()
        val albumName = intent.getStringExtra("albumName").toString()
        Log.d("d","$pos $eventName $albumName")
        event.getData(this,eventName)
        album.getData(this,eventName,albumName)
        Log.d("d","${event.title} ${event.date} ${event.member} ${event.dest}")
        Log.d("d","${album.albumName} ${album.photoList.size}")

        //设置viewpager
        photoViewerAdapter = PhotoViewerAdapter(album.photoList)
        val viewpager = findViewById<ViewPager2>(R.id.photo_view_pager)
        viewpager.adapter = photoViewerAdapter
        viewpager.isUserInputEnabled=true
        viewpager.orientation=ViewPager2.ORIENTATION_HORIZONTAL
        viewpager.setCurrentItem(pos,false)
        val mAnimator = ViewPager2.PageTransformer { page, position ->
            val absPos = abs(position)
            val scaleX: Float
            val scaleY: Float
            if (absPos > 1) {
                scaleX = 0f
                scaleY = 0f
            } else {
                scaleX = 1 - absPos
                scaleY = 1 - absPos
            }
            page.scaleX = scaleX
            page.scaleY = scaleY
        }
        viewpager.setPageTransformer(mAnimator)
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
}