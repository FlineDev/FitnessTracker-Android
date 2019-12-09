package com.flinesoft.fitnesstracker.model

import com.flinesoft.fitnesstracker.globals.utility.TimeInterval

interface Recoverable {
    val timeToRecover: TimeInterval
}
