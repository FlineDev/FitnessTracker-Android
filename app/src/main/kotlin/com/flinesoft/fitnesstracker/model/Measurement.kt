package com.flinesoft.fitnesstracker.model

import android.icu.util.MeasureUnit
import com.flinesoft.fitnesstracker.globals.extensions.MeasureFormatExt
import com.flinesoft.fitnesstracker.globals.extensions.doubleToString
import org.joda.time.DateTime

interface Measurement {
    val value: Double
    val unit: MeasureUnit
    val measureDate: DateTime

    fun formattedValue(): String = MeasureFormatExt.short().doubleToString(value, unit)
}
