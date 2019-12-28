package com.flinesoft.fitnesstracker.globals.extensions

import android.icu.text.DecimalFormat
import android.icu.text.MeasureFormat
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import android.icu.util.ULocale

object MeasureFormatExt {
    val short: MeasureFormat = MeasureFormat.getInstance(ULocale.getDefault(), MeasureFormat.FormatWidth.SHORT)
}

fun MeasureFormat.doubleToString(doubleValue: Double, unit: MeasureUnit): String = format(Measure(doubleValue, unit))
fun MeasureFormat.intToString(intValue: Int, unit: MeasureUnit): String = format(Measure(intValue, unit))

fun MeasureFormat.stringToDouble(string: String, unit: MeasureUnit): Double? {
    val groupingSeparator: String = (numberFormat as DecimalFormat).decimalFormatSymbols.groupingSeparatorString
    val valueText: String = string.replace(getUnitDisplayName(unit), "").replace(groupingSeparator, "").trim()
    return if (valueText.isNotEmpty()) valueText.toDouble() else null
}

fun MeasureFormat.stringToInt(string: String, unit: MeasureUnit): Int? {
    val groupingSeparator: String = (numberFormat as DecimalFormat).decimalFormatSymbols.groupingSeparatorString
    val valueText: String = string.replace(getUnitDisplayName(unit), "").replace(groupingSeparator, "").trim()
    return if (valueText.isNotEmpty()) valueText.toInt() else null
}
