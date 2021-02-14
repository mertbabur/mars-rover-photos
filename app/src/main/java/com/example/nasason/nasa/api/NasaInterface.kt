package com.example.nasa.api

import com.example.mvvm.vo.NasaPhotos
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface NasaInterface {

    /**
     * All photos respect to rover name.
     */
    @GET("rovers/{which}/photos")
    fun getNasaDetails(  @Path("which") which: String
                        ,@Query("sol") @androidx.annotation.IntRange(from = 1000) sol:Int
                        ,@Query("page") @androidx.annotation.IntRange(from = 0) page: Int): Single<NasaPhotos>

    /**
     * Photos respect to rover name and camera name.
     */
    @GET("rovers/{which}/photos")
    fun getNasaDetailsWithCamera( @Path("which") which: String
                                 ,@Query("camera") camera: String
                                 ,@Query("sol") @androidx.annotation.IntRange(from = 1000) sol:Int
                                 ,@Query("page") @androidx.annotation.IntRange(from = 0) page: Int): Single<NasaPhotos>
}