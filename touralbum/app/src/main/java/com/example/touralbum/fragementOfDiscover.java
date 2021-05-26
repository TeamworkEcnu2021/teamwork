package com.example.touralbum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class fragementOfDiscover extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragement_of_discover);

        ImageButton ibutn = findViewById(R.id.create_btn2);
        ibutn.setOnClickListener(v -> {
            Intent intent = new Intent(fragementOfDiscover.this, MainActivity.class);
            startActivity(intent);
        });
    }
}