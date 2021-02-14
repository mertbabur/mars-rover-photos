package com.example.nasa.single_nasa_details

import androidx.lifecycle.LiveData
import com.example.mvvm.vo.NasaPhotos
import com.example.nasa.api.NasaInterface
import com.example.nasa.repository.NasaPhotosNetworkDataSource
import com.example.nasa.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class NasaPhotosRepository (private val apiService: NasaInterface){

    lateinit var nasaPhotosNetworkDataSource: NasaPhotosNetworkDataSource

    /**
     * Fetch single photo from data source.
     */
    fun fetchSingelNasaDetails(compositeDisposable: CompositeDisposable, which: String, page: Int): LiveData<NasaPhotos> {

        nasaPhotosNetworkDataSource = NasaPhotosNetworkDataSource(apiService, compositeDisposable)
        nasaPhotosNetworkDataSource.fetchNasaDetails(which, page)

        return nasaPhotosNetworkDataSource.downloadedNasaResponse
    }

    /**
     * Network State.
     */
    fun getNasaDetailsNetworkState(): LiveData<NetworkState> {
        return nasaPhotosNetworkDataSource.networkState
    }
}