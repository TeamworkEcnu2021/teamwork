package com.example.touralbum.eventContent

import android.os.Bundle
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.touralbum.R

class EventContent : AppCompatActivity() {

    private val albumList = ArrayList<Album>()
    private val funcBtnList = ArrayList<FuncBtn>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_content)
        supportActionBar?.hide()
        initAlbum()
        val layoutManager = GridLayoutManager(this,2)
        val recyclerView : RecyclerView = findViewById(R.id.albumList)
        recyclerView.layoutManager = layoutManager
        val albumAdapter = AlbumAdapter(albumList)
        recyclerView.adapter = albumAdapter
        initFuncBtn()
        val gridView :GridView = findViewById(R.id.option_button)
        val funcBtnAdapter = FuncBtnAdapter(this,funcBtnList)
        gridView.adapter = funcBtnAdapter
    }

    private fun initAlbum() {
        repeat(16){
            albumList.add(Album("Empty Album", R.drawable.ic_baseline_photo_24))
            //todo 改成取数据后再加
        }
    }

    private  fun initFuncBtn() {
        funcBtnList.add(FuncBtn(R.drawable.ic_members,"members"))
        funcBtnList.add(FuncBtn(R.drawable.ic_add_album,"add album"))
        funcBtnList.add(FuncBtn(R.drawable.ic_event_diary,"diary"))
    }
    //todo 给事件内容页面加点击事件
}