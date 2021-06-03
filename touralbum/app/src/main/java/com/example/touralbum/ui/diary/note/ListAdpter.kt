package com.example.touralbum.ui.diary.note

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.touralbum.R

internal class ViewHolder(itemView: View?) {
    //public ImageView itemIcon;
    var itemNoteTitle: TextView
    var itemNoteDate: TextView
    var itemView: View

    init {
        requireNotNull(itemView) { "Item View cannot be empty!" }
        this.itemView = itemView
        itemNoteDate = itemView.findViewById(R.id.item_note_date)
        itemNoteTitle = itemView.findViewById(R.id.item_note_title)
    }
}

class ListAdpter(private val context: Context?, private val noteList: ArrayList<NoteInfo>) :
    BaseAdapter() {
    private val layoutInflater: LayoutInflater
    override fun getCount(): Int {
        return noteList.size
    }

    override fun getItem(position: Int): Any {
        return noteList[position].title!!
    }

    override fun getItemId(position: Int): Long {
        return noteList[position].id!!.toLong()
    }

    fun remove(index: Int) {
        noteList.removeAt(index)
    }

    fun refreshDataSet() {
        notifyDataSetChanged()
    }

    @SuppressLint("InflateParams")
    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        var convertView = convertView
        var holder: ViewHolder? = null
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_layout, null)
            holder = ViewHolder(convertView)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        holder!!.itemNoteTitle.text = noteList[position].title
        holder.itemNoteDate.text = noteList[position].date
        return convertView
    }

    init {
        layoutInflater = LayoutInflater.from(context)
    }
}