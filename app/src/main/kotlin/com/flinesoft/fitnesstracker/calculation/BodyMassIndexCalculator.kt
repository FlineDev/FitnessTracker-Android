package com.flinesoft.fitnesstracker.calculation

object BodyMassIndexCalculator {
    // Source: https://en.wikipedia.org/wiki/Body_mass_index
    fun calculateIndex(weightInKilograms: Double, heightInMeters: Double): Double {
        return weightInKilograms / (heightInMeters * heightInMeters)
    }
}
