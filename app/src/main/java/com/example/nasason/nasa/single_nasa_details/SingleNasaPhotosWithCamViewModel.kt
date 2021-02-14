package com.example.nasason.nasa.single_nasa_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm.vo.NasaPhotos
import com.example.nasa.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class SingleNasaPhotosWithCamViewModel (private val nasaRepository: NasaPhotosWithCamRepository, which: String, camera:String, page:Int): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val nasaPhotos: LiveData<NasaPhotos> by lazy{
        nasaRepository.fetchSingelNasaDetailsWithCam(compositeDisposable, which, camera, page)
    }

    val networkState: LiveData<NetworkState> by lazy {
        nasaRepository.getNasaDetailsWithCamNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}