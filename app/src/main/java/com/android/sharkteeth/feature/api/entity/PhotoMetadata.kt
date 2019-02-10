package com.android.sharkteeth.feature.api.entity

import android.os.Parcelable
import android.support.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class PhotoMetadata(var page: Int, var pages: Int, var perpage: Int, var total: String, var photo: MutableList<Photo>):
        Parcelable