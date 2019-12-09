package com.flinesoft.fitnesstracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.flinesoft.fitnesstracker.globals.utility.TimeInterval
import com.flinesoft.fitnesstracker.globals.utility.days
import com.flinesoft.fitnesstracker.globals.utility.hours
import com.flinesoft.fitnesstracker.globals.utility.weeks
import org.joda.time.DateTime

@Entity(tableName = "impediments")
data class Impediment(
    var type: Type,
    var startDate: DateTime
) : Recoverable {
    enum class Type {
        STIFFNESS, MODERATE_COLD, SEVERE_COLD, MODERATE_INJURY, SEVERE_INJURY
    }

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L

    override val timeToRecover: TimeInterval
        get() = when (type) {
            Type.STIFFNESS -> 72.hours()
            Type.MODERATE_COLD -> 5.days()
            Type.SEVERE_COLD -> 10.days()
            Type.MODERATE_INJURY -> 2.weeks()
            Type.SEVERE_INJURY -> 4.weeks()
        }
}
