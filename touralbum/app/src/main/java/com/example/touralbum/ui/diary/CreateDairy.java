package com.example.touralbum.ui.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.touralbum.R;
import com.example.touralbum.ui.diary.note.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateDairy extends AppCompatActivity {

    private Button bt_done;
    private Button bt_back;
    private TextView tv_now;
    private EditText et_title;
    private EditText et_content;
    //记录当前编辑的笔记对象，用于对比是否修改
    private NoteInfo currentNote;
    //记录是否是插入（更新/编辑）状态
    private boolean insertFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dairy);
        initView();
        setListener();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //点击ListView里的一个items时跳转
        if(bundle != null){
            currentNote = (NoteInfo) bundle.getSerializable("noteInfo");
            et_title.setText(currentNote.getTitle());
            et_content.setText(currentNote.getContent());
            insertFlag = false;
        }



    }
    //初始化视图
    private void initView(){
        //获取完成按钮
        bt_done = findViewById(R.id.dairy_done);
        //获取回退按钮
        bt_back = findViewById(R.id.back_to_dairy);
        //获取文本框
        tv_now = findViewById(R.id.tv_now);
        et_content = findViewById(R.id.edit_content);
        et_title = findViewById(R.id.title_input);

        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        tv_now.setText(sdf.format(date));
    }

    //设置监听器
    private void setListener(){
        //设置监听并跳转到上个页面
        bt_back.setOnClickListener(v -> onBackPressed());
        //设置监听并跳转到完成界面
        bt_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_title.getText().toString().equals("")||
                        et_content.getText().toString().equals("")){
                    Toast.makeText(CreateDairy.this, "保存失败！标题或内容不可为空", Toast.LENGTH_SHORT).show();
                }else{
                    saveNote();
                    Intent intent = new Intent(CreateDairy.this, DairyList.class);
                    startActivity(intent);
                }
            }
        });
    }
    //保存笔记到数据库，判断是新建还是更新
    private void saveNote(){
        NoteDataBaseHelper dbHepler = DairyList.getDbHelper();

        ContentValues values = new ContentValues();
        values.put(Note.title, et_title.getText().toString());
        values.put(Note.content, et_content.getText().toString());
        values.put(Note.time,tv_now.getText().toString());
        if(insertFlag){
            Note.insertNote(dbHepler, values);
        }else{
            Note.updateNote(dbHepler, Integer.parseInt(currentNote.getId()),values);
        }
    }
    //overrider onbackpress函数，修改内容时提示保存，否则不保存直接返回

    @Override
    public void onBackPressed() {
        boolean display = false;
        if(insertFlag){
            if(!et_title.getText().toString().equals("")&&
                    !et_content.getText().toString().equals("")){
                display = true;
            }
        }else{
            if(!et_title.getText().toString().equals(currentNote.getTitle())||
                    !et_content.getText().toString().equals(currentNote.getContent())){
                display = true;
            }
        }
        if(display){
            String title = "警告";
            new AlertDialog.Builder(CreateDairy.this)
                    .setTitle(title)
                    .setMessage("是否保存当前内容?")
                    .setPositiveButton(R.string.btn_confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            saveNote();
                            Toast.makeText(CreateDairy.this,R.string.save_succ,Toast.LENGTH_LONG).show();
                            //更新当前Note对象的值 防止选择保存后按返回仍显示此警告对话框
                            currentNote.setTitle(et_title.getText().toString());
                            currentNote.setContent(et_content.getText().toString());
                        }
                    })
                    .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(CreateDairy.this, DairyList.class);
                            startActivity(intent);
                        }
                    }).create().show();
        }else{
            Intent intent = new Intent(CreateDairy.this, DairyList.class);
            startActivity(intent);
        }
    }
}