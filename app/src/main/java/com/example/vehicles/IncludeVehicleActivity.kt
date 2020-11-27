package com.example.vehicles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.text.set
import androidx.core.view.get
import com.example.vehicles.repository.sqlite.SQLiteRepository
import kotlinx.android.synthetic.main.activity_include_vehicle.*

class IncludeVehicleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_include_vehicle)
        val vehicleDB = SQLiteRepository(this)
        initSpinner()

        btnSave.setOnClickListener(){
            val id = radioGroupFuel.checkedRadioButtonId
            val radio = findViewById<RadioButton>(id)
            var gasoline = 0
            var ethanol = 0

            if(radio == null)
            else if(radio.text.equals("Flex")){
                gasoline = 1
                ethanol = 1
            }
            else if(radio.text.equals("Gasolina")){
                gasoline = 1
            }
            else if (radio.text.equals("Etanol"))
            {
                ethanol = 1
            }

            if(txtModel.text.isEmpty() || txtYear.text.isEmpty() ){
                Toast.makeText(this,"Dados invalidos!",Toast.LENGTH_LONG).show()
            }else{
                val vehicle = Vehicle(0,txtModel.text.toString(), Integer.valueOf(txtYear.text.toString()),spnManufacturer.selectedItemPosition,gasoline, ethanol)
                vehicleDB.save(vehicle)
                Toast.makeText(this,"Salvo!",Toast.LENGTH_LONG).show()
                txtModel.text.clear()
                txtYear.text.clear()
            }
        }
    }


    private fun initSpinner(){
        val names = arrayOf("Volkswagen", "Chevrolet", "Fiat", "Ford")
        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,names)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnManufacturer.adapter = adapter
    }


}