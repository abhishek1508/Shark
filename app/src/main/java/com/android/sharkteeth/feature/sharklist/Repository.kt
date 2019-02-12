package com.android.sharkteeth.feature.sharklist

import com.android.sharkteeth.factory.ServiceGeneratorFactory
import com.android.sharkteeth.feature.api.SharkService
import com.android.sharkteeth.feature.api.entity.PhotoInfo
import com.android.sharkteeth.feature.api.entity.Photos
import io.reactivex.Observable
import retrofit2.adapter.rxjava2.Result
import javax.inject.Inject

class Repository @Inject internal constructor(){

    @Inject
    lateinit var factory: ServiceGeneratorFactory

    fun getImages(queries: Map<String, String>): Observable<Result<Photos>> {
        return factory.createService(SharkService::class.java).getAllImages(queries)
    }

    fun getImageInfo(queries: Map<String, String>): Observable<Result<PhotoInfo>> {
        return factory.createService(SharkService::class.java).getImageInfo(queries)
    }
}