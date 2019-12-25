package com.flinesoft.fitnesstracker.persistence

import androidx.test.runner.AndroidJUnit4
import com.flinesoft.fitnesstracker.helpers.extensions.awaitValue
import com.flinesoft.fitnesstracker.model.WeightMeasurement
import kotlinx.coroutines.runBlocking
import org.joda.time.DateTime
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class WeightMeasurementTest: PersistenceTest() {
    @Test
    @Throws(Exception::class)
    fun runBasicCRUDOperations() = runBlocking {
        val allMeasurementsSortedByMeasureDate = database.weightMeasurementDao.allOrderedByMeasureDate()
        assertNotNull(allMeasurementsSortedByMeasureDate.awaitValue())
        assert(allMeasurementsSortedByMeasureDate.awaitValue()!!.isEmpty())

        val measurement = database.weightMeasurementDao.create(
            WeightMeasurement(80.0, DateTime(2019, 12, 18, 15, 20))
        ).awaitValue()!!
        assertEquals(measurement.id, allMeasurementsSortedByMeasureDate.awaitValue()!!.first().id)
        assertEquals(1, allMeasurementsSortedByMeasureDate.awaitValue()!!.size)

        val newWeightInKilograms = 75.5
        measurement.weightInKilograms = newWeightInKilograms
        database.weightMeasurementDao.update(measurement)
        assertEquals(newWeightInKilograms, allMeasurementsSortedByMeasureDate.awaitValue()!!.first().weightInKilograms, 0.01)

        database.weightMeasurementDao.delete(measurement)
        assert(allMeasurementsSortedByMeasureDate.awaitValue()!!.isEmpty())
    }
}
