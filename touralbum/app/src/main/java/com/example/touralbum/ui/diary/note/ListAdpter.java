package com.example.touralbum.ui.diary.note;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.touralbum.R;

import java.util.List;

class ViewHolder{
    //public ImageView itemIcon;
    public TextView itemNoteTitle;
    public TextView itemNoteDate;

    View itemView;

    public ViewHolder(View itemView){
        if(itemView == null){
            throw new IllegalArgumentException("Item View cannot be empty!");
        }
        this.itemView = itemView;
        itemNoteDate = itemView.findViewById(R.id.item_note_date);
        itemNoteTitle = itemView.findViewById(R.id.item_note_title);
    }
}

public class ListAdpter extends BaseAdapter {
    private List<NoteInfo> noteList;
    private LayoutInflater layoutInflater;
    private Context context;
    private ViewHolder holder = null;

    public ListAdpter(Context context, List<NoteInfo> noteList){
        this.noteList = noteList;
        this.context = context;

        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public Object getItem(int position) {
        return noteList.get(position).getTitle();
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(noteList.get(position).getId());
    }

    public void remove(int index){
        noteList.remove(index);
    }

    public void refreshDataSet(){
        notifyDataSetChanged();
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.itemNoteTitle.setText(noteList.get(position).getTitle());
        holder.itemNoteDate.setText(noteList.get(position).getDate());
        return convertView;
    }
}