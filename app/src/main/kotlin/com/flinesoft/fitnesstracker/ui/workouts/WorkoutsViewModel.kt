package com.flinesoft.fitnesstracker.ui.workouts

import android.app.Application
import android.content.Context
import android.text.format.DateUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.flinesoft.fitnesstracker.globals.WORKOUT_RECOMMENDATION_ADDITIONAL_HOURS
import com.flinesoft.fitnesstracker.globals.extensions.database
import com.flinesoft.fitnesstracker.model.Workout
import org.joda.time.DateTime
import kotlin.time.ExperimentalTime

@ExperimentalTime
class WorkoutsViewModel(application: Application) : AndroidViewModel(application) {
    val workouts: LiveData<List<Workout>> = database().workoutDao.allOrderedByEndDateDescending()

    fun suggestedNextWorkoutDateString(context: Context): String = DateUtils.getRelativeTimeSpanString(
        suggestedNextWorkoutDate().millis,
        DateTime.now().millis,
        DateUtils.DAY_IN_MILLIS
    ).toString()

    private fun suggestedNextWorkoutDate(): DateTime {
        val delayByDays: Int = 0 // TODO: read data from shared prefrences (last postpone button value)
        return workouts.value?.firstOrNull()?.let { latestWorkout ->
            latestWorkout.endDate
                .plusDays(delayByDays)
                .plus(latestWorkout.recoveryDuration.toLongMilliseconds())
                .plusHours(WORKOUT_RECOMMENDATION_ADDITIONAL_HOURS)
        } ?: DateTime.now()
    }
}
