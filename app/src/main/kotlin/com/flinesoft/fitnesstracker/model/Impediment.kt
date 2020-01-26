package com.flinesoft.fitnesstracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds

@ExperimentalTime
@Entity(tableName = "Impediments")
data class Impediment(var startDate: DateTime, var endDate: DateTime) : Recoverable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L

    override val recoveryDuration: Duration
        get() = (endDate.millis - startDate.millis).milliseconds
}
