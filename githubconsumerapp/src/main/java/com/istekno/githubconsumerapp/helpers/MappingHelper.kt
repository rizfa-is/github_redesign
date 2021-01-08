package com.istekno.githubconsumerapp.helpers

import android.database.Cursor
import com.istekno.githubconsumerapp.db.DatabaseContract
import com.istekno.githubconsumerapp.entity.Favorite

object MappingHelper {

    fun mapCursorToArrayList(favCursor: Cursor?): ArrayList<Favorite> {
        val favList = ArrayList<Favorite>()

        favCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavoriteColumn.ID))
                val username = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumn.USERNAME))
                val avatar = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumn.AVATAR))

                favList.add(Favorite(id, username, avatar))
            }
        }
        return favList
    }

    fun mapCursorToObject(favCursor: Cursor?): Favorite {
        var favorite = Favorite()
        favCursor?.apply {
            moveToFirst()
            val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavoriteColumn.ID))
            val username = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumn.USERNAME))
            val avatar = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumn.AVATAR))

            favorite = Favorite(id, username, avatar)
        }
        return favorite
    }
}