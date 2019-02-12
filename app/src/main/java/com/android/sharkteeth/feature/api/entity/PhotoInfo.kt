package com.android.sharkteeth.feature.api.entity

import android.os.Parcelable
import android.support.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class PhotoInfo(@field:SerializedName("photo")
                     var photo: PhotoInfoMetadata): Parcelable