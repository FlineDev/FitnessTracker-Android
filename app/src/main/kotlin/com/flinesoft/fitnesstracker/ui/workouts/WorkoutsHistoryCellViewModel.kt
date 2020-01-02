package com.flinesoft.fitnesstracker.ui.workouts

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.flinesoft.fitnesstracker.R
import com.flinesoft.fitnesstracker.globals.BETWEEN_WORKOUTS_NEUTRAL_DAYS
import com.flinesoft.fitnesstracker.globals.BETWEEN_WORKOUTS_POSITIVE_DAYS
import com.flinesoft.fitnesstracker.model.Workout
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.days

@ExperimentalTime
class WorkoutsHistoryCellViewModel(application: Application, val workout: Workout, val betweenWorkoutsDuration: Duration) : AndroidViewModel(application) {
    fun betweenWorkoutsIconDrawable(): Drawable? = when (betweenWorkoutsDuration) {
        in 0.days..BETWEEN_WORKOUTS_POSITIVE_DAYS.days ->
            ContextCompat.getDrawable(getApplication(), R.drawable.ic_workouts_time_between_positive)

        in BETWEEN_WORKOUTS_NEUTRAL_DAYS.days..(BETWEEN_WORKOUTS_POSITIVE_DAYS + BETWEEN_WORKOUTS_NEUTRAL_DAYS).days ->
            ContextCompat.getDrawable(getApplication(), R.drawable.ic_workouts_time_between_neutral)

        else ->
            ContextCompat.getDrawable(getApplication(), R.drawable.ic_workouts_time_between_negative)
    }
}
