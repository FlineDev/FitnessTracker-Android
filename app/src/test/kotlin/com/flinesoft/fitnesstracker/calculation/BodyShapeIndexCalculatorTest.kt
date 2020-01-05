package com.flinesoft.fitnesstracker.calculation

import com.flinesoft.fitnesstracker.model.Gender
import org.junit.Assert
import org.junit.Test

// Expected values calculated with: https://jumk.de/bmi/absi.php
class BodyShapeIndexCalculatorTest {
    @Test
    fun calculateIndex() {
        Assert.assertEquals(
            0.0615,
            BodyShapeIndexCalculator.calculateIndex(50.0, 1.65, 0.55),
            0.00005
        )
        Assert.assertEquals(
            0.0773,
            BodyShapeIndexCalculator.calculateIndex(80.0, 1.75, 0.90),
            0.00005
        )
        Assert.assertEquals(
            0.0880,
            BodyShapeIndexCalculator.calculateIndex(105.0, 1.80, 1.20),
            0.00005
        )
    }

    @Test
    fun calculateZIndex() {
        // TODO: add more age and gender variants
        Assert.assertEquals(
            -4.7350,
            BodyShapeIndexCalculator.calculateZIndex(50.0, 1.65, 0.55, ageInYears = 28, gender = Gender.MALE),
            0.25
        )
        Assert.assertEquals(
            -0.5196,
            BodyShapeIndexCalculator.calculateZIndex(80.0, 1.75, 0.90, ageInYears = 28, gender = Gender.MALE),
            0.25
        )
        Assert.assertEquals(
            2.3458,
            BodyShapeIndexCalculator.calculateZIndex(105.0, 1.80, 1.20, ageInYears = 28, gender = Gender.MALE),
            0.25
        )
    }
}
