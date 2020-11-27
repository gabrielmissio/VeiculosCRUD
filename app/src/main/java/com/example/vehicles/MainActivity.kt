package com.example.vehicles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.example.vehicles.repository.sqlite.SQLiteRepository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var vehicleDB:SQLiteRepository
    lateinit var vehicles:ArrayList<Vehicle>
    lateinit var adapter:VehicleAdapter
    private val vehiclesInput = mutableListOf<Vehicle>(
        Vehicle(0,"Onix",2018,1,1,1),
        Vehicle(0,"Uno",2012,2,1,0),
        Vehicle(0,"Del Rey",1988,3,0,1),
        Vehicle(0,"Gol",2014,0,1,1)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView.emptyView = findViewById(android.R.id.empty)
        vehicleDB = SQLiteRepository(this)
        refreshView()
        listView.setOnItemClickListener { parent, view, position, id ->
            val vehicle = parent.getItemAtPosition(position) as? Vehicle
            if(vehicle != null){
                val (id,model,year) = vehicle
                Toast.makeText(this, "Removido $model $year",Toast.LENGTH_LONG).show()
                vehicleDB.remove(vehicle)
                vehicles.remove(vehicle)
                adapter.notifyDataSetChanged()
            }
        }
        btnAdd.setOnClickListener(){
            val intent = Intent(this, IncludeVehicleActivity::class.java)
            startActivity(intent)
        }
    }

    private fun refreshView(){
        vehicles = vehicleDB.search("")
        adapter = VehicleAdapter(this,vehicles)
        listView.adapter = adapter
    }

    override fun onResume() {
        refreshView()
        super.onResume()
    }
}