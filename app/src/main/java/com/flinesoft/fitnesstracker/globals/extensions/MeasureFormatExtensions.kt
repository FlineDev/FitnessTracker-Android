package com.flinesoft.fitnesstracker.globals.extensions

import android.icu.text.MeasureFormat
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import android.icu.util.ULocale

object MeasureFormatExt {
    val short: MeasureFormat = MeasureFormat.getInstance(ULocale.getDefault(), MeasureFormat.FormatWidth.SHORT)
}

fun MeasureFormat.valueToString(value: Double, unit: MeasureUnit): String = format(Measure(value, unit))
fun MeasureFormat.valueToString(value: Int, unit: MeasureUnit): String = format(Measure(value, unit))

fun MeasureFormat.stringToDouble(string: String, unit: MeasureUnit): Double = string.replace(getUnitDisplayName(unit), "").trim().toDouble()
fun MeasureFormat.stringToInt(string: String, unit: MeasureUnit): Int = string.replace(getUnitDisplayName(unit), "").trim().toInt()