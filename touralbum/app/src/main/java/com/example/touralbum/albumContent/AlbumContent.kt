package com.example.touralbum.albumContent

import android.app.Activity
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
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.touralbum.Event
import com.example.touralbum.R
import com.example.touralbum.eventContent.Album
import java.io.ByteArrayOutputStream

class AlbumContent : AppCompatActivity() {

    private lateinit var album : Album
    private lateinit var event : Event

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_content)

        //取event和album数据
        val eventName = intent.getStringExtra("eventName")
        val albumName = intent.getStringExtra("albumName")
        event.getData(this,eventName!!)
        album.getData(this, eventName,albumName!!)

        //标题栏
        supportActionBar?.hide()
        val toolbar : Toolbar = findViewById(R.id.toolbar_event_content)
        toolbar.title = albumName
        toolbar.setNavigationOnClickListener {
            finish()
        }

        //相片列表
        val layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        val recyclerView :RecyclerView = findViewById(R.id.photoList)
        recyclerView.layoutManager = layoutManager
        val adapter = PhotoAdapter(album.photoList,eventName,albumName)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.album_content_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_photo -> {
                // 打开文件选择器
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                // 指定只显示图片
                intent.type = "image/*"
                startActivityForResult(intent,1)
            }
            R.id.delete_album -> {
                //todo delete album
            }
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    data.data?.let { uri ->
                        // 将选择的图片显示
                        val bitmap = getBitmapFromUri(uri)
                        if(bitmap == null){
                            Toast.makeText(this, "添加失败", Toast.LENGTH_SHORT).show()
                        }else {
                            //第一步:将Bitmap压缩至字节数组输出流ByteArrayOutputStream
                            val byteArrayOutputStream = ByteArrayOutputStream()
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                            //第二步:利用Base64将字节数组输出流中的数据转换成字符串String
                            val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
                            val imageString: String = Base64.encodeToString(byteArray, Base64.DEFAULT)
                            //第三步:将String保持至SharedPreferences
                            val sharedPreferences = getSharedPreferences("", MODE_PRIVATE)
                            val editor: SharedPreferences.Editor = sharedPreferences.edit()
                            editor.putString("image", imageString)
                            editor.apply()
                            //imageView.setImageBitmap(bitmap)
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