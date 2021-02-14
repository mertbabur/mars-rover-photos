package com.example.nasa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.vo.Photo
import com.example.nasa.Utils.ImageViewSet
import com.example.nasason.nasa.Utils.PopupWin
import kotlinx.android.synthetic.main.adapter_for_photos.view.*

class PhotoAdapter(val activity: FragmentActivity?, val photoList: ArrayList<Photo?>) : RecyclerView.Adapter<PhotoAdapter.ModelViewHolder>() {

    class ModelViewHolder(view:View) : RecyclerView.ViewHolder(view) {

        val imageView = itemView.imageViewForPhotoListAdapter

        /**
         * Bind items.
         */
        fun bindItems(activity: FragmentActivity?, item: Photo) {
            val url = item.imgSrc.trim()
            ImageViewSet.setPhotoToImageView(activity,imageView,url)
            clickImage(activity, item)
        }

        /**
         * If click image, dismiss popup.
         */
        fun clickImage(activity: FragmentActivity?, item: Photo){
            imageView.setOnClickListener {
                PopupWin.setRoverAndPhotoInfoToPopupWindow(activity, imageView, item)
            }
        }
    }

    /**
     * Define adapter layout.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_for_photos, parent, false)
        return ModelViewHolder(itemView)
    }

    /**
     * Return photoList size.
     */
    override fun getItemCount(): Int {
        if (photoList != null) {
            return photoList.size
        }
        return 0
    }

    /**
     * Bind items.
     */
    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        if (photoList != null) {
            photoList.get(position)?.let { holder.bindItems(activity, it) }
        }
    }
}