package com.flinesoft.fitnesstracker.globals.extensions

import com.flinesoft.fitnesstracker.model.WeightMeasurement
import org.joda.time.DateTime
import org.junit.Assert.assertEquals
import org.junit.Test

class MeasurementListExtTest {
    @Test
    fun reduceToLowestValuePerDay() {
        val measurements = listOf(
            WeightMeasurement(50.0, DateTime.now().minusDays(1)),
            WeightMeasurement(52.5, DateTime.now().minusDays(1).minusMinutes(2)),
            WeightMeasurement(53.0, DateTime.now().minusMinutes(5)),
            WeightMeasurement(51.6, DateTime.now().minusMinutes(3)),
            WeightMeasurement(52.4, DateTime.now())
        )

        assertEquals(listOf(50.0, 51.6), measurements.reduceToLowestValuePerDay().map { it.value })
    }

    @Test
    fun reduceToLatestMeasureDatePerDay() {
        val measurements = listOf(
            WeightMeasurement(50.0, DateTime.now().minusDays(1)),
            WeightMeasurement(52.5, DateTime.now().minusDays(1).minusMinutes(2)),
            WeightMeasurement(53.0, DateTime.now().minusMinutes(5)),
            WeightMeasurement(51.6, DateTime.now().minusMinutes(3)),
            WeightMeasurement(52.4, DateTime.now())
        )

        assertEquals(listOf(52.5, 52.4), measurements.reduceToLatestMeasureDatePerDay().map { it.value })
    }
}
