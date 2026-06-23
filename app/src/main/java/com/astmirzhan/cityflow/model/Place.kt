package com.astmirzhan.cityflow.model

data class Place(
    val id: String,
    val name: String,
    val category: PlaceCategory,
    val latitude: Double,
    val longitude: Double,
    val description: String,
    val indoor: Boolean,
    val visitMinutes: Int
)
