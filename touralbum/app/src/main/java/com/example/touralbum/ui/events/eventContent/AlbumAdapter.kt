package com.example.touralbum.ui.events.eventContent

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.touralbum.R
import com.example.touralbum.ui.events.albumContent.AlbumContent


class AlbumAdapter(val albumList: List<Album>, val eventName : String) :
        RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val albumImage: ImageView = view.findViewById(R.id.albumImage)
        val albumName: TextView = view.findViewById(R.id.albumName)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_album,null)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val album = albumList[position]
            val intent = Intent(parent.context, AlbumContent::class.java)
            intent.putExtra("albumName",album.albumName)
            intent.putExtra("eventName",eventName)
            parent.context.startActivity(intent)
        }
        return viewHolder
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = albumList[position]
        holder.albumImage.setImageBitmap(album.surfaceImage)
        holder.albumName.text = album.albumName
    }
    override fun getItemCount() = albumList.size
}