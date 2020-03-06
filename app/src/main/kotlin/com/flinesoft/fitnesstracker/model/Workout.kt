package com.flinesoft.fitnesstracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.flinesoft.fitnesstracker.globals.BETWEEN_WORKOUTS_POSITIVE_DAYS
import com.flinesoft.fitnesstracker.globals.BETWEEN_WORKOUTS_POSITIVE_PLUS_WARNING_DAYS
import com.flinesoft.fitnesstracker.globals.extensions.plusKt
import org.joda.time.DateTime
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.days
import kotlin.time.hours
import kotlin.time.milliseconds

@ExperimentalTime
@Entity(tableName = "Workouts")
data class Workout(var type: Type, override var startDate: DateTime, override var endDate: DateTime) : Recoverable {
    enum class Type {
        CARDIO, MUSCLE_BUILDING
    }

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L

    override val recoveryStartDate: DateTime
        get() = endDate

    override val recoveryEndDate: DateTime
        get() = endDate.plusKt(recoveryDuration)

    override val recoveryDuration: Duration
        get() = when (type) {
            Type.CARDIO -> 24.hours
            Type.MUSCLE_BUILDING -> 48.hours
        }

    override fun betweenRecoverablesDurationRating(recoverableAbove: Recoverable): Recoverable.BetweenDurationRating {
        return when ((recoverableAbove.endDate.millis - endDate.millis).milliseconds) {
            in 0.hours..recoveryDuration.div(2) ->
                if (recoverableAbove is Impediment) Recoverable.BetweenDurationRating.POSITIVE else Recoverable.BetweenDurationRating.NEGATIVE

            in recoveryDuration.div(2)..recoveryDuration ->
                if (recoverableAbove is Impediment) Recoverable.BetweenDurationRating.POSITIVE else Recoverable.BetweenDurationRating.WARNING

            in recoveryDuration..(recoveryDuration + BETWEEN_WORKOUTS_POSITIVE_DAYS.days) ->
                Recoverable.BetweenDurationRating.POSITIVE

            in (recoveryDuration + BETWEEN_WORKOUTS_POSITIVE_DAYS.days)..(recoveryDuration + BETWEEN_WORKOUTS_POSITIVE_PLUS_WARNING_DAYS.days) ->
                Recoverable.BetweenDurationRating.WARNING

            else ->
                Recoverable.BetweenDurationRating.NEGATIVE
        }
    }
}
