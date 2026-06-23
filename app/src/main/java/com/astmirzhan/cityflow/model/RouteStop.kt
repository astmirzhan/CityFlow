package com.astmirzhan.cityflow.model

data class RouteStop(
    val order: Int,
    val place: Place,
    val distanceFromPreviousMeters: Double,
    val walkMinutesFromPrevious: Int
)
