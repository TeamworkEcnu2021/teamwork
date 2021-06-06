package com.example.touralbum.ui.events

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.example.touralbum.R

class CreateEvent : AppCompatActivity() {

    private lateinit var mLocationClient: LocationClient
    private val myListener = MyLocationListener()
    private lateinit var positionText: EditText
    private lateinit var btn_loc: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)
        Log.d("d","创建事件活动")

        //标题栏
        supportActionBar?.hide()

        //在使用SDK各组件之前先初始化context信息，传入ApplicationContext
        //注意该方法要在setContextView之前实现
        mLocationClient = LocationClient(applicationContext)
        mLocationClient.registerLocationListener(myListener)
        positionText = findViewById(R.id.location)
        btn_loc = findViewById(R.id.get_loc)
        //如果没有启动权限，就询问用户打开
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        } else {
            requestLocation()
        }
        positionText.setOnEditorActionListener { v, actionId, event ->
            val triploc = v.text.toString()
            Toast.makeText(this, triploc, Toast.LENGTH_SHORT).show()
            false
        }

        //返回键
        val backWard = findViewById<ImageButton>(R.id.bt_back)
        backWard.setOnClickListener { v: View? -> finish() }
        Log.d("d","设置返回键")

        //创建事件
        val createBtn = findViewById<ImageButton>(R.id.create_btn)
        createBtn.setOnClickListener { v: View? ->
            val eventMember = findViewById<EditText>(R.id.trip_member)
            val eventName = findViewById<EditText>(R.id.trip_title)
            val time = findViewById<EditText>(R.id.trip_date)
            val location = findViewById<EditText>(R.id.location)
            val title = eventName.text.toString()
            val date = time.text.toString()
            val member = eventMember.text.toString()
            val dest = location.text.toString()
            val newEvent = Event(title,date,member,dest)
            saveAndCreate(newEvent)
            val intent = Intent()
            intent.putExtra("newEventTitle", newEvent.title)
            setResult(RESULT_OK, intent)
            Log.d("d","设置intent中的返回信息事件名并finish")
            finish()
        }
    }

    //创建并保存事件
    private fun saveAndCreate(event : Event){
        var editor = getSharedPreferences("event_${event.title}", Context.MODE_PRIVATE).edit()
        editor.putString("title", event.title)
        editor.putString("date",event.date)
        editor.putString("member",event.member)
        editor.putString("dest",event.dest)
        editor.putInt("albumCount",event.albumList.size)
        editor.apply()
        val prefs = getSharedPreferences("events",Context.MODE_PRIVATE)
        editor = prefs.edit()
        val count = prefs.getInt("eventCount",0)
        editor.putInt("eventCount",count+1)
        editor.putString("${count+1}",event.title)
        editor.apply()
        Log.d("d","把事件数据保存在名为event_事件名的sp中，将events中数量修改，并添加到最后")
    }

    private fun requestLocation() {
        initLocation()
        mLocationClient.start()
    }

    private fun initLocation() {
        val option = LocationClientOption()
        option.setCoorType("bd09ll")
        option.locationMode = LocationClientOption.LocationMode.Device_Sensors
        option.setScanSpan(5000)
        option.isOpenGps = true
        option.isLocationNotify = true
        option.setIsNeedAddress(true)
        option.setNeedNewVersionRgc(true)
        mLocationClient.locOption = option
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    finish()
                    return
                }
            }
            requestLocation()
        } else {
            finish()
        }
    }

    inner class MyLocationListener : BDAbstractLocationListener() {
        override fun onReceiveLocation(bdLocation: BDLocation) {
            runOnUiThread {
                btn_loc.setOnClickListener { positionText.setText(bdLocation.addrStr) }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mLocationClient.stop()
    }
}