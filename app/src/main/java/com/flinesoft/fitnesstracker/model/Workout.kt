package com.flinesoft.fitnesstracker.model

import com.flinesoft.fitnesstracker.globals.utility.TimeInterval
import com.flinesoft.fitnesstracker.globals.utility.hours
import java.util.*

data class Workout(
    val type: Type,
    val startDate: Date,
    val endDate: Date
): Recoverable {
    enum class Type {
        CARDIO, MUSCLE_BUILDING
    }

    override val timeToRecover: TimeInterval
        get() = when (type) {
            Type.CARDIO -> 24.hours()
            Type.MUSCLE_BUILDING -> 72.hours()
        }
}