package com.flinesoft.fitnesstracker.model

import org.joda.time.DateTime
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
interface Recoverable {
    val startDate: DateTime
    val endDate: DateTime
    val recoveryStartDate: DateTime
    val recoveryEndDate: DateTime
    val recoveryDuration: Duration
}
