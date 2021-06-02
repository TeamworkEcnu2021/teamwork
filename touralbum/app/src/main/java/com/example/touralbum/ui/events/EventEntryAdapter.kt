package com.example.touralbum.ui.events

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.touralbum.R
import com.example.touralbum.ui.events.eventContent.EventContent

class EventEntryAdapter(val eventEntryList: List<Event>) :
    RecyclerView.Adapter<EventEntryAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eventTitle: TextView = view.findViewById(R.id.event_entry_title)
        val eventDate: TextView = view.findViewById(R.id.event_entry_date)
        val eventMember: TextView = view.findViewById(R.id.event_entry_member)
        val eventDest: TextView = view.findViewById(R.id.event_entry_dest)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.event_entry_item,null)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val entry = eventEntryList[position]
            val intent = Intent(parent.context, EventContent::class.java)
            intent.putExtra("eventName",entry.title)
            parent.context.startActivity(intent)
        }
        return viewHolder
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = eventEntryList[position]
        holder.eventTitle.text = entry.title
        holder.eventDate.text = entry.date
        holder.eventMember.text = entry.member
        holder.eventDest.text = entry.dest
    }
    override fun getItemCount() = eventEntryList.size
}