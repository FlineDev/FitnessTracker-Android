package com.flinesoft.fitnesstracker.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import com.flinesoft.fitnesstracker.model.WaistCircumferenceMeasurement

@Dao
interface WaistCircumferenceMeasurementDao {
    @Insert
    fun insert(measurement: WaistCircumferenceMeasurement)

    @Update
    fun update(measurement: WaistCircumferenceMeasurement)

    @Delete
    fun delete(measurement: WaistCircumferenceMeasurement)

    @Query("SELECT * FROM WaistCircumferenceMeasurements ORDER BY measureDate ASC")
    fun allOrderedByMeasureDate(): LiveData<List<WaistCircumferenceMeasurement>>
}