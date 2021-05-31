package com.example.touralbum.ui.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.touralbum.R;

public class CreateDairy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dairy);
        Intent intent = new Intent(this, DairyList.class);
        //获取回退按钮
        Button bt_back = findViewById(R.id.back_to_dairy);
        //设置监听并跳转到上个页面
        bt_back.setOnClickListener(v -> startActivity(intent));
        //获取完成按钮
        Button bt_done = findViewById(R.id.dairy_done);
        //设置监听并跳转到完成界面
        bt_done.setOnClickListener(v -> startActivity(intent));
    }
}