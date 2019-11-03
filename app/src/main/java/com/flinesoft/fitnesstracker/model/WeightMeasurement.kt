package com.flinesoft.fitnesstracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "WeightMeasurements")
data class WeightMeasurement(
    var weightInKilograms: Double,
    var measureDate: Date
) {
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L
}