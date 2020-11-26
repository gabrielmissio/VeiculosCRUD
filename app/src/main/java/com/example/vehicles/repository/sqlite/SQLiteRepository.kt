package com.example.vehicles.repository.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.vehicles.Vehicle

class SQLiteRepository (ctx: Context): VehicleRepository {
    private val helper: VehicleSqlHelper = VehicleSqlHelper(ctx)

    private fun insert(vehicle: Vehicle){
        val db = helper.writableDatabase
        val cv = ContentValues().apply{
            put(COLUMN_MODEL,vehicle.model)
            put(COLUMN_YEAR,vehicle.year)
            put(COLUMN_MANUFACTURER,vehicle.manufacturer)
            put(COLUMN_GASOLINE,vehicle.gasoline)
            put(COLUMN_ETHANOL, vehicle.ethanol)
        }
        val id = db.insert(TABLE_VEHICLE, null, cv)
        if (id != -1L){
            vehicle.id=id
        }
        db.close()
    }

    override fun save(vehicle: Vehicle){
        if(vehicle.id == 0L){
            insert(vehicle)
        }
    }

    override fun search(term: String): ArrayList<Vehicle> {
        var sql = "Select * FROM $TABLE_VEHICLE"
        var args: Array<String>? = null
        if(term.isNotEmpty()){
            sql += "WHERE $COLUMN_MODEL LIKE ?"
            args = arrayOf("%term%")
        }
        sql += " ORDER BY $COLUMN_ID"
        val db = helper.readableDatabase
        val cursor = db.rawQuery(sql, args)
        val vehicles = ArrayList<Vehicle>()
        while (cursor.moveToNext()){
            val vehicle = vehicleFromCursor(cursor)
            vehicles.add(vehicle)
        }
        cursor.close()
        db.close()
        return vehicles
    }

    private fun vehicleFromCursor(cursor: Cursor): Vehicle{
        val id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
        val model = cursor.getString(cursor.getColumnIndex(COLUMN_MODEL))
        val year = cursor.getInt(cursor.getColumnIndex(COLUMN_YEAR))
        val manufacturer = cursor.getInt(cursor.getColumnIndex(COLUMN_MANUFACTURER))
        val gasoline = cursor.getInt(cursor.getColumnIndex(COLUMN_GASOLINE))
        val ethanol = cursor.getInt(cursor.getColumnIndex(COLUMN_ETHANOL))

        return Vehicle(id,model,year,manufacturer,gasoline, ethanol)
    }

    override fun remove(vararg vehicles: Vehicle) {
        val db = helper.writableDatabase
        for ( vehicle in vehicles){
            db.delete(
                TABLE_VEHICLE, "$COLUMN_ID = ?", arrayOf(vehicle.id.toString())
            )
        }
        db.close()
    }
}