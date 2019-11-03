package com.flinesoft.fitnesstracker.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import com.flinesoft.fitnesstracker.model.Injury

@Dao
interface InjuryDao {
    @Insert
    fun insert(injury: Injury)

    @Update
    fun update(injury: Injury)

    @Delete
    fun delete(injury: Injury)

    @Query("SELECT * FROM Injuries ORDER BY startDate ASC")
    fun allOrderedByStartDate(): LiveData<List<Injury>>
}