package com.flinesoft.fitnesstracker.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import com.flinesoft.fitnesstracker.model.WeightMeasurement

@Dao
interface WeightMeasurementDao {
    @Insert
    fun insert(measurement: WeightMeasurement)

    @Update
    fun update(measurement: WeightMeasurement)

    @Delete
    fun delete(measurement: WeightMeasurement)

    @Query("SELECT * FROM WeightMeasurements ORDER BY measureDate ASC")
    fun allOrderedByMeasureDate(): LiveData<List<WeightMeasurement>>
}