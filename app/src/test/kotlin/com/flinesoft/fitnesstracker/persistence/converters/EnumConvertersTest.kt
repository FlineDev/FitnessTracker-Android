package com.flinesoft.fitnesstracker.persistence.converters

import com.flinesoft.fitnesstracker.model.Workout
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class EnumConvertersTest {
    @Test
    fun workoutTypeToString() {
        assertEquals("CARDIO", EnumConverters().workoutTypeToString(Workout.Type.CARDIO))
        assertEquals("MUSCLE_BUILDING", EnumConverters().workoutTypeToString(Workout.Type.MUSCLE_BUILDING))
    }

    @Test
    fun stringToWorkoutType() {
        assertEquals(Workout.Type.CARDIO, EnumConverters().stringToWorkoutType("CARDIO"))
        assertEquals(Workout.Type.MUSCLE_BUILDING, EnumConverters().stringToWorkoutType("MUSCLE_BUILDING"))
    }

    @Test
    fun roundtripFromWorkoutTypeToStringAndBack() {
        assertEquals(Workout.Type.CARDIO, EnumConverters().stringToWorkoutType(EnumConverters().workoutTypeToString(Workout.Type.CARDIO)))
        assertEquals(Workout.Type.MUSCLE_BUILDING, EnumConverters().stringToWorkoutType(EnumConverters().workoutTypeToString(Workout.Type.MUSCLE_BUILDING)))
    }
}
