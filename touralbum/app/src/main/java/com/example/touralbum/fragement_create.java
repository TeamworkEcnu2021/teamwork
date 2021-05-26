package com.example.touralbum;

import androidx.appcompat.app.AppCompatActivity;

//import android.annotation.SuppressLint;
import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.view.View;
//import android.view.WindowInsets;
//import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import static android.text.InputType.TYPE_NULL;

public class fragement_create extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton add_button = findViewById(R.id.adding);
        add_button.setOnClickListener(v -> {
            //jump to friendlist
            Toast.makeText(fragement_create.this, "跳转到好友列表", Toast.LENGTH_SHORT).show();
        });
        RadioGroup visibility = findViewById(R.id.visible);
        visibility.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton r = findViewById(checkedId);
            //r.getText();//获取被选中的单选按钮的值
            Toast.makeText(fragement_create.this, r.getText() , Toast.LENGTH_SHORT).show();
        });
        ImageButton create_btn = findViewById(R.id.create_btn);
        create_btn.setOnClickListener(v -> {
            //jump to sharedEvent
            Toast.makeText(fragement_create.this,"创建成功",Toast.LENGTH_LONG).show();
            //Toast.makeText(MainActivity.this,visibility.getText() , Toast.LENGTH_SHORT).show();
        });

        EditText event_name = findViewById(R.id.trip_title);
        event_name.setOnEditorActionListener((v, actionId, event) -> {
            String tripTitle = event_name.getText().toString();
            //save tripTitle
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(event_name.getWindowToken(),0);
            Toast.makeText(fragement_create.this, tripTitle, Toast.LENGTH_SHORT).show();
            return false;
        });
        EditText time = findViewById(R.id.trip_date);
        time.setOnEditorActionListener((v, actionId, event) -> {
            String tripDate = time.getText().toString();
            //save tripDate
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(time.getWindowToken(),0);
            Toast.makeText(fragement_create.this, tripDate, Toast.LENGTH_SHORT).show();
            return false;
        });

        EditText location = findViewById(R.id.location);
        location.setOnEditorActionListener((v, actionId, event) -> {
            String loca = location.getText().toString();
            //save loca
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(location.getWindowToken(), 0);
            location.setInputType(TYPE_NULL);
            Toast.makeText(fragement_create.this, loca, Toast.LENGTH_SHORT).show();
            return false;
        });


        ImageButton back_ward = findViewById(R.id.bt_back);
        back_ward.setOnClickListener(v-> finish());
    }
}