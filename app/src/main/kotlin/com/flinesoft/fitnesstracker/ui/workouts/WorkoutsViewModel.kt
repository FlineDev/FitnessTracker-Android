package com.flinesoft.fitnesstracker.ui.workouts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.flinesoft.fitnesstracker.globals.extensions.database
import com.flinesoft.fitnesstracker.model.Workout
import kotlin.time.ExperimentalTime

@ExperimentalTime
class WorkoutsViewModel(application: Application) : AndroidViewModel(application) {
    val workouts: LiveData<List<Workout>> = database().workoutDao.allOrderedByEndDateDescending()
}
