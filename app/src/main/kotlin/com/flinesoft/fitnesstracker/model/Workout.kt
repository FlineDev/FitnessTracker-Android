package com.flinesoft.fitnesstracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.flinesoft.fitnesstracker.globals.utility.TimeInterval
import com.flinesoft.fitnesstracker.globals.utility.hours
import org.joda.time.DateTime

@Entity(tableName = "Workouts")
data class Workout(
    var type: Type,
    var startDate: DateTime,
    var endDate: DateTime
) : Recoverable {
    enum class Type {
        CARDIO, MUSCLE_BUILDING
    }

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L

    override val timeToRecover: TimeInterval
        get() = when (type) {
            Type.CARDIO -> 24.hours()
            Type.MUSCLE_BUILDING -> 48.hours()
        }
}
