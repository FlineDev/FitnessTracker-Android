package com.flinesoft.fitnesstracker.ui.workouts

import androidx.lifecycle.ViewModel
import com.flinesoft.fitnesstracker.model.Workout
import org.joda.time.DateTime
import kotlin.time.ExperimentalTime

@ExperimentalTime
class EditWorkoutViewModel : ViewModel() {
    var existingWorkoutId: Long? = null
    var workoutType = Workout.Type.CARDIO
    var startDate: DateTime = DateTime.now().minusHours(1)
    var endDate: DateTime = DateTime.now()
}
