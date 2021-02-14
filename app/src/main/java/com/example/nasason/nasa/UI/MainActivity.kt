package com.example.nasa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.nasa.Fragments.CursiosityFragment
import com.example.nasa.Fragments.OpportunityFragment
import com.example.nasa.Fragments.SpiritFragment
import com.example.nasa.Utils.NewFragment
import com.example.nasason.nasa.Utils.Const
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val cursiosityFragment = CursiosityFragment()
    private val opportunityFragment = OpportunityFragment()
    private val spiritFragment = SpiritFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        clickCursiosity()
        clickOpportunity()
        clickSpirit()
    }

    /**
     * Open curisiosity photos.
     */
    fun clickCursiosity(){
        cardViewCuriosity.setOnClickListener {
            makeInVisibleText()
            NewFragment.replaceFragmet(this, cursiosityFragment, Const.BUNDLE_KEY, Const.ALL_CAMERA)
        }
    }

    /**
     * Open opportunity photos.
     */
    fun clickOpportunity(){
        cardViewOpportunity.setOnClickListener {
            makeInVisibleText()
            NewFragment.replaceFragmet(this, opportunityFragment, Const.BUNDLE_KEY, Const.ALL_CAMERA)
        }
    }

    /**
     * Open spirit photos.
     */
    fun clickSpirit(){
        cardViewSpirit.setOnClickListener {
            makeInVisibleText()
            NewFragment.replaceFragmet(this, spiritFragment, Const.BUNDLE_KEY, Const.ALL_CAMERA)
        }
    }

    /**
     * If touch bottom nav, make invisible textView.
     */
    fun makeInVisibleText(){
        textViewInfo.visibility = View.INVISIBLE
    }
}