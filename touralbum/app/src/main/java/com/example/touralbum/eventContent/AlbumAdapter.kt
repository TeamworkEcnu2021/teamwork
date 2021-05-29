package com.example.touralbum.eventContent

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.touralbum.R

class AlbumAdapter(val albumList: List<Album>) :
        RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val albumImage: ImageView = view.findViewById(R.id.albumImage)
        val albumName: TextView = view.findViewById(R.id.albumName)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.album_item,null)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val album = albumList[position]
            Toast.makeText(parent.context, "打开了相册${album.albumName}", Toast.LENGTH_SHORT).show()
            //todo 改为加载相册内容
        }
        return viewHolder
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = albumList[position]
        holder.albumImage.setImageResource(album.imageId)
        holder.albumName.text = album.albumName
    }
    override fun getItemCount() = albumList.size
}