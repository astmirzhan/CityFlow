package com.astmirzhan.cityflow.domain

import com.astmirzhan.cityflow.model.IndoorPreference
import com.astmirzhan.cityflow.model.Place
import com.astmirzhan.cityflow.model.RoutePreferences
import com.astmirzhan.cityflow.model.RouteStop
import com.astmirzhan.cityflow.model.WalkingRoute
import kotlin.math.roundToInt
import kotlin.random.Random

object RoutePlanner {

    const val WALKING_SPEED_KMH = 5.0
    private const val METERS_PER_MINUTE = WALKING_SPEED_KMH * 1000.0 / 60.0

    fun walkMinutes(distanceMeters: Double): Int =
        (distanceMeters / METERS_PER_MINUTE).roundToInt()

    // Greedy nearest-neighbour route that fits inside the available time budget.
    fun build(
        startLat: Double,
        startLng: Double,
        places: List<Place>,
        preferences: RoutePreferences,
        firstStopChoices: Int = 1,
        random: Random = Random.Default
    ): WalkingRoute {
        val candidates = places
            .filter { preferences.categories.isEmpty() || it.category in preferences.categories }
            .filter { matchesIndoorPreference(it, preferences.indoorPreference) }
            .toMutableList()

        val stops = mutableListOf<RouteStop>()
        var currentLat = startLat
        var currentLng = startLng
        var usedMinutes = 0
        var totalDistance = 0.0
        var totalWalk = 0
        var totalVisit = 0
        var order = 1

        while (candidates.isNotEmpty()) {
            val next = if (order == 1 && firstStopChoices > 1) {
                candidates
                    .sortedBy { DistanceCalculator.haversineMeters(currentLat, currentLng, it.latitude, it.longitude) }
                    .take(firstStopChoices.coerceAtMost(candidates.size))
                    .random(random)
            } else {
                candidates.minByOrNull {
                    DistanceCalculator.haversineMeters(currentLat, currentLng, it.latitude, it.longitude)
                }
            } ?: break

            val distance = DistanceCalculator.haversineMeters(
                currentLat, currentLng, next.latitude, next.longitude
            )
            val walk = walkMinutes(distance)
            val cost = walk + next.visitMinutes

            if (usedMinutes + cost > preferences.availableMinutes) {
                candidates.remove(next)
                continue
            }

            stops.add(RouteStop(order++, next, distance, walk))
            usedMinutes += cost
            totalDistance += distance
            totalWalk += walk
            totalVisit += next.visitMinutes
            currentLat = next.latitude
            currentLng = next.longitude
            candidates.remove(next)
        }

        return WalkingRoute(
            startLatitude = startLat,
            startLongitude = startLng,
            stops = stops,
            totalDistanceMeters = totalDistance,
            totalWalkMinutes = totalWalk,
            totalVisitMinutes = totalVisit
        )
    }

    private fun matchesIndoorPreference(place: Place, preference: IndoorPreference): Boolean =
        when (preference) {
            IndoorPreference.ANY -> true
            IndoorPreference.INDOOR -> place.indoor
            IndoorPreference.OUTDOOR -> !place.indoor
        }
}
