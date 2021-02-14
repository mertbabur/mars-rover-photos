package com.example.nasason.nasa.single_nasa_details

import androidx.lifecycle.LiveData
import com.example.mvvm.vo.NasaPhotos
import com.example.nasa.api.NasaInterface
import com.example.nasa.repository.NetworkState
import com.example.nasason.nasa.repository.NasaPhotosWithCamNetworkDataSource
import io.reactivex.disposables.CompositeDisposable

class NasaPhotosWithCamRepository (private val apiService: NasaInterface){

    lateinit var nasaPhotosWithCamNetworkDataSource: NasaPhotosWithCamNetworkDataSource

    /**
     * Fetch single photo from data source.
     */
    fun fetchSingelNasaDetailsWithCam(compositeDisposable: CompositeDisposable, which: String, camera:String, page: Int): LiveData<NasaPhotos> {

        nasaPhotosWithCamNetworkDataSource = NasaPhotosWithCamNetworkDataSource(apiService, compositeDisposable)
        nasaPhotosWithCamNetworkDataSource.fetchNasaDetailsWithCam(which, camera, page)

        return nasaPhotosWithCamNetworkDataSource.downloadedNasaResponse
    }

    /**
     * Network State.
     */
    fun getNasaDetailsWithCamNetworkState(): LiveData<NetworkState> {
        return nasaPhotosWithCamNetworkDataSource.networkState
    }
}