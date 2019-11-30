package com.flinesoft.fitnesstracker.persistence.converters

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.junit.Test

import org.junit.Assert.*

class DateTimeConverterTest {
    @Test
    fun dateTimeToString() {
        val date2000UTC = DateTime(2000, 1, 1, 0, 0, DateTimeZone.UTC)
        val date2000MEZ = DateTime(2000, 1, 1, 1, 0, DateTimeZone.forID("CET")) // German time zone
        val date2000PST = DateTime(1999, 12, 31, 16, 0, DateTimeZone.forID("PST8PDT")) // Californian time zone

        assertEquals("2000-01-01T00:00:00.000Z", DateTimeConverter().dateTimeToString(date2000UTC))
        assertEquals("2000-01-01T00:00:00.000Z", DateTimeConverter().dateTimeToString(date2000MEZ))
        assertEquals("2000-01-01T00:00:00.000Z", DateTimeConverter().dateTimeToString(date2000PST))
    }

    @Test
    fun stringToDateTime() {
        val date2000 = DateTime(2000, 1, 1, 0, 0, DateTimeZone.UTC)
        assertEquals(date2000, DateTimeConverter().stringToDateTime("2000-01-01T00:00:00.000Z"))
        assertEquals(date2000, DateTimeConverter().stringToDateTime("2000-01-01T01:00:00.000+01:00")) // German time zone
        assertEquals(date2000, DateTimeConverter().stringToDateTime("1999-12-31T16:00:00.000-08:00")) // Californian time zone
    }

    @Test
    fun roundtripFromDateTimeToStringAndBack() {
        val date2000 = DateTime(2000, 1, 1, 0, 0, DateTimeZone.UTC)
        assertEquals(date2000, DateTimeConverter().stringToDateTime(DateTimeConverter().dateTimeToString(date2000)))
    }
}