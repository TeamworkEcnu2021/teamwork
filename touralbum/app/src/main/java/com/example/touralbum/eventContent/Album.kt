package com.example.touralbum.eventContent

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.example.touralbum.albumContent.Photo
import java.io.ByteArrayInputStream

class Album(var albumName:String, var surfaceImage : Bitmap) {
    var photoList = ArrayList<Photo>()

    fun getData(context:Context,eventName:String,albumName:String){
        val prefs = context.getSharedPreferences("${eventName}_${albumName}", Context.MODE_PRIVATE)
        this.albumName= prefs.getString("albumName",albumName).toString()
        val count = prefs.getInt("photoCount",0)
        var i = 1
        while(i<=count){
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
        this.surfaceImage=this.photoList[1].image
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