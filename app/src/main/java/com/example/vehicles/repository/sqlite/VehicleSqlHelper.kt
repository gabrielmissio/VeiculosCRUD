package com.example.vehicles.repository.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class VehicleSqlHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    override fun onCreate(db: SQLiteDatabase){
        db.execSQL(
            "CREATE TABLE $TABLE_VEHICLE("+
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "$COLUMN_MANUFACTURER INTEGER NOT NULL,"+
                    "$COLUMN_MODEL TEXT NOT NULL,"+
                    "$COLUMN_YEAR INTEGER NOT NULL,"+
                    "$COLUMN_GASOLINE INTEGER NOT NULL,"+
                    "$COLUMN_ETHANOL INTEGER NOT NULL)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int){
    }

}