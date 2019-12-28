package com.flinesoft.fitnesstracker.persistence.converters

import androidx.room.TypeConverter
import com.flinesoft.fitnesstracker.model.Impediment
import com.flinesoft.fitnesstracker.model.Workout
import kotlin.time.ExperimentalTime

@ExperimentalTime
class EnumConverters {
    @TypeConverter
    fun impedimentTypeToString(impedimentType: Impediment.Type): String = impedimentType.name

    @TypeConverter
    fun stringToImpedimentType(string: String): Impediment.Type = Impediment.Type.valueOf(string)

    @TypeConverter
    fun workoutTypeToString(workoutType: Workout.Type): String = workoutType.name

    @TypeConverter
    fun stringToWorkoutType(string: String): Workout.Type = Workout.Type.valueOf(string)
}
