package com.flinesoft.fitnesstracker.globals.extensions

import java.text.DateFormat
import java.util.*

object DateFormatExt {
    fun dateMedium(): DateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
    fun timeShort(): DateFormat = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault())
}
