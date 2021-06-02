package com.example.touralbum.ui.reminder.activity_memo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.touralbum.R;
import com.example.touralbum.ui.reminder.base_memo.BaseActivity;
import com.example.touralbum.ui.reminder.entity_memo.Event;
import com.example.touralbum.ui.reminder.manager_memo.EventManager;
import com.example.touralbum.ui.reminder.service_memo.ClockService;
import com.example.touralbum.ui.reminder.util_memo.AlertDialogUtil;

public class ClockActivity extends BaseActivity {
    private static final String TAG = "ClockActivity";
    public static final String EXTRA_CLOCK_EVENT = "clock.event";
    private MediaPlayer mediaPlayer;
    private Vibrator mVibrator;
    private EventManager mEventManger = EventManager.getInstance();
    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void initView() {
    }

    private void clock() {
        mediaPlayer.start();
        long[] pattern = new long[]{1500, 1000};
        mVibrator.vibrate(pattern, 0);
        //获取自定义布局
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_alarm_layout_memo, null);
        TextView textView = inflate.findViewById(R.id.tv_event);
        textView.setText(String.format(getString(R.string.clock_event_msg_template), event.getmTitle()));
        Button btnConfirm = inflate.findViewById(R.id.btn_confirm);
        final AlertDialog alertDialog = AlertDialogUtil.showDialog(this, inflate);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mVibrator.cancel();
                alertDialog.dismiss();
                finish();
            }
        });
        alertDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        clock();
    }

    @Override
    protected void initData() {
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.clock);
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        Intent intent = getIntent();
        event = getIntent().getParcelableExtra(ClockService.EXTRA_EVENT);
        if (event == null) {
            finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        clock();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_clock_memo;
    }
}
