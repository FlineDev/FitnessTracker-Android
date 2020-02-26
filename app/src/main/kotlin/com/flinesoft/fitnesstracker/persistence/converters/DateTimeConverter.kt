package com.flinesoft.fitnesstracker.persistence.converters

import androidx.room.TypeConverter
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat

class DateTimeConverter {
    @TypeConverter
    fun dateTimeToString(dateTime: DateTime): String = ISODateTimeFormat.dateTime().withZoneUTC().print(dateTime)

    @TypeConverter
    fun stringToDateTime(string: String): DateTime = ISODateTimeFormat.dateTime().parseDateTime(string)
}
