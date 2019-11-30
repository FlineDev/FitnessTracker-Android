package com.flinesoft.fitnesstracker.persistence.converters

import com.flinesoft.fitnesstracker.model.Injury
import com.flinesoft.fitnesstracker.model.Workout
import org.junit.Test

import org.junit.Assert.*

class EnumConvertersTest {
    @Test
    fun injuryTypeToString() {
        assertEquals("STIFFNESS", EnumConverters().injuryTypeToString(Injury.Type.STIFFNESS))
        assertEquals("MODERATE_COLD", EnumConverters().injuryTypeToString(Injury.Type.MODERATE_COLD))
        assertEquals("SEVERE_COLD", EnumConverters().injuryTypeToString(Injury.Type.SEVERE_COLD))
    }

    @Test
    fun stringToInjuryType() {
        assertEquals(Injury.Type.STIFFNESS, EnumConverters().stringToInjuryType("STIFFNESS"))
        assertEquals(Injury.Type.MODERATE_COLD, EnumConverters().stringToInjuryType("MODERATE_COLD"))
        assertEquals(Injury.Type.SEVERE_COLD, EnumConverters().stringToInjuryType("SEVERE_COLD"))
    }

    @Test
    fun roundtripFromInjuryTypeToStringAndBack() {
        assertEquals(Injury.Type.STIFFNESS, EnumConverters().stringToInjuryType(EnumConverters().injuryTypeToString(Injury.Type.STIFFNESS)))
        assertEquals(Injury.Type.MODERATE_COLD, EnumConverters().stringToInjuryType(EnumConverters().injuryTypeToString(Injury.Type.MODERATE_COLD)))
        assertEquals(Injury.Type.SEVERE_COLD, EnumConverters().stringToInjuryType(EnumConverters().injuryTypeToString(Injury.Type.SEVERE_COLD)))
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
