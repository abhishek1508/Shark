package com.android.sharkteeth.feature.api.entity

import android.os.Parcelable
import android.support.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class PhotoInfoMetadata(
        @field:SerializedName("id")
        var id: String,
        @field:SerializedName("secret")
        var secret: String,
        @field:SerializedName("server")
        var server: String,
        @field:SerializedName("farm")
        var farm: Int,
        @field:SerializedName("title")
        var title: Content,
        @field:SerializedName("description")
        var description: Content): Parcelable