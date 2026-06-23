package com.astmirzhan.cityflow

import com.astmirzhan.cityflow.domain.DistanceCalculator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class DistanceCalculatorTest {

    @Test
    fun samePointIsZero() {
        val d = DistanceCalculator.haversineMeters(50.72, 10.45, 50.72, 10.45)
        assertEquals(0.0, d, 0.001)
    }

    @Test
    fun knownDistanceApproximatelyCorrect() {
        // Roughly 1 degree of latitude ~ 111 km.
        val d = DistanceCalculator.haversineMeters(50.0, 10.0, 51.0, 10.0)
        assertTrue("expected ~111km, was $d", d in 111_000.0..112_000.0)
    }

    @Test
    fun shortCityDistanceIsReasonable() {
        val d = DistanceCalculator.haversineMeters(50.7211, 10.4541, 50.7218, 10.4528)
        assertTrue("expected under 200m, was $d", d in 50.0..200.0)
    }
}
