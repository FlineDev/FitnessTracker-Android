package com.flinesoft.fitnesstracker.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.flinesoft.fitnesstracker.model.Impediment
import kotlin.time.ExperimentalTime

@ExperimentalTime
@Dao
abstract class ImpedimentDao : CrudDao<Impediment>() {
    // TODO: fetch only the latest workouts (up to 1 year back)
    @Query("SELECT * FROM Impediments ORDER BY endDate DESC")
    abstract fun allOrderedByEndDateDescending(): LiveData<List<Impediment>>

    suspend fun create(impediment: Impediment): LiveData<Impediment> = read(insert(impediment))

    @Query("SELECT * FROM Impediments WHERE id = :id")
    abstract fun read(id: Long): LiveData<Impediment>
}
