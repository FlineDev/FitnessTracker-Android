package com.flinesoft.fitnesstracker.persistence.converters

import com.flinesoft.fitnesstracker.model.Impediment
import com.flinesoft.fitnesstracker.model.Workout
import org.junit.Test

import org.junit.Assert.*

class EnumConvertersTest {
    @Test
    fun impedimentTypeToString() {
        assertEquals("STIFFNESS", EnumConverters().impedimentTypeToString(Impediment.Type.STIFFNESS))
        assertEquals("MODERATE_COLD", EnumConverters().impedimentTypeToString(Impediment.Type.MODERATE_COLD))
        assertEquals("SEVERE_COLD", EnumConverters().impedimentTypeToString(Impediment.Type.SEVERE_COLD))
    }

    @Test
    fun stringToImpedimentType() {
        assertEquals(Impediment.Type.STIFFNESS, EnumConverters().stringToImpedimentType("STIFFNESS"))
        assertEquals(Impediment.Type.MODERATE_COLD, EnumConverters().stringToImpedimentType("MODERATE_COLD"))
        assertEquals(Impediment.Type.SEVERE_COLD, EnumConverters().stringToImpedimentType("SEVERE_COLD"))
    }

    @Test
    fun roundtripFromImpedimentTypeToStringAndBack() {
        assertEquals(Impediment.Type.STIFFNESS, EnumConverters().stringToImpedimentType(EnumConverters().impedimentTypeToString(Impediment.Type.STIFFNESS)))
        assertEquals(Impediment.Type.MODERATE_COLD, EnumConverters().stringToImpedimentType(EnumConverters().impedimentTypeToString(Impediment.Type.MODERATE_COLD)))
        assertEquals(Impediment.Type.SEVERE_COLD, EnumConverters().stringToImpedimentType(EnumConverters().impedimentTypeToString(Impediment.Type.SEVERE_COLD)))
    }

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
