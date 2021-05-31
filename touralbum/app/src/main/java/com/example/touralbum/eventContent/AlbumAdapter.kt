package com.example.touralbum.eventContent

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.touralbum.R
import com.example.touralbum.albumContent.AlbumContent


class AlbumAdapter(val albumList: List<Album>,val eventName : String) :
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