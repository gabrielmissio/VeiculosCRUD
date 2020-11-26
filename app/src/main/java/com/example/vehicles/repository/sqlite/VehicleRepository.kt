package com.example.vehicles.repository.sqlite

import com.example.vehicles.Vehicle

interface VehicleRepository {
    fun save(vehicle:Vehicle)
    fun search(term:String): ArrayList<Vehicle>
    fun remove(vararg vehicles: Vehicle)
}