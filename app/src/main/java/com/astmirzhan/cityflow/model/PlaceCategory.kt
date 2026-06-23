package com.astmirzhan.cityflow.model

enum class PlaceCategory(val label: String) {
    OLD_TOWN("Old Town"),
    CAMPUS("University"),
    CAFE("Cafe"),
    PARK("Park"),
    MUSEUM("Museum"),
    VIEWPOINT("Viewpoint"),
    LIBRARY("Library"),
    STATION("Station"),
    SUPERMARKET("Supermarket"),
    HISTORIC_SQUARE("Historic Square");

    companion object {
        fun fromName(value: String): PlaceCategory? = entries.firstOrNull { it.name == value }
    }
}
