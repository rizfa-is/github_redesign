package com.istekno.githubredesign.db

import android.provider.BaseColumns

internal class DatabaseContract {

    internal class FavoriteColums : BaseColumns {

        companion object {
            const val TABLE_NAME = "favorite"
            const val ID = "id"
            const val USERNAME = "username"
            const val AVATAR = "avatar"
        }
    }
}