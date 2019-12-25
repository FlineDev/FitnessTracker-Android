package com.flinesoft.fitnesstracker.persistence

import androidx.test.runner.AndroidJUnit4
import com.flinesoft.fitnesstracker.helpers.extensions.awaitValue
import com.flinesoft.fitnesstracker.model.Impediment
import kotlinx.coroutines.runBlocking
import org.joda.time.DateTime
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ImpedimentTest: PersistenceTest() {
    @Test
    @Throws(Exception::class)
    fun runBasicCRUDOperations() = runBlocking {
        val allImpedimentsOrderedByStartDate = database.impedimentDao.allOrderedByStartDate()
        assertNotNull(allImpedimentsOrderedByStartDate.awaitValue())
        assert(allImpedimentsOrderedByStartDate.awaitValue()!!.isEmpty())

        val impediment = database.impedimentDao.create(
            Impediment(Impediment.Type.SEVERE_COLD, DateTime(2019, 12, 18, 15, 20))
        ).awaitValue()!!
        assertEquals(impediment.id, allImpedimentsOrderedByStartDate.awaitValue()!!.first().id)
        assertEquals(1, allImpedimentsOrderedByStartDate.awaitValue()!!.size)

        val nowDateTime = DateTime.now()
        impediment.startDate = nowDateTime
        database.impedimentDao.update(impediment)
        assertEquals(nowDateTime.hourOfDay, allImpedimentsOrderedByStartDate.awaitValue()!!.first().startDate.hourOfDay)

        database.impedimentDao.delete(impediment)
        assert(allImpedimentsOrderedByStartDate.awaitValue()!!.isEmpty())
    }
}