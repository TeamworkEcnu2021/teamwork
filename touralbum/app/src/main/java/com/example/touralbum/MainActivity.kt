package com.example.touralbum

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.touralbum.ui.events.CreateEvent
import com.example.touralbum.ui.events.eventContent.EventContent
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //导航栏
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_events,R.id.navigation_diary,R.id.navigation_add,R.id.navigation_chat,R.id.navigation_account))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //创建事件按钮
        val addevent : FloatingActionButton = findViewById(R.id.add_event)
        addevent.setOnClickListener {
            val intent = Intent(this, CreateEvent::class.java)
            startActivityForResult(intent,0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            0 -> if (resultCode == RESULT_OK) {
                val newTitle = data?.getStringExtra("newEventTitle")
                val intent = Intent(this, EventContent::class.java)
                intent.putExtra("eventTitle",newTitle)
                startActivity(intent)
            }//创建了新事件则根据事件名称进入事件内容界面
        }
    }
}