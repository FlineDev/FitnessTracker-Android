package com.flinesoft.fitnesstracker.globals.extensions

import java.text.DateFormat
import java.util.Locale

object DateFormatExt {
    fun dateMedium(): DateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
    fun timeShort(): DateFormat = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault())
    fun dateTimeShort(): DateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.getDefault())
    fun dateMediumTimeShort(): DateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale.getDefault())
}
