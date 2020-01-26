package com.flinesoft.fitnesstracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds

@ExperimentalTime
@Entity(tableName = "Impediments")
class Impediment(startDate: DateTime, endDate: DateTime) : Recoverable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L

    override var startDate: DateTime = startDate
        set(value) { field = value.withTimeAtStartOfDay() }

    override var endDate: DateTime = endDate
        set(value) { field = value.withTimeAtStartOfDay().plusDays(1).minusSeconds(1) }

    override val recoveryStartDate: DateTime
        get() = startDate

    override val recoveryEndDate: DateTime
        get() = endDate

    override val recoveryDuration: Duration
        get() = (endDate.millis - startDate.millis).milliseconds
}
