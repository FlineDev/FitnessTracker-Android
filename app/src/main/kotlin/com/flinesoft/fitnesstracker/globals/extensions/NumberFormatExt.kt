package com.flinesoft.fitnesstracker.globals.extensions

import android.icu.text.DecimalFormat
import android.icu.text.NumberFormat
import android.icu.util.ULocale

object NumberFormatExt {
    val default: NumberFormat = NumberFormat.getInstance(ULocale.getDefault())
}

fun NumberFormat.decimalSeparator(): Char = (this as DecimalFormat).decimalFormatSymbols.decimalSeparator
fun NumberFormat.groupingSeparator(): Char = (this as DecimalFormat).decimalFormatSymbols.groupingSeparator

fun NumberFormat.decimalSeparatorString(): String = (this as DecimalFormat).decimalFormatSymbols.decimalSeparatorString
fun NumberFormat.groupingSeparatorString(): String = (this as DecimalFormat).decimalFormatSymbols.groupingSeparatorString
