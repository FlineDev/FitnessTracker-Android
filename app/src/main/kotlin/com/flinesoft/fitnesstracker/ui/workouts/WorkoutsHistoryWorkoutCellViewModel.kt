package com.flinesoft.fitnesstracker.ui.workouts

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.flinesoft.fitnesstracker.R
import com.flinesoft.fitnesstracker.globals.extensions.DateFormatExt
import com.flinesoft.fitnesstracker.model.Workout
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
class WorkoutsHistoryWorkoutCellViewModel(
    application: Application,
    override val recoverable: Workout,
    override val betweenRecoverablesDuration: Duration,
    override val hideBetweenRecoverablesEntry: Boolean
) : WorkoutsHistoryRecoverableCellViewModel<Workout>(
    application,
    recoverable,
    betweenRecoverablesDuration,
    hideBetweenRecoverablesEntry
) {
    fun workoutTypeIconDrawable(context: Context): Drawable? = when (recoverable.type) {
        Workout.Type.CARDIO ->
            ContextCompat.getDrawable(context, R.drawable.ic_workouts_cardio)

        Workout.Type.MUSCLE_BUILDING ->
            ContextCompat.getDrawable(context, R.drawable.ic_workouts_muscle_building)
    }

    fun workoutTypeText(context: Context): String = when (recoverable.type) {
        Workout.Type.CARDIO ->
            context.getString(R.string.models_workout_type_cardio)

        Workout.Type.MUSCLE_BUILDING ->
            context.getString(R.string.models_workout_type_muscle_building)
    }

    fun timeText(context: Context): String = context.getString(
        R.string.global_duration_time_interval,
        DateFormatExt.timeShort().format(recoverable.startDate.toDate()),
        DateFormatExt.timeShort().format(recoverable.endDate.toDate())
    )
}
