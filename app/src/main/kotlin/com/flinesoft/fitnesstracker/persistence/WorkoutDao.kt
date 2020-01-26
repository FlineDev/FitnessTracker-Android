package com.flinesoft.fitnesstracker.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.flinesoft.fitnesstracker.model.Workout
import kotlin.time.ExperimentalTime

@ExperimentalTime
@Dao
abstract class WorkoutDao : CrudDao<Workout>() {
    // TODO: fetch only the latest workouts (up to 1 year back)
    @Query("SELECT * FROM Workouts ORDER BY endDate DESC")
    abstract fun allOrderedByEndDateDescending(): LiveData<List<Workout>>

    suspend fun create(workout: Workout): LiveData<Workout> = read(insert(workout))

    @Query("SELECT * FROM Workouts WHERE id = :id")
    abstract fun read(id: Long): LiveData<Workout>
}
