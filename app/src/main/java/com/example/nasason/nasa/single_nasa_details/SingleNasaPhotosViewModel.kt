package com.example.nasa.single_nasa_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm.vo.NasaPhotos
import com.example.nasa.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class SingleNasaPhotosViewModel (private val nasaRepository: NasaPhotosRepository, which: String, page:Int): ViewModel(){
    private val compositeDisposable = CompositeDisposable()

    val nasaPhotos: LiveData<NasaPhotos> by lazy{
        nasaRepository.fetchSingelNasaDetails(compositeDisposable, which, page)
    }

    val networkState: LiveData<NetworkState> by lazy {
        nasaRepository.getNasaDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}