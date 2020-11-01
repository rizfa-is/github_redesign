package com.istekno.githubredesign.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Developer (
    val name : String? = null,
    val username : String? = null,
    val location : String? = null,
    val company : String? = null,
    val avatar : String? = null,
    val repository : Int? = null,
    val follower : Int? = null,
    val following : Int? = null
) : Parcelable

@Parcelize
data class Content (
    val name : String? = null,
    val photo : String? = null
) : Parcelable

