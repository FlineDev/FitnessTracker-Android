package com.flinesoft.fitnesstracker.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.flinesoft.fitnesstracker.model.Impediment

@Dao
abstract class ImpedimentDao : CrudDao<Impediment>() {
    @Query("SELECT * FROM Impediments ORDER BY startDate ASC")
    abstract fun allOrderedByStartDate(): LiveData<List<Impediment>>

    suspend fun create(impediment: Impediment): LiveData<Impediment> = read(insert(impediment))

    @Query("SELECT * FROM Impediments WHERE id = :id")
    protected abstract fun read(id: Long): LiveData<Impediment>
}
