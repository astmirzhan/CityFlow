package com.astmirzhan.cityflow

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.astmirzhan.cityflow.data.CityRepository
import com.astmirzhan.cityflow.data.SelectedCityRepository
import com.astmirzhan.cityflow.model.City

class CitySelectionActivity : AppCompatActivity() {

    private lateinit var selectedCityRepository: SelectedCityRepository
    private lateinit var countrySpinner: Spinner
    private lateinit var citySpinner: Spinner

    private var citiesForCountry: List<City> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        selectedCityRepository = SelectedCityRepository(this)
        if (selectedCityRepository.getSelectedCity() != null) {
            openMain()
            return
        }

        setContentView(R.layout.activity_city_selection)

        countrySpinner = findViewById(R.id.countrySpinner)
        citySpinner = findViewById(R.id.citySpinner)

        setupCountrySpinner()
        findViewById<Button>(R.id.continueButton).setOnClickListener { onContinueClicked() }
    }

    private fun setupCountrySpinner() {
        val countries = CityRepository.getCountries()
        countrySpinner.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item, countries
        )
        countrySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateCitySpinner(countries[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun updateCitySpinner(country: String) {
        citiesForCountry = CityRepository.getCitiesByCountry(country)
        citySpinner.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item, citiesForCountry.map { it.name }
        )
    }

    private fun onContinueClicked() {
        val position = citySpinner.selectedItemPosition
        if (position < 0 || position >= citiesForCountry.size) return
        selectedCityRepository.saveSelectedCity(citiesForCountry[position].id)
        openMain()
    }

    private fun openMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
