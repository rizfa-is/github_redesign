package com.istekno.githubconsumerapp.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.istekno.githubredesign"
    const val SCHEME = "content"

    class FavoriteColumn : BaseColumns {

        companion object {
            const val TABLE_NAME = "favorite"
            const val ID = "id"
            const val USERNAME = "username"
            const val AVATAR = "avatar"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}