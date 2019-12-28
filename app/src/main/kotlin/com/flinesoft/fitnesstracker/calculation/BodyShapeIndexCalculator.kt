package com.flinesoft.fitnesstracker.calculation

import kotlin.math.pow
import kotlin.math.sqrt

object BodyShapeIndexCalculator {
    enum class Gender { FEMALE, MALE }

    // Source: https://en.wikipedia.org/wiki/Body_Shape_Index
    fun calculateIndex(weightInKilograms: Double, heightInMeters: Double, waistCircumferenceInMeters: Double): Double {
        val bodyMassIndex = BodyMassIndexCalculator.calculateIndex(weightInKilograms, heightInMeters)
        return waistCircumferenceInMeters / (bodyMassIndex.pow(2.0 / 3.0) * sqrt(heightInMeters))
    }

    // Source: https://www.mytecbits.com/tools/medical/absi-calculator
    fun calculateZIndex(weightInKilograms: Double, heightInMeters: Double, waistCircumferenceInMeters: Double, ageInYears: Int, gender: Gender): Double {
        val baseIndex = calculateIndex(weightInKilograms, heightInMeters, waistCircumferenceInMeters)
        return (baseIndex - meanIndex(ageInYears, gender)) / standardDeviation(ageInYears, gender)
    }

    // Source: https://www.mytecbits.com/tools/medical/absi-calculator
    private fun meanIndex(ageInYears: Int, gender: Gender): Double = when (gender) {
        Gender.MALE ->
            when (ageInYears) {
                in 0..4 -> 0.0792
                in 5..8 -> 0.0796
                in 9..18 -> 0.08185 - 0.00027 * ageInYears
                else -> 0.0742 + 0.00016 * ageInYears
            }

        Gender.FEMALE ->
            when (ageInYears) {
                in 0..25 -> 0.0803 - 0.00012 * ageInYears
                in 26..30 -> 0.0774
                in 31..35 -> 0.0776
                else -> 0.0731 + 0.000138 * ageInYears
            }
    }

    // Source: https://www.mytecbits.com/tools/medical/absi-calculator
    private fun standardDeviation(ageInYears: Int, gender: Gender): Double {
        return 0.00374 // TODO: add data tables from here: https://www.mytecbits.com/tools/medical/absi-calculator
    }
}
