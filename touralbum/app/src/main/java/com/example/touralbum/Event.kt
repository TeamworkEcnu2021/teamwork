package com.example.touralbum

import android.content.Context
import com.example.touralbum.eventContent.Album

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
            var albumName = prefs.getString("$i","").toString()
            lateinit var album : Album
            album.getData(context,eventName,albumName)
            this.albumList.add(album)
            i++
        }
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