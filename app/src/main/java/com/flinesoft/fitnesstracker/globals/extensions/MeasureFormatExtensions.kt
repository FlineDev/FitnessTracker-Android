package com.flinesoft.fitnesstracker.globals.extensions

import android.icu.text.MeasureFormat
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import android.icu.util.ULocale

object MeasureFormatExt {
    val short: MeasureFormat = MeasureFormat.getInstance(ULocale.getDefault(), MeasureFormat.FormatWidth.SHORT)
}

// TODO: continue here by adding a unit tests for parsing string to int/double for big numbers (separators aren't parsed correctly)

fun MeasureFormat.valueToString(value: Double, unit: MeasureUnit): String = format(Measure(value, unit))
fun MeasureFormat.valueToString(value: Int, unit: MeasureUnit): String = format(Measure(value, unit))

fun MeasureFormat.stringToDouble(string: String, unit: MeasureUnit): Double? {
    val valueText: String = string.replace(getUnitDisplayName(unit), "").trim()
    return if (valueText.isNotEmpty()) valueText.toDouble() else null
}

fun MeasureFormat.stringToInt(string: String, unit: MeasureUnit): Int? {
    val valueText: String = string.replace(getUnitDisplayName(unit), "").trim()
    return if (valueText.isNotEmpty()) valueText.toInt() else null
}