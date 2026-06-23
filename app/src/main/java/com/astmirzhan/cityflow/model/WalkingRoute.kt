package com.astmirzhan.cityflow.model

data class WalkingRoute(
    val startLatitude: Double,
    val startLongitude: Double,
    val stops: List<RouteStop>,
    val totalDistanceMeters: Double,
    val totalWalkMinutes: Int,
    val totalVisitMinutes: Int
) {
    val totalMinutes: Int get() = totalWalkMinutes + totalVisitMinutes
    val isEmpty: Boolean get() = stops.isEmpty()
}
