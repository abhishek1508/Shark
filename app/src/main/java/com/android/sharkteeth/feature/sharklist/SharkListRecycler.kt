package com.android.sharkteeth.feature.sharklist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.sharkteeth.R
import com.android.sharkteeth.extension.android.view.loadImage
import com.android.sharkteeth.feature.api.entity.Photo
import kotlinx.android.synthetic.main.layout_item_photo.view.*

class SharkListRecycler(private val context: Context):
        RecyclerView.Adapter<SharkListRecycler.SharkListViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var photoList: MutableList<Photo> = mutableListOf()
    private lateinit var callback: IPhotoOnClickListener

    fun setCallback(photoListener: IPhotoOnClickListener) {
        this.callback = photoListener
    }

    fun addAllPhotos(list: MutableList<Photo>) {
        this.photoList.addAll(list)
        notifyItemRangeInserted(0, list.size)
    }

    fun removeAllPhotos() {
        val size = this.photoList.size
        if (size > 0) {
            this.photoList.clear()
            notifyItemRangeRemoved(0, size)
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SharkListViewHolder {
        val view = inflater.inflate(R.layout.layout_item_photo, p0, false)
        return SharkListViewHolder(view)
    }

    override fun onBindViewHolder(p0: SharkListViewHolder, p1: Int) {
        val photo = photoList[p1]
        p0.bindPhoto(photo)
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    inner class SharkListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindPhoto(photo: Photo) {
            itemView.sharkImage.loadImage(context.resources.getString(R.string.image_url,
                                                                      photo.farm,
                                                                      photo.server,
                                                                      photo.id,
                                                                      photo.secret))
        }
    }

    interface IPhotoOnClickListener {
        fun onPhotoClicked(position: Int)
    }
}