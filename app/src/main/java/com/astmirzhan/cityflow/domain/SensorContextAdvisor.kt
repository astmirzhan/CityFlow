package com.astmirzhan.cityflow.domain

import com.astmirzhan.cityflow.model.IndoorPreference
import kotlin.math.sqrt

object SensorContextAdvisor {

    const val INDOOR_LUX_THRESHOLD = 200f
    const val OUTDOOR_LUX_THRESHOLD = 1000f

    private const val GRAVITY = 9.81f
    const val WALKING_THRESHOLD = 1.2f
    const val SHAKE_THRESHOLD = 18f

    enum class MotionState { STOPPED, WALKING, MOVING }

    // Suggest a refined indoor/outdoor preference from ambient light.
    fun refinePreference(current: IndoorPreference, lux: Float): IndoorPreference {
        if (current != IndoorPreference.ANY) return current
        return when {
            lux <= INDOOR_LUX_THRESHOLD -> IndoorPreference.INDOOR
            lux >= OUTDOOR_LUX_THRESHOLD -> IndoorPreference.OUTDOOR
            else -> IndoorPreference.ANY
        }
    }

    fun lightLabel(lux: Float): String = when {
        lux <= INDOOR_LUX_THRESHOLD -> "Indoor lighting"
        lux >= OUTDOOR_LUX_THRESHOLD -> "Outdoor daylight"
        else -> "Mixed lighting"
    }

    // Linear acceleration magnitude with gravity removed.
    fun linearMagnitude(x: Float, y: Float, z: Float): Float {
        val magnitude = sqrt(x * x + y * y + z * z)
        return kotlin.math.abs(magnitude - GRAVITY)
    }

    fun motionState(linearMagnitude: Float): MotionState = when {
        linearMagnitude < WALKING_THRESHOLD -> MotionState.STOPPED
        linearMagnitude < SHAKE_THRESHOLD -> MotionState.WALKING
        else -> MotionState.MOVING
    }

    fun isShake(linearMagnitude: Float): Boolean = linearMagnitude >= SHAKE_THRESHOLD
}
