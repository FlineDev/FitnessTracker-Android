package com.flinesoft.fitnesstracker.globals.extensions

import org.joda.time.DateTime
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds

@ExperimentalTime
fun DateTime.minusKt(duration: Duration): DateTime = this.minus(duration.toLongMilliseconds())

@ExperimentalTime
fun DateTime.plusKt(duration: Duration): DateTime = this.plus(duration.toLongMilliseconds())

@ExperimentalTime
fun DateTime.durationSince(otherDateTime: DateTime): Duration = (this.millis - otherDateTime.millis).milliseconds
