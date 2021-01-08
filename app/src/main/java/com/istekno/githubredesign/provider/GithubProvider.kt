package com.istekno.githubredesign.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.istekno.githubredesign.db.DatabaseContract.AUTHORITY
import com.istekno.githubredesign.db.DatabaseContract.FavoriteColumn.Companion.CONTENT_URI
import com.istekno.githubredesign.db.DatabaseContract.FavoriteColumn.Companion.TABLE_NAME
import com.istekno.githubredesign.db.GithubHelper

class GithubProvider : ContentProvider() {

    companion object {
        private const val GITHUB = 1
        private const val GITHUB_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var githubHelper: GithubHelper

        init {
            // content://com.dicoding.picodiploma.mynotesapp/note
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, GITHUB)
            // content://com.dicoding.picodiploma.mynotesapp/note/id
            sUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", GITHUB_ID)
        }

    }

    override fun onCreate(): Boolean {
        githubHelper = GithubHelper.getInstance(context as Context)
        githubHelper.open()

        return true
    }

    override fun query(uri: Uri, strings: Array<String>?, s: String?, strings1: Array<String>?, s1: String?): Cursor? {
        return when (sUriMatcher.match(uri)) {
            GITHUB -> githubHelper.queryAll()
            GITHUB_ID -> githubHelper.queryById(uri.lastPathSegment.toString())
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        val added: Long = when (GITHUB) {
            sUriMatcher.match(uri) -> githubHelper.insert(contentValues)
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun update(uri: Uri, contentValues: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        val updated: Int = when (GITHUB_ID) {
            sUriMatcher.match(uri) -> githubHelper.update(uri.lastPathSegment.toString(), contentValues)
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return updated
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted: Int = when (GITHUB_ID) {
            sUriMatcher.match(uri) -> githubHelper.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return deleted
    }
}