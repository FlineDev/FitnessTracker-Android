package com.flinesoft.fitnesstracker.calculation

import com.flinesoft.fitnesstracker.globals.MOVING_AVERAGE_MIN_DATA_ENTRIES
import com.flinesoft.fitnesstracker.globals.extensions.durationSince
import com.flinesoft.fitnesstracker.globals.extensions.minusKt
import org.joda.time.DateTime
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

// Source: https://en.wikipedia.org/wiki/Moving_average
@ExperimentalTime
object MovingAverageCalculator {
    data class DataEntry(val measureDate: DateTime, val value: Double)

    /** Calculates the moving average for a given sorted list of time series data. Newer entries are weighted higher. Returns `null` if not enough data. */
    fun calculateMovingAverageAt(date: DateTime, timeIntervalToConsider: Duration, dataEntries: List<DataEntry>): Double? {
        val filteredDataEntries = filteredDataEntries(
            fromDate = date.minusKt(timeIntervalToConsider),
            toDate = date,
            dataEntries = dataEntries
        ) ?: return null

        // TODO: [2020-02-23] calculate weighted moving averages – the weighting should be done using the time difference to the requested data
        // TODO: [2020-02-23] start at 0 (0.1?) for (date - timeIntervalToConsider) and go linearly up to 1 for the projected end value at exactly date

        return null // TODO: [2020-02-23] not yet implemented
    }

    /** Filters all data entries outside of the interval and makes sure there are projected data entries for the edges of the interval. */
    fun filteredDataEntries(fromDate: DateTime, toDate: DateTime, dataEntries: List<DataEntry>): List<DataEntry>? {
        val firstEntryAfterDate = dataEntries.firstOrNull { it.measureDate.isAfter(toDate) } ?: return null
        val lastEntryBeforeInterval = dataEntries.lastOrNull { it.measureDate.isBefore(fromDate) }

        val dataEntriesInInterval = dataEntries
            .dropWhile { it.measureDate.isBefore(fromDate) }
            .dropLastWhile { it.measureDate.isAfter(toDate) }
            .toMutableList()

        if (dataEntriesInInterval.size < MOVING_AVERAGE_MIN_DATA_ENTRIES) return null

        val projectedFirstDataEntry = lastEntryBeforeInterval?.run {
            val firstEntry = dataEntriesInInterval.first()
            DataEntry(
                measureDate = fromDate,
                value = (value * fromDate.durationSince(measureDate).inSeconds + firstEntry.value * firstEntry.measureDate.durationSince(fromDate).inSeconds) /
                            firstEntry.measureDate.durationSince(measureDate).inSeconds
            )
        }
        projectedFirstDataEntry?.let { dataEntriesInInterval.add(0, it) }

        val projectedLastDataEntry = firstEntryAfterDate.run {
            val lastEntry = dataEntriesInInterval.last()
            DataEntry(
                measureDate = toDate,
                value = (lastEntry.value * toDate.durationSince(lastEntry.measureDate).inSeconds + value * measureDate.durationSince(toDate).inSeconds) /
                            measureDate.durationSince(lastEntry.measureDate).inSeconds
            )
        }
        dataEntriesInInterval.add(projectedLastDataEntry)

        return dataEntriesInInterval
    }
}
