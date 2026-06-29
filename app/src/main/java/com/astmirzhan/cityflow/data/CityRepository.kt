package com.astmirzhan.cityflow.data

import com.astmirzhan.cityflow.model.City

// Local curated city dataset used for the onboarding selection screen.
object CityRepository {

    private val cities = listOf(
        City(
            id = "schmalkalden",
            country = "Germany",
            name = "Schmalkalden",
            latitude = 50.7200,
            longitude = 10.4500,
            description = "Small historic German city for compact walking routes."
        ),
        City(
            id = "astana",
            country = "Kazakhstan",
            name = "Astana",
            latitude = 51.1694,
            longitude = 71.4491,
            description = "Capital city of Kazakhstan with modern landmarks."
        ),
        City(
            id = "almaty",
            country = "Kazakhstan",
            name = "Almaty",
            latitude = 43.2389,
            longitude = 76.8897,
            description = "Large cultural city near the mountains."
        )
    )

    fun getAllCities(): List<City> = cities

    fun getCountries(): List<String> = cities.map { it.country }.distinct()

    fun getCitiesByCountry(country: String): List<City> = cities.filter { it.country == country }

    fun getCityById(cityId: String): City? = cities.firstOrNull { it.id == cityId }
}
