package com.example.task2coders.services

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import kotlin.time.measureTime

class SQLConection(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 2
        const val DATABASE_NAME = "task_two_coders"
    }

    object FavTable : BaseColumns {
        const val TABLE_NAME = "fav_table"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE ${FavTable.TABLE_NAME} (id INTEGER, name LONG, is_fav BOOLEAN)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${FavTable.TABLE_NAME}")
        onCreate(db)
    }

    fun isAlreadyFav(cursor: Cursor): Boolean {
        with(cursor) {
            if (cursor.count > 0) cursor.moveToNext()
            return if (cursor.count > 0) (cursor.getInt(1) > 0) else false
        }
    }

}