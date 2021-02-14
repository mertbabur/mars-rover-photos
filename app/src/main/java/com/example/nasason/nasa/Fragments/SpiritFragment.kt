package com.example.nasa.Fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.vo.NasaPhotos
import com.example.mvvm.vo.Photo
import com.example.nasa.PhotoAdapter
import com.example.nasa.R
import com.example.nasa.Utils.NewFragment
import com.example.nasa.Utils.ToolbarSet
import com.example.nasa.api.NasaClient
import com.example.nasa.api.NasaInterface
import com.example.nasa.repository.NetworkState
import com.example.nasa.single_nasa_details.NasaPhotosRepository
import com.example.nasa.single_nasa_details.SingleNasaPhotosViewModel
import com.example.nasason.nasa.Utils.Const
import com.example.nasason.nasa.single_nasa_details.NasaPhotosWithCamRepository
import com.example.nasason.nasa.single_nasa_details.SingleNasaPhotosWithCamViewModel
import kotlinx.android.synthetic.main.fragment_spirit.view.*

class SpiritFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var scrollView: NestedScrollView

    private lateinit var photosViewModel: SingleNasaPhotosViewModel
    private lateinit var nasaPhotosRepository : NasaPhotosRepository

    private lateinit var photosWithCamViewModel: SingleNasaPhotosWithCamViewModel
    private lateinit var nasaPhotosWithCamRepository : NasaPhotosWithCamRepository

    private lateinit var photoAdapter : PhotoAdapter // it for recyclerview.

    private var photoList : ArrayList<Photo?> = ArrayList() // put photo in api.

    private lateinit var CAMERA: String // put camera name from bundle which came from mainActivity.

    private var page = 0 // put page number for api.

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView : View =  inflater.inflate(R.layout.fragment_spirit, container, false)
        defineAttributes(rootView)
        ToolbarSet.defineToolbar(activity, rootView.toolbarForSpirit)
        setHasOptionsMenu(true)
        getArgumentsForCameraType()

        getPhotoFromApi(Const.SPIRIT, 0)
        loadMorePhotoFromApi(Const.SPIRIT)
        return rootView
    }

    /**
     * Define attributes.
     */
    fun defineAttributes(rootView: View){
        recyclerView = rootView.recyclerViewForSpirit
        progressBar = rootView.progressBarForSpirit
        scrollView = rootView.nestedScrollViewForSpirit
    }

    /**
     * Get camera type from argument.
     */
    fun getArgumentsForCameraType(){
        CAMERA = arguments?.getString(Const.BUNDLE_KEY).toString()
    }

    /**
     * Set special toolbarMenu to Toolbar.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_for_toolbar_spirit,menu)
    }

    /**
     * Choose item which clicked.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val fragment = SpiritFragment()
        return when(item.itemId){
            R.id.action_allForSpirit -> {
                NewFragment.replaceFragmet(activity, fragment, Const.BUNDLE_KEY, Const.ALL_CAMERA)
                true
            }
            R.id.action_fhazForSpirit -> {
                NewFragment.replaceFragmet(activity, fragment, Const.BUNDLE_KEY, Const.FHAZ_CAMERA)
                true
            }
            R.id.action_rhazForSpirit -> {
                NewFragment.replaceFragmet(activity, fragment, Const.BUNDLE_KEY, Const.RHAZ_CAMERA)
                true
            }
            R.id.action_navcamForSpirit -> {
                NewFragment.replaceFragmet(activity, fragment, Const.BUNDLE_KEY, Const.NAVCAM_CAMERA)
                true
            }
            R.id.action_pancamForSpirit -> {
                NewFragment.replaceFragmet(activity, fragment, Const.BUNDLE_KEY, Const.PANCAM_CAMERA)
                true
            }
            R.id.action_minitesForSpirit -> {
                NewFragment.replaceFragmet(activity, fragment, Const.BUNDLE_KEY, Const.MINITES_CAMERA)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Get photo from api.
     * If camera equals to 'all', it means getting all photos.
     * If camera does not equal to 'all' it means getting photos which have specific camera.
     */
    fun getPhotoFromApi(roverName: String, page: Int){
        if (!CAMERA.equals("all"))
            runForPhotoWithCam(roverName, CAMERA,page)
        else
            runForPhoto(roverName,page)
    }

    /**
     * Load more photo from api.
     * If scroll down end of recyclerView, load more data from api.
     */
    fun loadMorePhotoFromApi(roverName: String){
        scrollView.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->

            if (v != null) {
                if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight){
                    page++
                    progressBar.visibility = View.VISIBLE
                    getPhotoFromApi(roverName, page)
                }
            }
        }

    }

    /**
     * Get nasa photos view model. --> single.
     */
    private fun getNasaPhotosViewModel(which:String, page: Int): SingleNasaPhotosViewModel {
        return ViewModelProviders.of(this,object: ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleNasaPhotosViewModel(nasaPhotosRepository, which,page) as T
            }
        })[SingleNasaPhotosViewModel::class.java]
    }

    /**
     * Get all photo.
     */
    fun runForPhoto(roverName: String, page: Int){

        val apiService : NasaInterface = NasaClient.getClient()
        nasaPhotosRepository = NasaPhotosRepository(apiService)

        photosViewModel = getNasaPhotosViewModel(roverName, page)
        photosViewModel.nasaPhotos.observe(viewLifecycleOwner, Observer{ // dikkat buraya ->> this vardi .
            bindUIForPhoto(it)
        })

        photosViewModel.networkState.observe(viewLifecycleOwner, Observer {
            if (it == NetworkState.LOADING)
                progressBar.visibility = View.VISIBLE

            if (it == NetworkState.LOADED)
                progressBar.visibility = View.GONE

            if (it == NetworkState.ERROR)
                Log.e("Hata.", "Hata ...")
        })
    }

    /**
     * Bind photo to list.
     */
    fun bindUIForPhoto(it: NasaPhotos){
        var list = it.photos
        if (list.size != 0) {
            var count = 0
            while (true) {
                photoList.add(list.get(count))
                count++
                if (count == list.size)
                    break
            }
            defineRecyclerView()
        }
    }

    /**
     * Define recyclerView.
     */
    fun defineRecyclerView(){
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        photoAdapter = PhotoAdapter(activity, photoList); // 2 --> bu fragmenttan gidildigini soyler .
        recyclerView.adapter = photoAdapter
    }


    /**
     * Get nasa photos view model respect to camera.
     */
    private fun getNasaPhotosWithCamViewModel(which:String, camera:String, page: Int): SingleNasaPhotosWithCamViewModel {
        return ViewModelProviders.of(this,object: ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleNasaPhotosWithCamViewModel(nasaPhotosWithCamRepository, which,camera,page) as T
            }
        })[SingleNasaPhotosWithCamViewModel::class.java]
    }


    /**
     * Get photo respect to camera.
     */
    fun runForPhotoWithCam(roverName: String, camera:String, page: Int){

        val apiService : NasaInterface = NasaClient.getClient()
        nasaPhotosWithCamRepository = NasaPhotosWithCamRepository(apiService)

        photosWithCamViewModel = getNasaPhotosWithCamViewModel(roverName, camera, page)
        photosWithCamViewModel.nasaPhotos.observe(viewLifecycleOwner, Observer{ // dikkat buraya ->> this vardi .
            bindUIForPhotoWithCam(it)
        })

        photosWithCamViewModel.networkState.observe(viewLifecycleOwner, Observer {
            if (it == NetworkState.LOADING)
                progressBar.visibility = View.VISIBLE

            if (it == NetworkState.LOADED)
                progressBar.visibility = View.GONE

            if (it == NetworkState.ERROR)
                Log.e("Hata.", "Hata ...")
        })
    }

    /**
     * Bind photo to list.
     */
    fun bindUIForPhotoWithCam(it: NasaPhotos){
        var list = it.photos
        if (list.size != 0) {
            var count = 0
            while (true) {
                photoList.add(list.get(count))
                count++
                if (count == list.size)
                    break
            }
            defineRecyclerView()
        }
    }
}