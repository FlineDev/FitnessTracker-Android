package com.flinesoft.fitnesstracker.calculation

import com.flinesoft.fitnesstracker.globals.extensions.minusKt
import com.flinesoft.fitnesstracker.globals.extensions.plusKt
import org.joda.time.DateTime
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import kotlin.time.ExperimentalTime
import kotlin.time.days

@ExperimentalTime
class MovingAverageCalculatorTest {
    @Test
    fun calculateMovingAverageAt() {
        // TODO: [2020-02-24] not yet implemented
    }

    @Test
    fun weightAt() {
        val fromDate = DateTime.now().minusDays(100)
        val toDate = DateTime.now()

        val minWeight = MovingAverageCalculator.weightAt(
            date = fromDate,
            fromDate = fromDate,
            toDate = toDate,
            maxWeightFactor = 10.0
        )
        assertEquals(1.0, minWeight, 0.001)

        val thirdWeight = MovingAverageCalculator.weightAt(
            date = DateTime.now().minusKt(66.666.days),
            fromDate = fromDate,
            toDate = toDate,
            maxWeightFactor = 10.0
        )
        assertEquals(4.0, thirdWeight, 0.001)

        val halfWeight = MovingAverageCalculator.weightAt(
            date = DateTime.now().minusDays(50),
            fromDate = fromDate,
            toDate = toDate,
            maxWeightFactor = 10.0
        )
        assertEquals(5.5, halfWeight, 0.001)

        val maxWeight = MovingAverageCalculator.weightAt(
            date = toDate,
            fromDate = fromDate,
            toDate = toDate,
            maxWeightFactor = 5.0
        )
        assertEquals(5.0, maxWeight, 0.001)
    }

    @Test
    fun filteredDataEntries() {
        val toDate = DateTime.now().minusYears(1)
        val weightStepDuration = 1.days

        val dataEntries: List<MovingAverageCalculator.DataEntry> = listOf(
            MovingAverageCalculator.DataEntry(
                measureDate = toDate.minusKt(4.25.days),
                value = 100.0
            ),
            MovingAverageCalculator.DataEntry(
                measureDate = toDate.minusKt(3.5.days),
                value = 190.0
            ),
            MovingAverageCalculator.DataEntry(
                measureDate = toDate.minusKt(2.days),
                value = 250.0
            ),
            MovingAverageCalculator.DataEntry(
                measureDate = toDate.minusKt(0.5.days),
                value = 300.0
            ),
            MovingAverageCalculator.DataEntry(
                measureDate = toDate.plusKt(0.25.days),
                value = 330.0
            )
        )

        val filteredDataEntries = MovingAverageCalculator.filteredDataEntries(
            fromDate = toDate.minusKt(weightStepDuration * 4),
            toDate = toDate,
            dataEntries = dataEntries,
            minDataEntriesCount = 3
        )

        assertNotNull(filteredDataEntries)

        assertEquals(
            listOf(
                toDate.minusKt(weightStepDuration * 4),
                toDate.minusKt(weightStepDuration * 3.5),
                toDate.minusKt(weightStepDuration * 2),
                toDate.minusKt(weightStepDuration * 0.5),
                toDate
            ),
            filteredDataEntries!!.map { it.measureDate }
        )

        assertEquals(listOf(130.0, 190.0, 250.0, 300.0, 320.0), filteredDataEntries.map { it.value })
    }
}
