package com.flinesoft.fitnesstracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.hours

@ExperimentalTime
@Entity(tableName = "Workouts")
data class Workout(var type: Type, override var startDate: DateTime, var endDate: DateTime) : Recoverable {
    enum class Type {
        CARDIO, MUSCLE_BUILDING
    }

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L

    override val recoveryStartDate: DateTime
        get() = endDate

    override val recoveryEndDate: DateTime
        get() = endDate.plus(recoveryDuration.toLongMilliseconds())

    override val recoveryDuration: Duration
        get() = when (type) {
            Type.CARDIO -> 24.hours
            Type.MUSCLE_BUILDING -> 48.hours
        }
}
