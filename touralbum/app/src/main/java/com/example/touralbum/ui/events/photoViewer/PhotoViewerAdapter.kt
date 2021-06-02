package com.example.touralbum.ui.events.photoViewer

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.touralbum.R
import com.example.touralbum.ui.events.albumContent.Photo

class PhotoViewerAdapter(private val photoList: List<Photo>) :
    RecyclerView.Adapter<PhotoViewerAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val photoImage: ImageView = view.findViewById(R.id.photoImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.photo_item, null)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = photoList[position]
        holder.photoImage.setImageBitmap(photo.image)
    }
    override fun getItemCount() = photoList.size
}