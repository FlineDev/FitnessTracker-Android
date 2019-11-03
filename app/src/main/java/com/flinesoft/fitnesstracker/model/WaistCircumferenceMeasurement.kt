package com.flinesoft.fitnesstracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime

@Entity(tableName = "WaistCircumferenceMeasurements")
data class WaistCircumferenceMeasurement(
    var circumferenceInCentimeters: Double,
    var measureDate: DateTime
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
}