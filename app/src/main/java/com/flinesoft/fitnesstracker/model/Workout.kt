package com.flinesoft.fitnesstracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.flinesoft.fitnesstracker.globals.utility.TimeInterval
import com.flinesoft.fitnesstracker.globals.utility.hours
import java.util.*

@Entity(tableName = "Workouts")
data class Workout(
    var type: Type,
    var startDate: Date,
    var endDate: Date
): Recoverable {
    enum class Type {
        CARDIO, MUSCLE_BUILDING
    }

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L

    override val timeToRecover: TimeInterval
        get() = when (type) {
            Type.CARDIO -> 24.hours()
            Type.MUSCLE_BUILDING -> 72.hours()
        }
}