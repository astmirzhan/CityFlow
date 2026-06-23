package com.astmirzhan.cityflow

import com.astmirzhan.cityflow.data.PlaceRepository
import com.astmirzhan.cityflow.domain.RoutePlanner
import com.astmirzhan.cityflow.model.IndoorPreference
import com.astmirzhan.cityflow.model.PlaceCategory
import com.astmirzhan.cityflow.model.RoutePreferences
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class RoutePlannerTest {

    private val start = PlaceRepository.CITY_CENTER_LAT to PlaceRepository.CITY_CENTER_LNG

    @Test
    fun walkMinutesUses5kmh() {
        // 5 km/h -> 5000 m in 60 min -> ~12 min per km.
        assertEquals(12, RoutePlanner.walkMinutes(1000.0))
    }

    @Test
    fun routeFitsWithinTimeBudget() {
        val prefs = RoutePreferences(60, emptySet(), IndoorPreference.ANY)
        val route = RoutePlanner.build(start.first, start.second, PlaceRepository.all(), prefs)
        assertTrue(route.totalMinutes <= 60)
        assertTrue(route.stops.isNotEmpty())
    }

    @Test
    fun ordersAreSequential() {
        val prefs = RoutePreferences(180, emptySet(), IndoorPreference.ANY)
        val route = RoutePlanner.build(start.first, start.second, PlaceRepository.all(), prefs)
        route.stops.forEachIndexed { index, stop -> assertEquals(index + 1, stop.order) }
    }

    @Test
    fun indoorPreferenceFiltersOutdoorPlaces() {
        val prefs = RoutePreferences(240, emptySet(), IndoorPreference.INDOOR)
        val route = RoutePlanner.build(start.first, start.second, PlaceRepository.all(), prefs)
        assertTrue(route.stops.all { it.place.indoor })
    }

    @Test
    fun categoryFilterRestrictsResults() {
        val prefs = RoutePreferences(240, setOf(PlaceCategory.CAFE), IndoorPreference.ANY)
        val route = RoutePlanner.build(start.first, start.second, PlaceRepository.all(), prefs)
        assertTrue(route.stops.all { it.place.category == PlaceCategory.CAFE })
    }

    @Test
    fun tinyBudgetMayProduceEmptyRoute() {
        val prefs = RoutePreferences(1, emptySet(), IndoorPreference.ANY)
        val route = RoutePlanner.build(start.first, start.second, PlaceRepository.all(), prefs)
        assertTrue(route.totalMinutes <= 1)
    }
}
