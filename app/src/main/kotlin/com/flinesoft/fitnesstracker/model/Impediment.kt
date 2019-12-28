package com.flinesoft.fitnesstracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.days
import kotlin.time.hours

@ExperimentalTime
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

    override val recoveryDuration: Duration
        get() = when (type) {
            Type.STIFFNESS -> 72.hours
            Type.MODERATE_COLD -> 5.days
            Type.SEVERE_COLD -> 10.days
            Type.MODERATE_INJURY -> 14.days
            Type.SEVERE_INJURY -> 28.days
        }
}
