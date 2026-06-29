package com.astmirzhan.cityflow

import com.astmirzhan.cityflow.data.CityRepository
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CityRepositoryTest {

    @Test
    fun containsAllCuratedCities() {
        val names = CityRepository.getAllCities().map { it.name }
        assertTrue(names.contains("Schmalkalden"))
        assertTrue(names.contains("Astana"))
        assertTrue(names.contains("Almaty"))
    }

    @Test
    fun countriesContainGermanyAndKazakhstan() {
        val countries = CityRepository.getCountries()
        assertTrue(countries.contains("Germany"))
        assertTrue(countries.contains("Kazakhstan"))
    }

    @Test
    fun kazakhstanCitiesAreAstanaAndAlmaty() {
        val names = CityRepository.getCitiesByCountry("Kazakhstan").map { it.name }
        assertTrue(names.contains("Astana"))
        assertTrue(names.contains("Almaty"))
    }

    @Test
    fun getCityByIdReturnsKnownCity() {
        assertNotNull(CityRepository.getCityById("schmalkalden"))
    }

    @Test
    fun getCityByIdReturnsNullForUnknown() {
        assertNull(CityRepository.getCityById("unknown"))
    }
}
