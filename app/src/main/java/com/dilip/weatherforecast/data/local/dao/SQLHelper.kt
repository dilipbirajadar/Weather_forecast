package com.dilip.weatherforecast.data.local.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "location_db"
        private val TABLE_LOCATION = "location"
        private val KEY_ID = "id"
        private val KEY_LAT= "lat"
        private val KEY_LAN = "lan"
        private val KEY_CITY = "city"
        private val KEY_CREATED = "created_at"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_LOCATION + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_LAT + " TEXT,"
                + KEY_LAN + " TEXT," + KEY_CITY +" TEXT,"+  KEY_CREATED +" DATETIME DEFAULT CURRENT_TIMESTAMP" +")")
        p0?.execSQL(CREATE_CONTACTS_TABLE)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION)
        onCreate(p0)
    }



    /*
    * Insert locations details to DB
    * @param lat : latitude
    * @param lan : longitude
    * @param createdAt : location creation time
    * @param db : SQLiteDatabase
     */
    fun insertLocation(lat: String?, lan: String?, city: String?, createdAt: String?, db: SQLiteDatabase): Long {
        val values = ContentValues()
        values.put(KEY_LAT, lat)
        values.put(KEY_LAN, lan)
        values.put(KEY_CITY, city)
        values.put(KEY_CREATED, createdAt)
        return db.insert(TABLE_LOCATION, null, values)
    }

    /*
     * delete location details from DB
     * @param id : location id
     * @param db : SQLiteDatabase
     */
    fun deleteLocation(id: Long, db: SQLiteDatabase) {
        db.delete(TABLE_LOCATION, "_id=$id", null)
    }
}