package com.example.nasa.Utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

object ImageViewSet {

    /**
     * Set rover's photo to imageView with glide.
     */
    fun setPhotoToImageView(mContext: Context?, imageView: ImageView, imageUrl: String){
        Glide.with(mContext)
            .load(imageUrl)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(imageView)
    }

}