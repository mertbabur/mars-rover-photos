package com.example.nasa.Utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.nasa.R

object NewFragment {

    /**
     * Open new fragment which has bundle.
     */
    fun replaceFragmet(activity: FragmentActivity?, fragment: Fragment, bundleKey: String, bundleString: String){
        if (fragment != null && activity != null){
            val bundle = Bundle()
            bundle.putString(bundleKey, bundleString)
            fragment.arguments = bundle
            activity.supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).addToBackStack(null).commit()
        }
    }

}