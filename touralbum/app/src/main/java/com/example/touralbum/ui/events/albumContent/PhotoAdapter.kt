package com.example.touralbum.ui.events.albumContent

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.touralbum.R
import com.example.touralbum.ui.events.photoViewer.PhotoViewer

class PhotoAdapter(var photoList:List<Photo>, val eventName:String, val albumName:String) :
    RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val photoImage: ImageView = view.findViewById(R.id.photoImage)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.photo_item,null)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val intent = Intent(parent.context, PhotoViewer::class.java)
            intent.putExtra("eventName",eventName)
            intent.putExtra("albumName",albumName)
            intent.putExtra("position",position)
            parent.context.startActivity(intent)
        }
        return viewHolder
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = photoList[position]
        holder.photoImage.setImageBitmap(photo.image)
    }
    override fun getItemCount() = photoList.size
}