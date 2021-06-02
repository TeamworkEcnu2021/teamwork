package com.example.touralbum.ui.events;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.example.touralbum.R;


public class EventContent extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_content);
        Button back_btn = findViewById(R.id.back_to_list);
        back_btn.setOnClickListener(v->{
            Intent intent = new Intent(this, EventList.class);
            startActivity(intent);
        });
    }
}