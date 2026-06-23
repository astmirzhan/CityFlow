package com.astmirzhan.cityflow.model

data class RouteHistoryItem(
    val id: String,
    val createdAt: Long,
    val title: String,
    val stopCount: Int,
    val totalDistanceMeters: Double,
    val totalMinutes: Int,
    val placeNames: List<String>
)
