package com.astmirzhan.cityflow.data

import com.astmirzhan.cityflow.model.Place
import com.astmirzhan.cityflow.model.PlaceCategory

object PlaceRepository {

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
        ),
        Place(
            id = "nurzhol",
            name = "Nurzhol Boulevard",
            category = PlaceCategory.PARK,
            latitude = 51.1280,
            longitude = 71.4238,
            description = "Central pedestrian boulevard with fountains and monuments.",
            indoor = false,
            visitMinutes = 25
        ),
        Place(
            id = "independence_square",
            name = "Independence Square",
            category = PlaceCategory.HISTORIC_SQUARE,
            latitude = 51.1219,
            longitude = 71.4635,
            description = "Open square with the Kazakh Eli monument.",
            indoor = false,
            visitMinutes = 15
        ),
        Place(
            id = "central_park_astana",
            name = "Central Park",
            category = PlaceCategory.PARK,
            latitude = 51.1547,
            longitude = 71.4184,
            description = "Riverside park with walking alleys and attractions.",
            indoor = false,
            visitMinutes = 30
        ),
        Place(
            id = "astana_cafe",
            name = "Coffee Boulevard",
            category = PlaceCategory.CAFE,
            latitude = 51.1290,
            longitude = 71.4300,
            description = "Popular coffee spot near the boulevard.",
            indoor = true,
            visitMinutes = 20
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
        ),
        Place(
            id = "almaty_cafe",
            name = "Arbat Coffee House",
            category = PlaceCategory.CAFE,
            latitude = 43.2570,
            longitude = 76.9480,
            description = "Cosy cafe on the pedestrian Arbat street.",
            indoor = true,
            visitMinutes = 20
        )
    )

    private val berlinPlaces = listOf(
        Place(
            id = "brandenburg_gate",
            name = "Brandenburg Gate",
            category = PlaceCategory.HISTORIC_SQUARE,
            latitude = 52.5163,
            longitude = 13.3777,
            description = "Neoclassical monument and symbol of the city.",
            indoor = false,
            visitMinutes = 20
        ),
        Place(
            id = "reichstag",
            name = "Reichstag Building",
            category = PlaceCategory.MUSEUM,
            latitude = 52.5186,
            longitude = 13.3762,
            description = "Historic parliament building with a glass dome.",
            indoor = true,
            visitMinutes = 40
        ),
        Place(
            id = "museum_island",
            name = "Museum Island",
            category = PlaceCategory.MUSEUM,
            latitude = 52.5212,
            longitude = 13.3966,
            description = "UNESCO ensemble of five world-class museums.",
            indoor = true,
            visitMinutes = 50
        ),
        Place(
            id = "berlin_cathedral",
            name = "Berlin Cathedral",
            category = PlaceCategory.HISTORIC_SQUARE,
            latitude = 52.5190,
            longitude = 13.4010,
            description = "Grand baroque cathedral on Museum Island.",
            indoor = true,
            visitMinutes = 30
        ),
        Place(
            id = "alexanderplatz",
            name = "Alexanderplatz",
            category = PlaceCategory.HISTORIC_SQUARE,
            latitude = 52.5219,
            longitude = 13.4132,
            description = "Busy central square with the TV Tower.",
            indoor = false,
            visitMinutes = 20
        ),
        Place(
            id = "gendarmenmarkt",
            name = "Gendarmenmarkt",
            category = PlaceCategory.HISTORIC_SQUARE,
            latitude = 52.5138,
            longitude = 13.3927,
            description = "Elegant square with concert hall and cathedrals.",
            indoor = false,
            visitMinutes = 20
        ),
        Place(
            id = "tiergarten",
            name = "Tiergarten Park",
            category = PlaceCategory.PARK,
            latitude = 52.5145,
            longitude = 13.3501,
            description = "Large central park with shaded walking paths.",
            indoor = false,
            visitMinutes = 30
        ),
        Place(
            id = "berlin_cafe",
            name = "Hackescher Cafe",
            category = PlaceCategory.CAFE,
            latitude = 52.5225,
            longitude = 13.4025,
            description = "Trendy cafe near Hackescher Markt.",
            indoor = true,
            visitMinutes = 25
        ),
        Place(
            id = "tv_tower",
            name = "TV Tower Viewpoint",
            category = PlaceCategory.VIEWPOINT,
            latitude = 52.5208,
            longitude = 13.4094,
            description = "Panoramic observation deck over Berlin.",
            indoor = true,
            visitMinutes = 35
        )
    )

    private val munichPlaces = listOf(
        Place(
            id = "marienplatz",
            name = "Marienplatz",
            category = PlaceCategory.HISTORIC_SQUARE,
            latitude = 48.1374,
            longitude = 11.5755,
            description = "Central square with the New Town Hall.",
            indoor = false,
            visitMinutes = 20
        ),
        Place(
            id = "frauenkirche",
            name = "Frauenkirche",
            category = PlaceCategory.HISTORIC_SQUARE,
            latitude = 48.1385,
            longitude = 11.5735,
            description = "Iconic cathedral with twin domed towers.",
            indoor = true,
            visitMinutes = 25
        ),
        Place(
            id = "viktualienmarkt",
            name = "Viktualienmarkt",
            category = PlaceCategory.SUPERMARKET,
            latitude = 48.1352,
            longitude = 11.5762,
            description = "Famous open-air food market.",
            indoor = false,
            visitMinutes = 30
        ),
        Place(
            id = "peterskirche",
            name = "St. Peter's Viewpoint",
            category = PlaceCategory.VIEWPOINT,
            latitude = 48.1360,
            longitude = 11.5765,
            description = "Tower with panoramic views over the old town.",
            indoor = true,
            visitMinutes = 25
        ),
        Place(
            id = "odeonsplatz",
            name = "Odeonsplatz",
            category = PlaceCategory.HISTORIC_SQUARE,
            latitude = 48.1424,
            longitude = 11.5772,
            description = "Grand square with the Feldherrnhalle.",
            indoor = false,
            visitMinutes = 15
        ),
        Place(
            id = "hofgarten",
            name = "Hofgarten",
            category = PlaceCategory.PARK,
            latitude = 48.1428,
            longitude = 11.5810,
            description = "Renaissance-style formal garden.",
            indoor = false,
            visitMinutes = 25
        ),
        Place(
            id = "english_garden",
            name = "English Garden",
            category = PlaceCategory.PARK,
            latitude = 48.1642,
            longitude = 11.6056,
            description = "One of the world's largest urban parks.",
            indoor = false,
            visitMinutes = 40
        ),
        Place(
            id = "deutsches_museum",
            name = "Deutsches Museum",
            category = PlaceCategory.MUSEUM,
            latitude = 48.1298,
            longitude = 11.5834,
            description = "World's largest science and technology museum.",
            indoor = true,
            visitMinutes = 50
        ),
        Place(
            id = "munich_cafe",
            name = "Altstadt Cafe",
            category = PlaceCategory.CAFE,
            latitude = 48.1380,
            longitude = 11.5760,
            description = "Cosy cafe in the heart of the old town.",
            indoor = true,
            visitMinutes = 20
        )
    )

    fun all(): List<Place> = places

    fun getPlaces(): List<Place> = places

    fun getPlacesForCity(cityId: String): List<Place> = when (cityId) {
        "astana" -> astanaPlaces
        "almaty" -> almatyPlaces
        "berlin" -> berlinPlaces
        "munich" -> munichPlaces
        else -> places
    }

    fun byCategories(categories: Set<PlaceCategory>): List<Place> =
        if (categories.isEmpty()) places else places.filter { it.category in categories }

    fun byId(id: String): Place? = places.firstOrNull { it.id == id }
}
