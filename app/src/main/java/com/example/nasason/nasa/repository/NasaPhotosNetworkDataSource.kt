package com.example.nasa.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvm.vo.NasaPhotos
import com.example.nasa.api.NasaInterface
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NasaPhotosNetworkDataSource (private val apiService:NasaInterface, private val compositeDisposable: CompositeDisposable) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _downloadedNasaPhotosResponse = MutableLiveData<NasaPhotos>()
    val downloadedNasaResponse: LiveData<NasaPhotos>
        get() = _downloadedNasaPhotosResponse

    /**
     * Fetch photo from api.
     */
    fun fetchNasaDetails(which: String, page: Int){
        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiService.getNasaDetails(which,1000,page)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadedNasaPhotosResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("NasaDetailsDataSource", it.message.toString())
                        }
                    )
            )
        }
        catch (e: Exception){
            Log.e("NasaDetailsDataSource", e.message.toString())
        }
    }

}