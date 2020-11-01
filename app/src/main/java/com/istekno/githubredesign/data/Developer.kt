package com.istekno.githubredesign.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Developer (
    val name : String?,
    val username : String?,
    val location : String?,
    val company : String?,
    val avatar : String?,
    val repository : Int?,
    val follower : Int?,
    val following : Int?
) : Parcelable

@Parcelize
data class Content (
    val name : String?,
    val photo : String?
) : Parcelable

