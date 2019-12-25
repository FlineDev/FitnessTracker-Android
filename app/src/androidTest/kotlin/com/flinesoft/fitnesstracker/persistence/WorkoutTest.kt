package com.flinesoft.fitnesstracker.persistence

import androidx.test.runner.AndroidJUnit4
import com.flinesoft.fitnesstracker.helpers.extensions.awaitValue
import com.flinesoft.fitnesstracker.model.Workout
import kotlinx.coroutines.runBlocking
import org.joda.time.DateTime
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class WorkoutTest: PersistenceTest() {
    @Test
    @Throws(Exception::class)
    fun runBasicCRUDOperations() = runBlocking {
        val allWorkoutsOrderedByStartDate = database.workoutDao.allOrderedByStartDate()
        assertNotNull(allWorkoutsOrderedByStartDate.awaitValue())
        assert(allWorkoutsOrderedByStartDate.awaitValue()!!.isEmpty())

        val workout = database.workoutDao.create(
            Workout(
                Workout.Type.CARDIO,
                DateTime(2019, 12, 18, 15, 20),
                DateTime(2019, 12, 18, 16, 50)
            )
        ).awaitValue()!!
        assertEquals(workout.id, allWorkoutsOrderedByStartDate.awaitValue()!!.first().id)
        assertEquals(1, allWorkoutsOrderedByStartDate.awaitValue()!!.size)

        val newType = Workout.Type.MUSCLE_BUILDING
        workout.type = newType
        database.workoutDao.update(workout)
        assertEquals(newType, allWorkoutsOrderedByStartDate.awaitValue()!!.first().type)

        database.workoutDao.delete(workout)
        assert(allWorkoutsOrderedByStartDate.awaitValue()!!.isEmpty())
    }
}
