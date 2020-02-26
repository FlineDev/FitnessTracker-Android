package com.flinesoft.fitnesstracker.model

import android.icu.util.MeasureUnit
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime

@Entity(tableName = "WeightMeasurements")
data class WeightMeasurement(
    var weightInKilograms: Double,
    override var measureDate: DateTime
) : Measurement {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L

    override val value: Double
        get() = weightInKilograms

    override val unit: MeasureUnit
        get() = MeasureUnit.KILOGRAM
}
