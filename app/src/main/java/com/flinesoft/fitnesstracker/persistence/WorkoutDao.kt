package com.flinesoft.fitnesstracker.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import com.flinesoft.fitnesstracker.model.Workout

@Dao
interface WorkoutDao {
    @Insert
    fun insert(workout: Workout)

    @Update
    fun update(workout: Workout)

    @Delete
    fun delete(workout: Workout)

    @Query("SELECT * FROM Workouts ORDER BY startDate ASC")
    fun allOrderedByStartDate(): LiveData<List<Workout>>

    @Query("SELECT * FROM Workouts ORDER BY endDate ASC")
    fun allOrderedByEndDate(): LiveData<List<Workout>>
}