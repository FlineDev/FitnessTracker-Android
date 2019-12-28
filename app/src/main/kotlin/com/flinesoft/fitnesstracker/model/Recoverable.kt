package com.flinesoft.fitnesstracker.model

import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
interface Recoverable {
    val recoveryDuration: Duration
}
