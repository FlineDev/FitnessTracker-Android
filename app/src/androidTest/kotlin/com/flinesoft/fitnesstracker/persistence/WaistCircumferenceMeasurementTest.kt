package com.flinesoft.fitnesstracker.persistence

import androidx.test.runner.AndroidJUnit4
import com.flinesoft.fitnesstracker.helpers.extensions.awaitValue
import com.flinesoft.fitnesstracker.model.WaistCircumferenceMeasurement
import kotlinx.coroutines.runBlocking
import org.joda.time.DateTime
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.time.ExperimentalTime

@ExperimentalTime
@RunWith(AndroidJUnit4::class)
class WaistCircumferenceMeasurementTest : PersistenceTest() {
    @Test
    @Throws(Exception::class)
    fun runBasicCRUDOperations() = runBlocking {
        val allMeasurementsSortedByMeasureDate = database.waistCircumferenceMeasurementDao.allOrderedByMeasureDate()
        assertNotNull(allMeasurementsSortedByMeasureDate.awaitValue())
        assert(allMeasurementsSortedByMeasureDate.awaitValue()!!.isEmpty())

        val measurement = database.waistCircumferenceMeasurementDao.create(
            WaistCircumferenceMeasurement(100.0, DateTime(2019, 12, 18, 15, 20))
        ).awaitValue()!!
        assertEquals(measurement.id, allMeasurementsSortedByMeasureDate.awaitValue()!!.first().id)
        assertEquals(1, allMeasurementsSortedByMeasureDate.awaitValue()!!.size)

        val newCircumferenceInCentimeters = 95.0
        measurement.circumferenceInCentimeters = newCircumferenceInCentimeters
        database.waistCircumferenceMeasurementDao.update(measurement)
        assertEquals(newCircumferenceInCentimeters, allMeasurementsSortedByMeasureDate.awaitValue()!!.first().circumferenceInCentimeters, 0.01)

        database.waistCircumferenceMeasurementDao.delete(measurement)
        assert(allMeasurementsSortedByMeasureDate.awaitValue()!!.isEmpty())
    }
}
