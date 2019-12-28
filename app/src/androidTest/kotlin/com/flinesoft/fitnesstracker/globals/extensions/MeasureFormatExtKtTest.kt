package com.flinesoft.fitnesstracker.globals.extensions

import android.icu.text.MeasureFormat
import android.icu.util.MeasureUnit
import android.icu.util.ULocale
import androidx.test.runner.AndroidJUnit4
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MeasureFormatExtKtTest {
    private val measureFormat = MeasureFormat.getInstance(ULocale.US, MeasureFormat.FormatWidth.SHORT)

    @Test
    fun valueToStringForDouble() {
        assertEquals("42.4 kg", measureFormat.doubleToString(42.4, MeasureUnit.KILOGRAM))
        assertEquals("1,000,000.001 cm", measureFormat.doubleToString(1_000_000.001, MeasureUnit.CENTIMETER))
    }

    @Test
    fun valueToStringForInt() {
        assertEquals("42 kg", measureFormat.intToString(42, MeasureUnit.KILOGRAM))
        assertEquals("1,000,000 cm", measureFormat.intToString(1_000_000, MeasureUnit.CENTIMETER))
    }

    @Test
    fun stringToDouble() {
        assertEquals(42.4, measureFormat.stringToDouble("42.4 kg", MeasureUnit.KILOGRAM))
        assertEquals(1_000_000.001, measureFormat.stringToDouble("1,000,000.001 cm", MeasureUnit.CENTIMETER))
    }

    @Test
    fun stringToInt() {
        assertEquals(42, measureFormat.stringToInt("42 kg", MeasureUnit.KILOGRAM))
        assertEquals(1_000_000, measureFormat.stringToInt("1,000,000 cm", MeasureUnit.CENTIMETER))
    }
}
