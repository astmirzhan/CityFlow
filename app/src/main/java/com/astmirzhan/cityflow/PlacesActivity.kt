package com.astmirzhan.cityflow

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.astmirzhan.cityflow.data.PlaceRepository
import com.astmirzhan.cityflow.data.SelectedCityRepository
import com.astmirzhan.cityflow.ui.PlaceAdapter

class PlacesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val city = SelectedCityRepository(this).getSelectedCity()
        if (city == null) {
            startActivity(Intent(this, CitySelectionActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_places)
        title = getString(R.string.places_title_city, city.name)

        val list = findViewById<RecyclerView>(R.id.placesList)
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = PlaceAdapter(PlaceRepository.getPlacesForCity(city.id))
    }
}
