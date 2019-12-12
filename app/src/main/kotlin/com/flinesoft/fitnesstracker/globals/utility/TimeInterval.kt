package com.flinesoft.fitnesstracker.globals.utility

/**
 * The time interval in seconds.
 */
typealias TimeInterval = Double

// Convenience initializers for a `TimeInterval` from integer literals. Use like this: `5.minutes()`
fun Int.milliseconds(): TimeInterval = seconds() / 1_000
fun Int.seconds(): TimeInterval = toDouble()
fun Int.minutes(): TimeInterval = seconds() * 60
fun Int.hours(): TimeInterval = minutes() * 60
fun Int.days(): TimeInterval = hours() * 24
fun Int.weeks(): TimeInterval = days() * 7
