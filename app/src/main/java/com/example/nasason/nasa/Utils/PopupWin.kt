package com.example.nasason.nasa.Utils

import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.PopupWindow
import androidx.fragment.app.FragmentActivity
import com.example.mvvm.vo.Photo
import com.example.nasa.R
import com.example.nasa.Utils.ImageViewSet
import com.example.nasason.nasa.vo.RoverAndPhotoInfoModelForPopup
import kotlinx.android.synthetic.main.popup_rover.view.*

object PopupWin {

    /**
     * Create popup window.
     */
    fun createPopupWindow(activity: FragmentActivity?, imageView: ImageView): View {
        val view = activity!!.layoutInflater.inflate(R.layout.popup_rover, null)
        val popup = PopupWindow(activity)
        popup.contentView = view
        popup.showAtLocation(imageView, Gravity.CENTER,0,0)
        closePopup(view, popup)
        return view
    }

    /**
     * Create popup windows and set info to it's attributes.
     */
    fun setRoverAndPhotoInfoToPopupWindow(activity: FragmentActivity?, imageView: ImageView, item: Photo){
        val view = createPopupWindow(activity, imageView)
        setPopupAttributes(activity, view, item)
    }

    /**
     * Close popup.
     */
    fun closePopup(view: View, popup: PopupWindow){
        view.popupClose.setOnClickListener {
            popup.dismiss()
        }
    }

    /**
     * Get data from item and it set to RoverAndPhotoInfoModelForPopup object.
     */
    fun getDataFromItem(item: Photo): RoverAndPhotoInfoModelForPopup {
        val imageUrl = item.imgSrc.trim()
        val earthDate = item.earthDate
        val roverName = item.rover.name
        val cameraName = item.camera.fullName + " (" + item.camera.name +")"
        val roverStatus = item.rover.status
        val launchDate = item.rover.launchDate
        val landingDate = item.rover.landingDate

        return RoverAndPhotoInfoModelForPopup(imageUrl, earthDate, roverName, cameraName, roverStatus, launchDate, landingDate)
    }

    /**
     * First, get data from item object.
     * Then, set info to popup which has attributes.
     */
    fun setPopupAttributes(activity: FragmentActivity?, view: View, item: Photo){
        val roverAndPhotoInfo = getDataFromItem(item)
        ImageViewSet.setPhotoToImageView(activity,view.popupImage, item.imgSrc)
        view.popupEarthDate.text = "Resim Tarihi : " + roverAndPhotoInfo.earthDate
        view.popupWhichCamera.text = "Kamera : " + roverAndPhotoInfo.cameraName
        view.popupRoverName.text = "Rover Adı : " + roverAndPhotoInfo.roverName
        view.popupStatus.text = "Durum : " + roverAndPhotoInfo.roverStatus
        view.popupLaunchDate.text = "Fırlatılış Tarihi : " + roverAndPhotoInfo.launchDate
        view.popupLandingDate.text = "İniş Tarihi : " + roverAndPhotoInfo.landingDate
    }

}