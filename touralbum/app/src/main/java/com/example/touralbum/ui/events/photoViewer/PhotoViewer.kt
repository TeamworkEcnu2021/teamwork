package com.example.touralbum.ui.events.photoViewer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.example.touralbum.ui.events.Event
import com.example.touralbum.R
import com.example.touralbum.ui.events.eventContent.Album
import kotlin.math.abs


class PhotoViewer : AppCompatActivity() {

    private lateinit var event: Event
    private lateinit var album: Album
    var pos : Int = 1
    private lateinit var photoViewerAdapter : PhotoViewerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_viewer)

        //标题栏
        supportActionBar?.hide()
        val toolbar : Toolbar = findViewById(R.id.toolbar_photo_viewer)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        //取event和album数据
        val intent = Intent()
        pos = intent.getIntExtra("position",1)
        val eventName = intent.getStringExtra("eventName").toString()
        val albumName = intent.getStringExtra("albumName").toString()
        event.getData(this,eventName)
        album.getData(this,eventName,albumName)

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.photo_viewer_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_photo -> {
                val prefs = getSharedPreferences("${event.title}_${album.albumName}",Context.MODE_PRIVATE)
                val editor = prefs.edit()
                editor.remove("$pos")
                var i = pos
                while(i<album.photoList.size){
                    val string = prefs.getString("${i+1}","").toString()
                    editor.putString("$i",string)
                    i++
                }
                editor.remove("$i")
                editor.apply()
                album.photoList.removeAt(pos)
                if(album.photoList.isEmpty()){
                    finish()
                }
                photoViewerAdapter.notifyDataSetChanged()
            }
        }
        return true
    }
}