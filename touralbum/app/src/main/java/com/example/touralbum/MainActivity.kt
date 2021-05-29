package com.example.touralbum

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.touralbum.eventContent.EventContent
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_events,R.id.navigation_discovery,R.id.navigation_add,R.id.navigation_chat,R.id.navigation_account))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        val addevent : FloatingActionButton = findViewById(R.id.add_event)
        addevent.setOnClickListener {
            //todo 跳转到create event活动
            val intent = Intent(this, EventContent::class.java)
            Toast.makeText(this, "创建出行事件", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }
    }
}