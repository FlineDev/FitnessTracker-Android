package com.flinesoft.fitnesstracker.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.flinesoft.fitnesstracker.model.WeightMeasurement

@Dao
abstract class WeightMeasurementDao : CrudDao<WeightMeasurement>() {
    @Query("SELECT * FROM WeightMeasurements ORDER BY measureDate ASC")
    abstract fun allOrderedByMeasureDate(): LiveData<List<WeightMeasurement>>

    suspend fun create(measurement: WeightMeasurement): LiveData<WeightMeasurement> = read(insert(measurement))

    @Query("SELECT * FROM WeightMeasurements WHERE id = :id")
    abstract fun read(id: Long): LiveData<WeightMeasurement>
}
