package com.flinesoft.fitnesstracker.ui.workouts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.flinesoft.fitnesstracker.R
import com.flinesoft.fitnesstracker.globals.DEFAULT_WORKOUT_DURATION_MINUTES
import com.flinesoft.fitnesstracker.globals.MAX_WORKOUT_DURATION_HOURS
import com.flinesoft.fitnesstracker.globals.extensions.database
import com.flinesoft.fitnesstracker.model.Workout
import org.joda.time.DateTime
import timber.log.Timber
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds

@ExperimentalTime
class EditWorkoutViewModel(application: Application) : AndroidViewModel(application) {
    var existingWorkoutId: Long? = null
    var workoutType = Workout.Type.CARDIO
    var startDate = MutableLiveData<DateTime>(DateTime.now().minusMinutes(DEFAULT_WORKOUT_DURATION_MINUTES))
    var endDate = MutableLiveData<DateTime>(DateTime.now())

    fun updateStartDate(year: Int, month: Int, day: Int) {
        startDate.value = startDate.value!!.withYear(year).withMonthOfYear(month).withDayOfMonth(day)

        // auto-correct end date on start date change
        updateEndDate(year, month, day)
        if (endDate.value!! < startDate.value!!) {
            endDate.value = endDate.value!!.plusDays(1)
        }
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
            getApplication<Application>().getString(R.string.models_workout_type_cardio) ->
                workoutType = Workout.Type.CARDIO

            getApplication<Application>().getString(R.string.models_workout_type_muscle_building) ->
                workoutType = Workout.Type.MUSCLE_BUILDING

            else -> Timber.e("Found unexpected spinner item localized string: $spinnerItemLocalizedString")
        }
    }

    suspend fun save(): Boolean {
        if (dateIsFuture() || invalidDuration()) return false

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

        return true
    }

    private fun dateIsFuture(): Boolean = startDate.value!! > DateTime.now() || endDate.value!! > DateTime.now()
    private fun invalidDuration(): Boolean = workoutDuration().inHours < 0 || workoutDuration().inHours > MAX_WORKOUT_DURATION_HOURS
    private fun workoutDuration(): Duration = (endDate.value!!.millis - startDate.value!!.millis).milliseconds
}
