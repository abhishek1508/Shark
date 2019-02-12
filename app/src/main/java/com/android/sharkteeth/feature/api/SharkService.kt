package com.android.sharkteeth.feature.api

import com.android.sharkteeth.feature.api.entity.PhotoInfo
import com.android.sharkteeth.feature.api.entity.Photos
import io.reactivex.Observable
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface SharkService {
    @GET("services/rest/")
    fun getAllImages(@QueryMap queries: Map<String, String>): Observable<Result<Photos>>

    @GET("services/rest/")
    fun getImageInfo(@QueryMap queries: Map<String, String>): Observable<Result<PhotoInfo>>
}