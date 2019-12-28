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
    private fun meanIndex(ageInYears: Int, gender: Gender): Double {
        return 0.07922 // TODO: add data tables from here: https://www.mytecbits.com/tools/medical/absi-calculator
    }

    // Source: https://www.mytecbits.com/tools/medical/absi-calculator
    private fun standardDeviation(ageInYears: Int, gender: Gender): Double {
        return 0.00374 // TODO: add data tables from here: https://www.mytecbits.com/tools/medical/absi-calculator
    }
}
