package com.example.touralbum.ui.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.touralbum.R;

public class DairyContent extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dairy_content);
        Button back_btn = findViewById(R.id.back_to_list);
        back_btn.setOnClickListener(v->{
            Intent intent = new Intent(this, DairyList.class);
            startActivity(intent);
        });
    }
}