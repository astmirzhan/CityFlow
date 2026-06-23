package com.astmirzhan.cityflow.model

enum class IndoorPreference { INDOOR, OUTDOOR, ANY }

data class RoutePreferences(
    val availableMinutes: Int,
    val categories: Set<PlaceCategory>,
    val indoorPreference: IndoorPreference
)
