package com.flinesoft.fitnesstracker.calculation

import com.flinesoft.fitnesstracker.globals.MOVING_AVERAGE_WEIGHT_STEP_DURATION
import com.flinesoft.fitnesstracker.globals.extensions.minusKt
import com.flinesoft.fitnesstracker.globals.extensions.plusKt
import org.joda.time.DateTime
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class MovingAverageCalculatorTest {
    @Test
    fun calculateMovingAverageAt() {
        // TODO: [2020-02-24] not yet implemented
    }

    @Test
    fun filteredDataEntries() {
        val toDate = DateTime.now().minusYears(1)
        val dataEntries: List<MovingAverageCalculator.DataEntry> = listOf(
            MovingAverageCalculator.DataEntry(
                measureDate = toDate.minusKt(MOVING_AVERAGE_WEIGHT_STEP_DURATION * 4.25), // TODO: [2020-02-24] Coninue here by fixing the tests
                value = 100.0
            ),
            MovingAverageCalculator.DataEntry(
                measureDate = toDate.minusKt(MOVING_AVERAGE_WEIGHT_STEP_DURATION * 3.5),
                value = 190.0
            ),
            MovingAverageCalculator.DataEntry(
                measureDate = toDate.minusKt(MOVING_AVERAGE_WEIGHT_STEP_DURATION * 2),
                value = 250.0
            ),
            MovingAverageCalculator.DataEntry(
                measureDate = toDate.minusKt(MOVING_AVERAGE_WEIGHT_STEP_DURATION * 0.5),
                value = 300.0
            ),
            MovingAverageCalculator.DataEntry(
                measureDate = toDate.plusKt(MOVING_AVERAGE_WEIGHT_STEP_DURATION * 0.25),
                value = 330.0
            )
        )

        val filteredDataEntries = MovingAverageCalculator.filteredDataEntries(
            fromDate = toDate.minusKt(MOVING_AVERAGE_WEIGHT_STEP_DURATION * 4),
            toDate = toDate,
            dataEntries = dataEntries
        )

        assertNotNull(filteredDataEntries)

        assertEquals(
            listOf(
                toDate.minusKt(MOVING_AVERAGE_WEIGHT_STEP_DURATION * 4),
                toDate.minusKt(MOVING_AVERAGE_WEIGHT_STEP_DURATION * 3.5),
                toDate.minusKt(MOVING_AVERAGE_WEIGHT_STEP_DURATION * 2),
                toDate.minusKt(MOVING_AVERAGE_WEIGHT_STEP_DURATION * 0.5),
                toDate
            ),
            filteredDataEntries!!.map { it.measureDate }
        )

        assertEquals(listOf(130.0, 190.0, 250.0, 300.0, 320.0), filteredDataEntries.map { it.value })
    }
}
