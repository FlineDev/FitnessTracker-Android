package com.flinesoft.fitnesstracker.model

import org.joda.time.DateTime

interface Measurement {
    val value: Double
    val measureDate: DateTime
}
