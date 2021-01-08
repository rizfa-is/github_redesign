package com.istekno.githubconsumerapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DeveloperDetail (
    var name : String = "",
    var username : String = "",
    var location : String = "",
    var company : String = "",
    var avatar : String = "",
    var repository : Int = 0,
    var follower : Int = 0,
    var following : Int = 0
) : Parcelable

@Parcelize
data class DeveloperList (
    var username : String = "",
    var avatar : String = ""
) : Parcelable

@Parcelize
data class Repository (
    var name : String = "",
    var username: String = "",
    var avatar : String = ""
) : Parcelable

@Parcelize
data class Follows (
    var name : String = "",
    var location : String = "",
    var avatar : String = ""
) : Parcelable