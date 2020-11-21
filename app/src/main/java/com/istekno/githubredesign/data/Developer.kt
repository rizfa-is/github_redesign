package com.istekno.githubredesign.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Developer (
    var name : String? = null,
    var username : String? = null,
    var location : String? = null,
    var company : String? = null,
    var avatar : String? = null,
    var repository : Int? = null,
    var follower : Int? = null,
    var following : Int? = null
) : Parcelable

@Parcelize
data class Content (
    var name : String? = null,
    var photo : String? = null
) : Parcelable

