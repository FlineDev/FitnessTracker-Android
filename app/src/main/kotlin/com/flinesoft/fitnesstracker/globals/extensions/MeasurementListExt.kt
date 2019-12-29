package com.flinesoft.fitnesstracker.globals.extensions

import com.flinesoft.fitnesstracker.model.Measurement

/**
 * Returns a list of measurement entries with multiple entries on the same day reduced to the one with the lowest `value`.
 * NOTE: Requires the list being called on to be sorted by `measureDate` already.
 */
fun <T : Measurement> List<T>.reduceToLowestValuePerDay(): List<T> {
    if (isEmpty()) return this

    var filteredList = mutableListOf(first())
    var lastAddedMeasurement = first()

    for (measurement in this.drop(1)) {
        if (measurement.measureDate.withTimeAtStartOfDay() > lastAddedMeasurement.measureDate) {
            filteredList.add(measurement)
            lastAddedMeasurement = measurement
        } else if (measurement.value < lastAddedMeasurement.value) {
            filteredList[filteredList.lastIndex] = measurement
            lastAddedMeasurement = measurement
        }
    }

    return filteredList
}

/**
 * Returns a list of measurement entries with multiple entries on the same day reduced to the one with the latest `measureDate`.
 * NOTE: Requires the list being called on to be sorted by `measureDate` already.
 */
fun <T : Measurement> List<T>.reduceToLatestMeasureDatePerDay(): List<T> {
    if (isEmpty()) return this

    var filteredList = mutableListOf(first())
    var lastAddedMeasurement = first()

    for (measurement in this.drop(1)) {
        if (measurement.measureDate.withTimeAtStartOfDay() > lastAddedMeasurement.measureDate) {
            filteredList.add(measurement)
            lastAddedMeasurement = measurement
        } else {
            filteredList[filteredList.lastIndex] = measurement
            lastAddedMeasurement = measurement
        }
    }

    return filteredList
}
