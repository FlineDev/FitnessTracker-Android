package com.flinesoft.fitnesstracker.persistence.converters

import androidx.room.TypeConverter
import com.flinesoft.fitnesstracker.model.Injury
import com.flinesoft.fitnesstracker.model.Workout

class EnumConverters {
    @TypeConverter
    fun injuryTypeToString(injuryType: Injury.Type): String = injuryType.name

    @TypeConverter
    fun stringToInjuryType(string: String): Injury.Type = Injury.Type.valueOf(string)

    @TypeConverter
    fun workoutTypeToString(workoutType: Workout.Type): String = workoutType.name

    @TypeConverter
    fun stringToWorkoutType(string: String): Workout.Type = Workout.Type.valueOf(string)
}