package com.example.touralbum.ui.events.albumContent

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.touralbum.R
import com.example.touralbum.ui.events.Event
import com.example.touralbum.ui.events.eventContent.Album
import java.io.ByteArrayOutputStream
import java.io.File

class AlbumContent : AppCompatActivity() {

    private lateinit var album : Album
    val event = Event("","","","")
    private lateinit var adapter: PhotoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_content)
        Log.d("d","相册内容")


        //取event和album数据
        val drawable = ContextCompat.getDrawable(this, R.drawable.ic_baseline_photo_24)
        val eventName = intent.getStringExtra("eventName")
        val albumName = intent.getStringExtra("albumName")
        album = Album(albumName!!,drawableToBitmap(drawable!!))
        event.getData(this,eventName!!)
        album.getData(this, eventName, albumName)
        Log.d("d","取event和album数据")

        //标题栏
        supportActionBar?.hide()
        val toolbar : Toolbar = findViewById(R.id.toolbar_album_content)
        toolbar.title = albumName
        toolbar.setNavigationOnClickListener {
            finish()
        }
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.add_photo -> {
                    Log.d("d","打开文件选择器选择要加入的相片")
                    // 打开文件选择器
                    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                    intent.addCategory(Intent.CATEGORY_OPENABLE)
                    // 指定只显示图片
                    intent.type = "image/*"
                    startActivityForResult(intent,1)
                }
                R.id.delete_album -> {
                    Log.d("d","删除相册")
                    val file = File("/data/data/${packageName}/shared_prefs/${event.title}_${album.albumName}.xml")
                    Log.d("d","文件对象：${file}")
                    if (file.exists()) {
                        file.delete()
                        Log.d("d","获取到文件路径，删除相册文件")
                    }else Log.d("d","获取相册文件失败")
                    val prefs = getSharedPreferences("event_${album.albumName}",Context.MODE_PRIVATE)
                    val editor = prefs.edit()
                    val count = prefs.getInt("albumCount",0)
                    if(count != 0)editor.putInt("albumCount",count-1)
                    var i = 1
                    while(i <= count){
                        val title = prefs.getString("$i","")
                        if(title == album.albumName){
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
            }
            true
        }

        //相片列表
        val layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        val recyclerView :RecyclerView = findViewById(R.id.photoList)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemDecorationCount
        adapter = PhotoAdapter(album.photoList,eventName,albumName)
        recyclerView.adapter = adapter
        Log.d("d","设置数据适配器，加载相片列表")

        //设置选项菜单

    }

    override fun onResume() {
        album.photoList.clear()
        val name = album.albumName
        album.getData(this,event.title,name)
        adapter.notifyDataSetChanged()
        super.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    data.data?.let { uri ->
                        // 将选择的图片显示
                        Log.d("d","选好图片")
                        val bitmap = getBitmapFromUri(uri)
                        if(bitmap == null){
                            Toast.makeText(this, "添加失败", Toast.LENGTH_SHORT).show()
                        }else {
                            //第一步:将Bitmap压缩至字节数组输出流ByteArrayOutputStream
                            val byteArrayOutputStream = ByteArrayOutputStream()
                            bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream)
                            //第二步:利用Base64将字节数组输出流中的数据转换成字符串String
                            val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
                            val imageString: String = Base64.encodeToString(byteArray, Base64.DEFAULT)
                            //第三步:将String保持至SharedPreferences
                            val sharedPreferences = getSharedPreferences("${event.title}_${album.albumName}", MODE_PRIVATE)
                            val editor: SharedPreferences.Editor = sharedPreferences.edit()
                            editor.putString("${album.photoList.size+1}", imageString)
                            val count = sharedPreferences.getInt("photoCount",0)
                            editor.putInt("photoCount",count+1)
                            editor.apply()
                            album.photoList.add(Photo(bitmap))
                            adapter.notifyDataSetChanged()
                            Log.d("d","将图片转换成字符串存在sp中")
                        }
                    }
                }
            }
        }
    }

    private fun getBitmapFromUri(uri: Uri) = contentResolver
        .openFileDescriptor(uri, "r")?.use {
            BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
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