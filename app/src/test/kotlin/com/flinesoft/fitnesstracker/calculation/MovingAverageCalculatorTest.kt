package com.flinesoft.fitnesstracker.calculation

import com.flinesoft.fitnesstracker.globals.extensions.minusKt
import com.flinesoft.fitnesstracker.globals.extensions.plusKt
import junit.framework.Assert.assertNull
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
        val date = DateTime.now().minusYears(1)
        val dataEntries: List<MovingAverageCalculator.DataEntry> = listOf(
            MovingAverageCalculator.DataEntry(measureDate = date.minusDays(20), value = 100.0),
            MovingAverageCalculator.DataEntry(measureDate = date.minusDays(10), value = 200.0),
            MovingAverageCalculator.DataEntry(measureDate = date.minusDays(5), value = 150.0),
            MovingAverageCalculator.DataEntry(measureDate = date.minusDays(1), value = 175.0),
            MovingAverageCalculator.DataEntry(measureDate = date.plusDays(4), value = 200.0)
        )

        // Expected: (1 * 160 + 5 * 200 + 10 * 150 + 14 * 175 + 15 * 180) / (1 + 5 + 10 + 14 + 15) = 173.555

        val movingAverage = MovingAverageCalculator.calculateMovingAverageAt(
            date = date,
            timeIntervalToConsider = 14.days,
            dataEntries = dataEntries,
            minDataEntriesCount = 3,
            maxWeightFactor = 15.0
        )
        assertNotNull(movingAverage)
        assertEquals(movingAverage!!, 173.555, 0.01)

        val tooFewEntriesMovingAverage = MovingAverageCalculator.calculateMovingAverageAt(
            date = date,
            timeIntervalToConsider = 14.days,
            dataEntries = dataEntries,
            minDataEntriesCount = 4,
            maxWeightFactor = 15.0
        )
        assertNull(tooFewEntriesMovingAverage)
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
        assertEquals(1.0, minWeight, 0.01)

        val thirdWeight = MovingAverageCalculator.weightAt(
            date = DateTime.now().minusKt(66.666.days),
            fromDate = fromDate,
            toDate = toDate,
            maxWeightFactor = 10.0
        )
        assertEquals(4.0, thirdWeight, 0.01)

        val halfWeight = MovingAverageCalculator.weightAt(
            date = DateTime.now().minusDays(50),
            fromDate = fromDate,
            toDate = toDate,
            maxWeightFactor = 10.0
        )
        assertEquals(5.5, halfWeight, 0.01)

        val maxWeight = MovingAverageCalculator.weightAt(
            date = toDate,
            fromDate = fromDate,
            toDate = toDate,
            maxWeightFactor = 5.0
        )
        assertEquals(5.0, maxWeight, 0.01)
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
