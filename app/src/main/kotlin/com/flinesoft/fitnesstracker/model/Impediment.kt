package com.flinesoft.fitnesstracker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.flinesoft.fitnesstracker.globals.BETWEEN_WORKOUTS_POSITIVE_DAYS
import com.flinesoft.fitnesstracker.globals.BETWEEN_WORKOUTS_POSITIVE_PLUS_WARNING_DAYS
import org.joda.time.DateTime
import kotlin.time.*

@ExperimentalTime
@Entity(tableName = "Impediments")
class Impediment(@ColumnInfo(defaultValue = "Impediment") var name: String, startDate: DateTime, endDate: DateTime) : Recoverable {
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

    override fun betweenRecoverablesDurationRating(recoverableAbove: Recoverable): Recoverable.BetweenDurationRating {
        return when ((recoverableAbove.endDate.millis - endDate.millis).milliseconds) {
            in 0.hours..BETWEEN_WORKOUTS_POSITIVE_DAYS.days ->
                Recoverable.BetweenDurationRating.POSITIVE

            in BETWEEN_WORKOUTS_POSITIVE_DAYS.days..BETWEEN_WORKOUTS_POSITIVE_PLUS_WARNING_DAYS.days ->
                Recoverable.BetweenDurationRating.WARNING

            else ->
                Recoverable.BetweenDurationRating.NEGATIVE
        }
    }
}
