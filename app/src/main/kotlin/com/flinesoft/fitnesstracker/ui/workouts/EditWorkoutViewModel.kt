package com.flinesoft.fitnesstracker.ui.workouts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.flinesoft.fitnesstracker.R
import com.flinesoft.fitnesstracker.globals.extensions.database
import com.flinesoft.fitnesstracker.model.Workout
import org.joda.time.DateTime
import timber.log.Timber
import kotlin.time.ExperimentalTime

@ExperimentalTime
class EditWorkoutViewModel(application: Application) : AndroidViewModel(application) {
    var existingWorkoutId: Long? = null
    var workoutType = Workout.Type.CARDIO
    var startDate = MutableLiveData<DateTime>(DateTime.now().minusHours(1))
    var endDate = MutableLiveData<DateTime>(DateTime.now())

    fun updateStartDate(year: Int, month: Int, day: Int) {
        startDate.value = startDate.value!!.withYear(year).withMonthOfYear(month).withDayOfMonth(day)
    }

    fun updateStartTime(hour: Int, minute: Int) {
        startDate.value = startDate.value!!.withHourOfDay(hour).withMinuteOfHour(minute)
    }

    fun updateEndDate(year: Int, month: Int, day: Int) {
        endDate.value = endDate.value!!.withYear(year).withMonthOfYear(month).withDayOfMonth(day)
    }

    fun updateEndTime(hour: Int, minute: Int) {
        endDate.value = endDate.value!!.withHourOfDay(hour).withMinuteOfHour(minute)
    }

    fun updateWorkoutType(spinnerItemLocalizedString: String) {
        when (spinnerItemLocalizedString) {
            getApplication<Application>().getString(R.string.workouts_edit_workout_workout_type_cardio) ->
                workoutType = Workout.Type.CARDIO

            getApplication<Application>().getString(R.string.workouts_edit_workout_workout_type_muscle_building) ->
                workoutType = Workout.Type.MUSCLE_BUILDING

            else -> Timber.e("Found unexpected spinner item localized string: $spinnerItemLocalizedString")
        }
    }

    suspend fun save() {
        if (existingWorkoutId == null) {
            database().workoutDao.create(
                Workout(
                    type = workoutType,
                    startDate = startDate.value!!,
                    endDate = endDate.value!!
                )
            )
        } else {
            // TODO: updating exisitng objects not yet implemented
        }
    }
}
