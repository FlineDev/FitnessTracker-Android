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
import kotlin.time.ExperimentalTime

@ExperimentalTime
@RunWith(AndroidJUnit4::class)
class WorkoutTest: PersistenceTest() {
    @Test
    @Throws(Exception::class)
    fun runBasicCRUDOperations() = runBlocking {
        val latestWorkouts = database.workoutDao.allOrderedByEndDateDescending()
        assertNotNull(latestWorkouts.awaitValue())
        assert(latestWorkouts.awaitValue()!!.isEmpty())

        val workout = database.workoutDao.create(
            Workout(
                Workout.Type.CARDIO,
                DateTime(2019, 12, 18, 15, 20),
                DateTime(2019, 12, 18, 16, 50)
            )
        ).awaitValue()!!
        assertEquals(workout.id, latestWorkouts.awaitValue()!!.first().id)
        assertEquals(1, latestWorkouts.awaitValue()!!.size)

        val newType = Workout.Type.MUSCLE_BUILDING
        workout.type = newType
        database.workoutDao.update(workout)
        assertEquals(newType, latestWorkouts.awaitValue()!!.first().type)

        database.workoutDao.delete(workout)
        assert(latestWorkouts.awaitValue()!!.isEmpty())
    }
}
