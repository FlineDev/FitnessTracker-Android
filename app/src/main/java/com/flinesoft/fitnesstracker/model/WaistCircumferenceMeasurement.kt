package com.flinesoft.fitnesstracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "WaistCircumferenceMeasurements")
data class WaistCircumferenceMeasurement(
    var circumferenceInCentimeters: Double,
    var measureDate: Date
) {
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L
}