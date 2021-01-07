package com.istekno.githubredesign.helpers

import android.database.Cursor
import com.istekno.githubredesign.db.DatabaseContract
import com.istekno.githubredesign.entity.Favorite

object MappingHelper {

    fun mapCursorToArrayList(favCursor: Cursor): ArrayList<Favorite> {
        val favList = ArrayList<Favorite>()

        favCursor.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavoriteColums.ID))
                val username = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColums.USERNAME))
                val avatar = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColums.AVATAR))

                favList.add(Favorite(id, username, avatar))
            }
        }
        return favList
    }
}