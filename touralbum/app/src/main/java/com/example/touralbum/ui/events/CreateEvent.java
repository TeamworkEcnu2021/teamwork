package com.example.touralbum.ui.events;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.touralbum.R;

import org.jetbrains.annotations.NotNull;

public class CreateEvent extends AppCompatActivity {
    public LocationClient mLocationClient = null;
    private final MyLocationListener myListener = new MyLocationListener();
    private EditText ed_loc;
    private StringBuilder currentPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener);
        //获取权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else {
            requestLocation();
        }

        Intent intent = new Intent(this, EventList.class);
        //获取回退按钮
        ImageButton bt_back = findViewById(R.id.bt_back);
        //设置监听并跳转到上个页面
        bt_back.setOnClickListener(v -> startActivity(intent));

        //获取定位按钮
        ImageButton bt_loc = findViewById(R.id.get_loc);
        //获取输入框
        ed_loc = findViewById(R.id.location);
        //为定位按钮设置监听，单击后获取当前位置并填入输入框
        bt_loc.setOnClickListener(v -> ed_loc.setText(currentPosition));
        //获取完成按钮
        ImageButton create_btn = findViewById(R.id.create_btn);
        //设置监听并跳转到完成界面
        create_btn.setOnClickListener(v -> startActivity(intent));
    }

    private void requestLocation(){
        initLocation();
        mLocationClient.start();
    }
    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        option.setScanSpan(5000);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIsNeedAddress(true);
        option.setNeedNewVersionRgc(true);
        mLocationClient.setLocOption(option);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1){
            for(int result : grantResults){
                if(result != PackageManager.PERMISSION_GRANTED){
                    finish();
                    return;
                }
            }
        }
    }

    public class MyLocationListener extends BDAbstractLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            runOnUiThread(() -> {
                currentPosition = new StringBuilder();
                currentPosition.append(bdLocation.getAddrStr());
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }
}
