package com.nollpointer.prankme

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val DB_NAME = "PrankMe"
const val DB_VERSION = 1

const val CONTACTS = "CONTACTS"
const val MESSAGES = "MESSAGES"

class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DB_NAME,null, DB_VERSION) {

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onDowngrade(db, oldVersion, newVersion)
    }

    override fun onCreate(db: SQLiteDatabase?) {

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}