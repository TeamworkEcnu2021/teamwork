package com.example.touralbum.ui.diary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.touralbum.ui.diary.note.*;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.example.touralbum.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DairyList extends Fragment {

    private ListView noteListView;
    private Button fab;
    private List<NoteInfo> noteList = new ArrayList<>();
    private ListAdpter mListAdapter;
    private static NoteDataBaseHelper dbHelper;
    View view = View.inflate(getActivity(),R.layout.fragment_dairy_list,null);

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new NoteDataBaseHelper(getActivity(),"MyNote.db", null, 1);
        initView();
        setListener();
        Intent intent = getActivity().getIntent();
        if(intent != null){
            getNoteList();
            mListAdapter.refreshDataSet();
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    //初始化视图
    private void initView(){
        noteListView = view.findViewById(R.id.note_list);
        fab = view.findViewById(R.id.fab);
        //获取noteList
        getNoteList();
        mListAdapter = new ListAdpter(getActivity(), noteList);
        noteListView.setAdapter(mListAdapter);
    }
    //设置监听器
    private void setListener(){
        //设置监听并跳转到创建日志页面
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CreateDairy.class);
            startActivity(intent);
        });
        noteListView.setOnItemClickListener((parent, view, position, id) -> {
            NoteInfo noteInfo = noteList.get(position);
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("noteInfo", noteInfo);
            intent.putExtras(bundle);
            intent.setClass(getActivity(), CreateDairy.class);
            startActivity(intent);
        });
        noteListView.setOnItemLongClickListener((parent, view, position, id) -> {
            final NoteInfo noteInfo = noteList.get(position);
            String title = "警告";
            new AlertDialog.Builder(getActivity())
                    .setTitle(title)
                    .setMessage("确定要删除吗?")
                    .setPositiveButton(R.string.btn_confirm, (dialog, which) -> {
                        Note.deleteNote(dbHelper, Integer.parseInt(noteInfo.getId()));
                        noteList.remove(position);
                        mListAdapter.refreshDataSet();
                        Toast.makeText(getActivity(), "删除成功！", Toast.LENGTH_LONG).show();
                    })
                    .setNegativeButton(R.string.btn_cancel, (dialog, which) -> {
                    }).create().show();
            return true;
        });
    }
    //从数据库中读取所有笔记，封装成List<NoteInfo>
    private void getNoteList(){
        noteList.clear();
        Cursor allNotes = Note.getAllNotes(dbHelper);
        for (allNotes.moveToFirst(); !allNotes.isAfterLast(); allNotes.moveToNext()){
           NoteInfo noteInfo = new NoteInfo();
            noteInfo.setId(allNotes.getString(allNotes.getColumnIndex(Note._id)));
            noteInfo.setTitle(allNotes.getString(allNotes.getColumnIndex(Note.title)));
            noteInfo.setContent(allNotes.getString(allNotes.getColumnIndex(Note.content)));
            noteInfo.setDate(allNotes.getString(allNotes.getColumnIndex(Note.time)));
            noteList.add(noteInfo);
        }
    }
    //重写返回按钮处理事件
    /*
    @Override
    public void onBackPressed() {
        String title = "提示";
        new AlertDialog.Builder(DairyList.this)
                .setTitle(title)
                .setMessage("确定要退出吗?")
                .setPositiveButton(R.string.btn_confirm, (dialog, which) -> finish())
                .setNegativeButton(R.string.btn_cancel, (dialog, which) -> {
                }).create().show();
    }*/
    //给其他类提供dbHelper
    public static NoteDataBaseHelper getDbHelper(){
        return dbHelper;
    }
}
