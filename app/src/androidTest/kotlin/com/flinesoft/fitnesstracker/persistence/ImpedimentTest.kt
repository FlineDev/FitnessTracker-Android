package com.flinesoft.fitnesstracker.persistence

import androidx.test.runner.AndroidJUnit4
import com.flinesoft.fitnesstracker.model.Impediment
import org.joda.time.DateTime
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import java.lang.Exception

@RunWith(AndroidJUnit4::class)
class ImpedimentTest: PersistenceTest() {
    @Test
    @Throws(Exception::class)
    fun runBasicCRUDOperations() {
        val allImpedimentsOrderedByStartDate = database.impedimentDao.allOrderedByStartDate()
        assertNotNull(allImpedimentsOrderedByStartDate.value)
        assert(allImpedimentsOrderedByStartDate.value!!.isEmpty())

        var impediment = Impediment(Impediment.Type.SEVERE_COLD, DateTime(2019, 12, 18, 15, 20))
        database.impedimentDao.insert(impediment)
        assertEquals(1, allImpedimentsOrderedByStartDate.value!!.size)

        val nowDateTime = DateTime.now()
        impediment.startDate = nowDateTime
        database.impedimentDao.update(impediment)
        assertEquals(nowDateTime, allImpedimentsOrderedByStartDate.value!!.first().startDate.hourOfDay())

        database.impedimentDao.delete(impediment)
        assert(allImpedimentsOrderedByStartDate.value!!.isEmpty())
    }
}