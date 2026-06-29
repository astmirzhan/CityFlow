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

    private val astanaPlaces = listOf(
        Place(
            id = "bayterek",
            name = "Bayterek Tower",
            category = PlaceCategory.VIEWPOINT,
            latitude = 51.1283,
            longitude = 71.4305,
            description = "Iconic observation tower symbolising the capital.",
            indoor = true,
            visitMinutes = 30
        ),
        Place(
            id = "khan_shatyr",
            name = "Khan Shatyr",
            category = PlaceCategory.SUPERMARKET,
            latitude = 51.1326,
            longitude = 71.4045,
            description = "Tent-shaped shopping and entertainment centre.",
            indoor = true,
            visitMinutes = 40
        ),
        Place(
            id = "palace_peace",
            name = "Palace of Peace and Reconciliation",
            category = PlaceCategory.MUSEUM,
            latitude = 51.1240,
            longitude = 71.4500,
            description = "Pyramid hosting cultural and exhibition spaces.",
            indoor = true,
            visitMinutes = 35
        ),
        Place(
            id = "nur_alem",
            name = "Nur Alem Museum",
            category = PlaceCategory.MUSEUM,
            latitude = 51.0894,
            longitude = 71.4181,
            description = "Sphere-shaped museum of future energy.",
            indoor = true,
            visitMinutes = 45
        ),
        Place(
            id = "astana_opera",
            name = "Astana Opera",
            category = PlaceCategory.HISTORIC_SQUARE,
            latitude = 51.1499,
            longitude = 71.4133,
            description = "Grand opera and ballet theatre.",
            indoor = true,
            visitMinutes = 30
        ),
        Place(
            id = "hazret_sultan",
            name = "Hazret Sultan Mosque",
            category = PlaceCategory.HISTORIC_SQUARE,
            latitude = 51.1247,
            longitude = 71.4717,
            description = "Large mosque with classical Islamic architecture.",
            indoor = true,
            visitMinutes = 30
        )
    )

    private val almatyPlaces = listOf(
        Place(
            id = "kok_tobe",
            name = "Kok Tobe",
            category = PlaceCategory.VIEWPOINT,
            latitude = 43.2331,
            longitude = 76.9750,
            description = "Hilltop park with panoramic city views.",
            indoor = false,
            visitMinutes = 40
        ),
        Place(
            id = "panfilov_park",
            name = "Panfilov Park",
            category = PlaceCategory.PARK,
            latitude = 43.2586,
            longitude = 76.9533,
            description = "Central park with shaded walking paths.",
            indoor = false,
            visitMinutes = 25
        ),
        Place(
            id = "zenkov_cathedral",
            name = "Zenkov Cathedral",
            category = PlaceCategory.HISTORIC_SQUARE,
            latitude = 43.2585,
            longitude = 76.9540,
            description = "Colourful wooden Russian Orthodox cathedral.",
            indoor = true,
            visitMinutes = 20
        ),
        Place(
            id = "green_bazaar",
            name = "Green Bazaar",
            category = PlaceCategory.SUPERMARKET,
            latitude = 43.2630,
            longitude = 76.9600,
            description = "Lively market with local food and produce.",
            indoor = true,
            visitMinutes = 30
        ),
        Place(
            id = "central_museum",
            name = "Central State Museum",
            category = PlaceCategory.MUSEUM,
            latitude = 43.2360,
            longitude = 76.9560,
            description = "National museum of Kazakhstan's history.",
            indoor = true,
            visitMinutes = 45
        ),
        Place(
            id = "republic_square",
            name = "Republic Square",
            category = PlaceCategory.HISTORIC_SQUARE,
            latitude = 43.2390,
            longitude = 76.9450,
            description = "Main public square with the Independence Monument.",
            indoor = false,
            visitMinutes = 20
        )
    )

    fun all(): List<Place> = places

    fun getPlaces(): List<Place> = places

    fun getPlacesForCity(cityId: String): List<Place> = when (cityId) {
        "astana" -> astanaPlaces
        "almaty" -> almatyPlaces
        else -> places
    }

    fun byCategories(categories: Set<PlaceCategory>): List<Place> =
        if (categories.isEmpty()) places else places.filter { it.category in categories }

    fun byId(id: String): Place? = places.firstOrNull { it.id == id }
}
