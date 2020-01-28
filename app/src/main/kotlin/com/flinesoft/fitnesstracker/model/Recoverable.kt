package com.flinesoft.fitnesstracker.model

import org.joda.time.DateTime
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
interface Recoverable {
    enum class BetweenDurationRating { POSITIVE, WARNING, NEGATIVE }

    val startDate: DateTime
    val endDate: DateTime
    val recoveryStartDate: DateTime
    val recoveryEndDate: DateTime
    val recoveryDuration: Duration

    fun betweenRecoverablesDurationRating(duration: Duration): BetweenDurationRating
}
