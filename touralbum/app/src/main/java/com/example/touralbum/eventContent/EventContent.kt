package com.example.touralbum.eventContent


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Base64
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.touralbum.Event
import com.example.touralbum.R
import java.io.ByteArrayInputStream


class EventContent : AppCompatActivity() {

    private val albumList = ArrayList<Album>()
    private val funcBtnList = ArrayList<FuncBtn>()
    private lateinit var event : Event

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_content)

        //取事件数据
        val eventTitle = intent.getStringExtra("eventTitle")
        event.getData(this,eventTitle!!)

        //标题栏
        supportActionBar?.hide()
        val toolbar : Toolbar = findViewById(R.id.toolbar_event_content)
        toolbar.title = event.title
        toolbar.setNavigationOnClickListener {
            finish()
        }

        //相册列表
        initAlbum(event)
        val layoutManager = GridLayoutManager(this,2)
        val recyclerView : RecyclerView = findViewById(R.id.albumList)
        recyclerView.layoutManager = layoutManager
        val albumAdapter = AlbumAdapter(albumList,event.title)
        recyclerView.adapter = albumAdapter

        //按钮列表
        initFuncBtn()
        val gridView :GridView = findViewById(R.id.option_button)
        val funcBtnAdapter = FuncBtnAdapter(this,funcBtnList)
        gridView.adapter = funcBtnAdapter
        gridView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id -> when(position){
                0 -> {Toast.makeText(this, "event info", Toast.LENGTH_SHORT).show()}
                1 -> {
                    val drawable = ContextCompat.getDrawable(this, R.drawable.ic_baseline_photo_24)
                    event.albumList.add(Album("new Album",drawableToBitmap(drawable!!)))
                    Toast.makeText(this, "创建成功", Toast.LENGTH_SHORT).show()
                }
                2 -> {Toast.makeText(this, "diary", Toast.LENGTH_SHORT).show()}
                3 -> {Toast.makeText(this, "delete event", Toast.LENGTH_SHORT).show()}
            } }//todo 改为弹出相应对话框和跳转
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

    private fun initAlbum(event : Event) {
        for( album in event.albumList){
            if(album.photoList.isEmpty()){
                val drawable = ContextCompat.getDrawable(this, R.drawable.ic_baseline_photo_24)
                val bitmap = drawableToBitmap(drawable!!)
                albumList.add((Album(album.albumName,bitmap)))
            }else {
                val prefs = getSharedPreferences("${event.title}_${album.albumName}", Context.MODE_PRIVATE)
                //第一步:取出字符串形式的Bitmap
                val imageString = prefs.getString("1", "")
                //第二步:利用Base64将字符串转换为ByteArrayInputStream
                val byteArray: ByteArray = Base64.decode(imageString, Base64.DEFAULT)
                val byteArrayInputStream = ByteArrayInputStream(byteArray)
                //第三步:利用ByteArrayInputStream生成Bitmap
                val bitmap = BitmapFactory.decodeStream(byteArrayInputStream)
                albumList.add(Album(album.albumName, bitmap))
            }
        }
    }

    private fun initFuncBtn() {
        funcBtnList.add(FuncBtn(R.drawable.ic_event_info,"event info"))
        funcBtnList.add(FuncBtn(R.drawable.ic_add_album,"add album"))
        funcBtnList.add(FuncBtn(R.drawable.ic_event_diary,"diary"))
        funcBtnList.add(FuncBtn(R.drawable.ic_delete,"delete event"))
    }
}