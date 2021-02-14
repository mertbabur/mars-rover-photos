package com.example.nasa.Utils

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity

object ToolbarSet {

    /**
     * Define toolbar.
     */
    fun defineToolbar(activity: FragmentActivity?, toolbar:Toolbar){

        (activity as AppCompatActivity).setSupportActionBar(toolbar)
    }

}