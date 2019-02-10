package com.android.sharkteeth.feature.api.entity

import android.os.Parcelable
import android.support.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class Photos(var photos: PhotoMetadata): Parcelable