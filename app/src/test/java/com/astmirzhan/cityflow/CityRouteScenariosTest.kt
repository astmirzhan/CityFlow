package com.astmirzhan.cityflow

import com.astmirzhan.cityflow.data.CityRepository
import com.astmirzhan.cityflow.data.PlaceRepository
import com.astmirzhan.cityflow.domain.RoutePlanner
import com.astmirzhan.cityflow.model.IndoorPreference
import com.astmirzhan.cityflow.model.RoutePreferences
import org.junit.Assert.assertTrue
import org.junit.Test

// End-to-end planner scenarios: every city must produce a non-empty route
// from its own centre for realistic time budgets and every indoor preference.
class CityRouteScenariosTest {

    private val budgets = listOf(30, 60, 90, 240)

    @Test
    fun everyCityBuildsNonEmptyRouteForAnyPreference() {
        CityRepository.getAllCities().forEach { city ->
            budgets.forEach { minutes ->
                val route = RoutePlanner.build(
                    startLat = city.latitude,
                    startLng = city.longitude,
                    places = PlaceRepository.getPlacesForCity(city.id),
                    preferences = RoutePreferences(minutes, emptySet(), IndoorPreference.ANY)
                )
                assertTrue(
                    "Expected non-empty route for ${city.name} with $minutes min",
                    route.stops.isNotEmpty()
                )
                assertTrue(route.totalMinutes <= minutes)
            }
        }
    }

    @Test
    fun everyCityHasIndoorAndOutdoorOptions() {
        CityRepository.getAllCities().forEach { city ->
            listOf(IndoorPreference.INDOOR, IndoorPreference.OUTDOOR).forEach { pref ->
                val route = RoutePlanner.build(
                    startLat = city.latitude,
                    startLng = city.longitude,
                    places = PlaceRepository.getPlacesForCity(city.id),
                    preferences = RoutePreferences(240, emptySet(), pref)
                )
                assertTrue(
                    "Expected non-empty $pref route for ${city.name}",
                    route.stops.isNotEmpty()
                )
            }
        }
    }
}
