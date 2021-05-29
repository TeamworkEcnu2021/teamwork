package com.example.touralbum.eventContent

import android.os.Bundle
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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
        val toolbar : Toolbar = findViewById(R.id.toolbar_event_content)
        toolbar.title = "我的标题"//todo 改为取数据得到的标题
        toolbar.setNavigationOnClickListener {
            finish()
        }
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
        gridView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id -> when(position){
                0 -> Toast.makeText(this, "members", Toast.LENGTH_SHORT).show()
                1 -> Toast.makeText(this, "add album", Toast.LENGTH_SHORT).show()
                2 -> Toast.makeText(this, "diary", Toast.LENGTH_SHORT).show()
            } }//todo 改为跳转到相应页面
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