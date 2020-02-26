package com.flinesoft.fitnesstracker.calculation

import org.junit.Assert.assertEquals
import org.junit.Test

// Calculated expected values here: https://jumk.de/bmi/absi.php
class BodyMassIndexCalculatorTest {
    @Test
    fun calculateIndex() {
        assertEquals(
            18.4,
            BodyMassIndexCalculator.calculateIndex(50.0, 1.65),
            0.05
        )
        assertEquals(
            26.1,
            BodyMassIndexCalculator.calculateIndex(80.0, 1.75),
            0.05
        )
        assertEquals(
            32.4,
            BodyMassIndexCalculator.calculateIndex(105.0, 1.80),
            0.05
        )
    }
}
