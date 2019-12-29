package com.flinesoft.fitnesstracker.globals.extensions

import android.icu.text.MeasureFormat
import android.icu.text.NumberFormat
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import android.icu.util.ULocale

object MeasureFormatExt {
    val short: MeasureFormat = MeasureFormat.getInstance(ULocale.getDefault(), MeasureFormat.FormatWidth.SHORT)
}

fun MeasureFormat.doubleToString(doubleValue: Double, unit: MeasureUnit): String {
    val numberFormat = NumberFormat.getInstance(ULocale.getDefault()).apply { minimumFractionDigits = 1 }
    return "${numberFormat.format(doubleValue)} ${getUnitDisplayName(unit)}"
}

fun MeasureFormat.intToString(intValue: Int, unit: MeasureUnit): String = format(Measure(intValue, unit))

fun MeasureFormat.stringToDouble(string: String, unit: MeasureUnit): Double? {
    if (string.isBlank()) return null

    val valueText: String = string.replace(getUnitDisplayName(unit), "").replace(NumberFormatExt.default.groupingSeparatorString(), "").trim()
    val doubleValue = NumberFormatExt.default.parse(valueText).toDouble()
    return if (doubleValue != 0.0) doubleValue else null
}

fun MeasureFormat.stringToInt(string: String, unit: MeasureUnit): Int? {
    if (string.isBlank()) return null

    val valueText: String = string.replace(getUnitDisplayName(unit), "").replace(NumberFormatExt.default.groupingSeparatorString(), "").trim()
    val intValue = NumberFormatExt.default.parse(valueText).toInt()
    return if (intValue != 0) intValue else null
}
