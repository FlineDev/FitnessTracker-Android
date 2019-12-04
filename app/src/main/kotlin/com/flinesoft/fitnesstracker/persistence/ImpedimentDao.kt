package com.flinesoft.fitnesstracker.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import com.flinesoft.fitnesstracker.model.Impediment

@Dao
interface ImpedimentDao {
    @Insert
    fun insert(impediment: Impediment)

    @Update
    fun update(impediment: Impediment)

    @Delete
    fun delete(impediment: Impediment)

    @Query("SELECT * FROM Impediments ORDER BY startDate ASC")
    fun allOrderedByStartDate(): LiveData<List<Impediment>>
}