package com.example.nasason.nasa.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvm.vo.NasaPhotos
import com.example.nasa.api.NasaInterface
import com.example.nasa.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NasaPhotosWithCamNetworkDataSource (private val apiService: NasaInterface, private val compositeDisposable: CompositeDisposable) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _downloadedNasaPhotosResponse = MutableLiveData<NasaPhotos>()
    val downloadedNasaResponse: LiveData<NasaPhotos>
        get() = _downloadedNasaPhotosResponse

    /**
     * Fetch photo from api.
     */
    fun fetchNasaDetailsWithCam(which: String, camera: String, page: Int){
        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                    apiService.getNasaDetailsWithCamera(which,camera,1000,page)
                            .subscribeOn(Schedulers.io())
                            .subscribe(
                                    {
                                        _downloadedNasaPhotosResponse.postValue(it)
                                        _networkState.postValue(NetworkState.LOADED)
                                    },
                                    {
                                        _networkState.postValue(NetworkState.ERROR)
                                        Log.e("NasaDetailsDataSource", it.message.toString()) // hata olabilir dikkat !!!
                                    }
                            )
            )
        }
        catch (e: Exception){
            Log.e("NasaDetailsDataSource", e.message.toString()) // hata olabilir dikkat !!!
        }
    }
}