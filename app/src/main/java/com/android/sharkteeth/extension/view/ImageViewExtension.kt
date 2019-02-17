package com.android.sharkteeth.extension.android.view

import android.graphics.drawable.Drawable
import android.support.annotation.Nullable
import android.widget.ImageView
import com.android.sharkteeth.di.GlideApp
import com.android.sharkteeth.di.GlideRequest
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadThumbnail(url: String?): GlideRequest<Drawable> {
    return GlideApp.with(context).load(url)
}

fun ImageView.loadImage(url: String?, @Nullable thumbnail: String?) {
    val reqOptions = RequestOptions().centerCrop()
    GlideApp.with(context)
            .load(url)
            .thumbnail(loadThumbnail(thumbnail))
            .apply(reqOptions)
            .into(this)
}

fun ImageView.loadCircularImage(url: String?, @Nullable thumbnail: String?) {
    val reqOptions = RequestOptions().circleCrop()
    GlideApp.with(context)
            .load(url)
            .thumbnail(loadThumbnail(thumbnail))
            .apply(reqOptions)
            .into(this)
}

