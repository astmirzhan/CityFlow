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
            id = "berlin",
            country = "Germany",
            name = "Berlin",
            // Mitte, close to Museum Island and the central landmarks.
            latitude = 52.5175,
            longitude = 13.3960,
            description = "Germany's vibrant capital with iconic landmarks."
        ),
        City(
            id = "munich",
            country = "Germany",
            name = "Munich",
            // Marienplatz, the heart of the old town.
            latitude = 48.1374,
            longitude = 11.5755,
            description = "Bavarian city with historic squares and parks."
        ),
        City(
            id = "astana",
            country = "Kazakhstan",
            name = "Astana",
            // Nurzhol Boulevard axis — walking distance to the main landmarks.
            latitude = 51.1282,
            longitude = 71.4270,
            description = "Capital city of Kazakhstan with modern landmarks."
        ),
        City(
            id = "almaty",
            country = "Kazakhstan",
            name = "Almaty",
            // Historic centre near Panfilov Park / Arbat — close to the place cluster.
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
