package com.flinesoft.fitnesstracker.model

import com.flinesoft.fitnesstracker.globals.utility.TimeInterval
import com.flinesoft.fitnesstracker.globals.utility.days
import com.flinesoft.fitnesstracker.globals.utility.hours
import java.util.*

data class Injury(
    val type: Type,
    val startDate: Date
): Recoverable {
    enum class Type {
        STIFFNESS, MODERATE_COLD, SEVERE_COLD
    }

    override val timeToRecover: TimeInterval
        get() = when (type) {
            Type.STIFFNESS -> 72.hours()
            Type.MODERATE_COLD -> 4.days()
            Type.SEVERE_COLD -> 7.days()
        }
}

