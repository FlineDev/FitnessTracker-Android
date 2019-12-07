package com.flinesoft.fitnesstracker.persistence

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.runner.AndroidJUnit4
import com.flinesoft.fitnesstracker.helpers.extensions.blockingValue
import com.flinesoft.fitnesstracker.model.Impediment
import org.joda.time.DateTime
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ImpedimentTest: PersistenceTest() {
    @Rule @JvmField
    public var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    @Throws(Exception::class)
    fun runBasicCRUDOperations() {
        val allImpedimentsOrderedByStartDate = database.impedimentDao.allOrderedByStartDate()
        assertNotNull(allImpedimentsOrderedByStartDate.blockingValue())
        assert(allImpedimentsOrderedByStartDate.blockingValue()!!.isEmpty())

        val impediment = Impediment(Impediment.Type.SEVERE_COLD, DateTime(2019, 12, 18, 15, 20))
        database.impedimentDao.insert(impediment)
        assertEquals(1, allImpedimentsOrderedByStartDate.blockingValue()!!.size)

        val nowDateTime = DateTime.now()
        impediment.startDate = nowDateTime
        database.impedimentDao.update(impediment)
        assertEquals(nowDateTime, allImpedimentsOrderedByStartDate.blockingValue()!!.first().startDate.hourOfDay())

        database.impedimentDao.delete(impediment)
        assert(allImpedimentsOrderedByStartDate.blockingValue()!!.isEmpty())
    }
}