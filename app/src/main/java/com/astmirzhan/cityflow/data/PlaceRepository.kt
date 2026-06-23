package com.astmirzhan.cityflow.data

import com.astmirzhan.cityflow.model.Place
import com.astmirzhan.cityflow.model.PlaceCategory

// Local curated places dataset (preloaded city places) for Schmalkalden, Germany.
object PlaceRepository {

    // City-centre fallback used when no GPS fix is available.
    const val CITY_CENTER_LAT = 50.7212
    const val CITY_CENTER_LNG = 10.4544

    private val places = listOf(
        Place(
            id = "altmarkt",
            name = "Altmarkt",
            category = PlaceCategory.HISTORIC_SQUARE,
            latitude = 50.7211,
            longitude = 10.4541,
            description = "Historic market square with timber-framed houses.",
            indoor = false,
            visitMinutes = 15
        ),
        Place(
            id = "old_town",
            name = "Old Town Lanes",
            category = PlaceCategory.OLD_TOWN,
            latitude = 50.7218,
            longitude = 10.4528,
            description = "Medieval cobbled streets and half-timbered facades.",
            indoor = false,
            visitMinutes = 20
        ),
        Place(
            id = "wilhelmsburg",
            name = "Schloss Wilhelmsburg Museum",
            category = PlaceCategory.MUSEUM,
            latitude = 50.7235,
            longitude = 10.4490,
            description = "Renaissance palace and regional museum.",
            indoor = true,
            visitMinutes = 45
        ),
        Place(
            id = "campus",
            name = "Schmalkalden University Campus",
            category = PlaceCategory.CAMPUS,
            latitude = 50.7156,
            longitude = 10.4602,
            description = "University of Applied Sciences campus grounds.",
            indoor = false,
            visitMinutes = 20
        ),
        Place(
            id = "cafe_central",
            name = "Cafe am Markt",
            category = PlaceCategory.CAFE,
            latitude = 50.7209,
            longitude = 10.4549,
            description = "Cosy cafe with coffee and regional cake.",
            indoor = true,
            visitMinutes = 25
        ),
        Place(
            id = "stadtpark",
            name = "Stadtpark Schmalkalden",
            category = PlaceCategory.PARK,
            latitude = 50.7195,
            longitude = 10.4580,
            description = "Green park with walking paths and benches.",
            indoor = false,
            visitMinutes = 20
        ),
        Place(
            id = "viewpoint",
            name = "Queste Viewpoint",
            category = PlaceCategory.VIEWPOINT,
            latitude = 50.7290,
            longitude = 10.4625,
            description = "Hilltop viewpoint over the town and valley.",
            indoor = false,
            visitMinutes = 25
        ),
        Place(
            id = "library",
            name = "Stadtbibliothek",
            category = PlaceCategory.LIBRARY,
            latitude = 50.7224,
            longitude = 10.4552,
            description = "Public city library and reading rooms.",
            indoor = true,
            visitMinutes = 20
        ),
        Place(
            id = "station",
            name = "Bahnhof Schmalkalden",
            category = PlaceCategory.STATION,
            latitude = 50.7138,
            longitude = 10.4498,
            description = "Town railway station and transport hub.",
            indoor = false,
            visitMinutes = 10
        ),
        Place(
            id = "supermarket",
            name = "Markt Supermarket",
            category = PlaceCategory.SUPERMARKET,
            latitude = 50.7182,
            longitude = 10.4521,
            description = "Local supermarket for snacks and water.",
            indoor = true,
            visitMinutes = 10
        )
    )

    fun all(): List<Place> = places

    fun byCategories(categories: Set<PlaceCategory>): List<Place> =
        if (categories.isEmpty()) places else places.filter { it.category in categories }

    fun byId(id: String): Place? = places.firstOrNull { it.id == id }
}
