package com.example.touralbum

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.touralbum.ui.events.eventContent.EventContent
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.touralbum.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("d","主活动")
        supportActionBar?.hide()
        // 隐藏标题栏
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        //window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //导航栏
        val navView: BottomNavigationView = binding.navView

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        //把BottomNavigationView与NavController绑定。
        //binding.navView.setupWithNavController(navController)

        //val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_events,R.id.navigation_diary,R.id.navigation_weather))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        Log.d("d","设置导航栏")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            0 -> if (resultCode == RESULT_OK) {
                val newTitle = data?.getStringExtra("newEventTitle")
                val intent = Intent(this, EventContent::class.java)
                intent.putExtra("eventName",newTitle)
                Log.d("d","根据intent中返回的事件名进入对应事件内容活动")
                startActivity(intent)
            }//创建了新事件则根据事件名称进入事件内容界面
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onBackPressed() {
    }
}