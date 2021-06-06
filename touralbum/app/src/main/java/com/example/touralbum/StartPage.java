package com.example.touralbum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class StartPage extends AppCompatActivity {

    final int SPLASH_DISPLAY_LENGHT = 1500;
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        mHandler.postDelayed(() -> {
            Intent intent = new Intent(StartPage.this, MainActivity.class);
            startActivity(intent);
        },SPLASH_DISPLAY_LENGHT);
    }
}