package com.android.sharkteeth.feature.api.entity

import android.os.Parcelable
import android.support.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class Photo(
        @field:SerializedName("id")
        var id: String,
        @field:SerializedName("owner")
        var owner: String,
        @field:SerializedName("secret")
        var secret: String,
        @field:SerializedName("server")
        var server: String,
        @field:SerializedName("farm")
        var farm: Int,
        @field:SerializedName("title")
        var title: String,
        @field:SerializedName("isFriend")
        var isFriend: Int,
        @field:SerializedName("isPublic")
        var isPublic: Int,
        @field:SerializedName("isFamily")
        var isFamily: Int
): Parcelable