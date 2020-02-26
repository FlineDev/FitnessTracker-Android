package com.flinesoft.fitnesstracker.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.flinesoft.fitnesstracker.model.WaistCircumferenceMeasurement

@Dao
abstract class WaistCircumferenceMeasurementDao : CrudDao<WaistCircumferenceMeasurement>() {
    @Query("SELECT * FROM WaistCircumferenceMeasurements ORDER BY measureDate ASC")
    abstract fun allOrderedByMeasureDate(): LiveData<List<WaistCircumferenceMeasurement>>

    // TODO: fetch only the latest measurements (up to 1 year back)
    @Query("SELECT * FROM WaistCircumferenceMeasurements ORDER BY measureDate DESC")
    abstract fun allOrderedByMeasureDateDescending(): LiveData<List<WaistCircumferenceMeasurement>>

    suspend fun create(measurement: WaistCircumferenceMeasurement): LiveData<WaistCircumferenceMeasurement> = read(insert(measurement))

    @Query("SELECT * FROM WaistCircumferenceMeasurements WHERE id = :id")
    abstract fun read(id: Long): LiveData<WaistCircumferenceMeasurement>
}
