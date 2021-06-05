package com.example.touralbum.ui.events.eventContent

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.util.Base64
import androidx.core.content.ContextCompat
import com.example.touralbum.R
import com.example.touralbum.ui.events.albumContent.Photo
import java.io.ByteArrayInputStream

class Album(var albumName:String, var surfaceImage : Bitmap) {
    var photoList = ArrayList<Photo>()

    fun getData(context:Context,eventName:String,albumName:String){
        val prefs = context.getSharedPreferences("${eventName}_${albumName}", Context.MODE_PRIVATE)
        this.albumName= prefs.getString("albumName",albumName).toString()
        val count = prefs.getInt("photoCount",0)
        if(count == 0){
            val drawable = ContextCompat.getDrawable(context, R.drawable.ic_baseline_photo_24)
            surfaceImage = drawableToBitmap(drawable!!)
        }else {
            var i = 1
            while (i <= count) {
                //第一步:取出字符串形式的Bitmap
                val imageString = prefs.getString("${i}", "")
                //第二步:利用Base64将字符串转换为ByteArrayInputStream
                val byteArray: ByteArray = Base64.decode(imageString, Base64.DEFAULT)
                val byteArrayInputStream = ByteArrayInputStream(byteArray)
                //第三步:利用ByteArrayInputStream生成Bitmap
                val photoImage = BitmapFactory.decodeStream(byteArrayInputStream)
                this.photoList.add(Photo(photoImage))
                i++
            }
            this.surfaceImage = this.photoList[0].image
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
用${event.title}_${album.albumName}命名的shared preference存储
"albumName", album.albumName
"photoCount",album.photoList.size
"1",photoList[1].stringConvertedFromBitmap
"2",photoList[2].stringConvertedFromBitmap
"3",photoList[3].stringConvertedFromBitmap
...
 */