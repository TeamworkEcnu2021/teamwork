package com.example.touralbum.ui.events;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.touralbum.R;

public class CreateEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        Intent intent = new Intent(this, EventList.class);
        //获取回退按钮
        ImageButton bt_back = findViewById(R.id.bt_back);
        //设置监听并跳转到上个页面
        bt_back.setOnClickListener(v -> startActivity(intent));


        //获取完成按钮
        ImageButton create_btn = findViewById(R.id.create_btn);
        //设置监听并跳转到完成界面
        create_btn.setOnClickListener(v -> startActivity(intent));
    }
}
