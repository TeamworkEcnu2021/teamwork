package com.example.touralbum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class fragement_discover extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragement_of_discover);

        ImageButton ibutn = findViewById(R.id.create_btn2);
        ibutn.setOnClickListener(v -> {
            Intent intent = new Intent(fragement_discover.this, fragement_create.class);
            startActivity(intent);
        });
    }
}