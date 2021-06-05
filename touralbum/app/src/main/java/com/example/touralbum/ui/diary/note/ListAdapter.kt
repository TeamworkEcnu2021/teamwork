package com.example.touralbum.ui.diary.note

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.touralbum.R
import com.example.touralbum.ui.diary.CreateDairy
import com.example.touralbum.ui.diary.DiaryFragment
import com.example.touralbum.ui.events.Event
import com.example.touralbum.ui.events.eventContent.EventContent

class ListAdapter(val noteList: ArrayList<NoteInfo>) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemNoteTitle: TextView = view.findViewById(R.id.item_note_title)
        val itemNoteDate: TextView = view.findViewById(R.id.item_note_date)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_layout,null)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val noteInfo = noteList[position]
            val intent = Intent()
            val bundle = Bundle()
            bundle.putSerializable("noteInfo", noteInfo)
            intent.putExtras(bundle)
            intent.setClass(parent.context, CreateDairy::class.java)
            parent.context.startActivity(intent)
        }
        viewHolder.itemView.setOnLongClickListener {
            val position = viewHolder.adapterPosition
            val noteInfo = noteList[position]
            val title = "警告"
            AlertDialog.Builder(parent.context)
                .setTitle(title)
                .setMessage("确定要删除吗?")
                .setPositiveButton(R.string.btn_confirm) { dialog: DialogInterface?, which: Int ->
                    Note.deleteNote(DiaryFragment.dbHelper, noteInfo.id!!.toInt())
                    noteList.removeAt(position)
                    notifyDataSetChanged()
                    Toast.makeText(parent.context, "删除成功！", Toast.LENGTH_LONG).show()
                }
                .setNegativeButton(R.string.btn_cancel) { dialog: DialogInterface?, which: Int -> }
                .create().show()
            true
        }
        return viewHolder
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = noteList[position]
        holder.itemNoteTitle.text = entry.title
        holder.itemNoteDate.text = entry.date
    }
    override fun getItemCount() = noteList.size
}