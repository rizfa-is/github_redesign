package com.istekno.githubredesign.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.istekno.githubredesign.db.DatabaseContract.FavoriteColumn.Companion.ID
import com.istekno.githubredesign.db.DatabaseContract.FavoriteColumn.Companion.TABLE_NAME
import java.sql.SQLException

class GithubHelper(context: Context) {

    private lateinit var database: SQLiteDatabase
    private var databaseHelper: DatabaseHelper = DatabaseHelper(context)

    companion object {

        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: GithubHelper? = null

        fun getInstance(context: Context): GithubHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: GithubHelper(context)
            }

    }

    @Throws(SQLException::class)
    fun open() {
        database = databaseHelper.writableDatabase
    }

    fun close() {
        databaseHelper.close()

        if (database.isOpen) {
            database.close()
        }
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$ID ASC")
    }

    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$ID = ?", arrayOf(id))
    }

    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$ID = $id", null)
    }

}