package com.astmirzhan.cityflow.data

import com.astmirzhan.cityflow.model.City

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
            id = "berlin",
            country = "Germany",
            name = "Berlin",
            latitude = 52.5175,
            longitude = 13.3960,
            description = "Germany's vibrant capital with iconic landmarks."
        ),
        City(
            id = "munich",
            country = "Germany",
            name = "Munich",
            latitude = 48.1374,
            longitude = 11.5755,
            description = "Bavarian city with historic squares and parks."
        ),
        City(
            id = "astana",
            country = "Kazakhstan",
            name = "Astana",
            latitude = 51.1282,
            longitude = 71.4270,
            description = "Capital city of Kazakhstan with modern landmarks."
        ),
        City(
            id = "almaty",
            country = "Kazakhstan",
            name = "Almaty",
            latitude = 43.2570,
            longitude = 76.9500,
            description = "Large cultural city near the mountains."
        )
    )

    fun getAllCities(): List<City> = cities

    fun getCountries(): List<String> = cities.map { it.country }.distinct()

    fun getCitiesByCountry(country: String): List<City> = cities.filter { it.country == country }

    fun getCityById(cityId: String): City? = cities.firstOrNull { it.id == cityId }
}
