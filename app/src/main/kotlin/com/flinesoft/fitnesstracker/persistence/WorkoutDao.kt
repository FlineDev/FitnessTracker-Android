package com.flinesoft.fitnesstracker.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.flinesoft.fitnesstracker.model.Workout

@Dao
abstract class WorkoutDao: CrudDao<Workout>() {
    @Query("SELECT * FROM Workouts ORDER BY startDate ASC")
    abstract fun allOrderedByStartDate(): LiveData<List<Workout>>

    @Query("SELECT * FROM Workouts ORDER BY endDate ASC")
    abstract fun allOrderedByEndDate(): LiveData<List<Workout>>

    fun create(workout: Workout): LiveData<Workout> = read(insert(workout))

    @Query("SELECT * FROM Workouts WHERE id = :id")
    protected abstract fun read(id: Long): LiveData<Workout>
}