package com.example.touralbum.ui.events

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.touralbum.R
import com.example.touralbum.ui.events.eventContent.Album

class Event(var title:String, var date:String, var member:String, var dest:String) {
    var albumList = ArrayList<Album>()

    fun getData(context: Context,eventName : String){
        val prefs = context.getSharedPreferences("event_${eventName}", Context.MODE_PRIVATE)
        this.title= prefs.getString("title",eventName).toString()
        this.date= prefs.getString("date","").toString()
        this.member= prefs.getString("member","").toString()
        this.dest= prefs.getString("dest","").toString()
        val count = prefs.getInt("albumCount",0)
        var i = 1
        while(i<=count){
            val albumName = prefs.getString("$i","").toString()
            val drawable = ContextCompat.getDrawable(context, R.drawable.ic_baseline_photo_24)
            val surfaceImage = drawableToBitmap(drawable!!)
            val album = Album(albumName,surfaceImage)
            album.getData(context,eventName,albumName)
            this.albumList.add(album)
            i++
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
}
/*
用event_${event.title}命名的shared preference存储
"title", event.title
"date",event.date
"member",event.member
"dest",event.dest
"albumCount",event.albumList.size
"1",albumList[1].albumName
"2",albumList[2].albumName
"3",albumList[3].albumName
...
 */
/*
用events命名的shared preference存储
"eventCount",event总数
"1"，...
“2”，...
 */