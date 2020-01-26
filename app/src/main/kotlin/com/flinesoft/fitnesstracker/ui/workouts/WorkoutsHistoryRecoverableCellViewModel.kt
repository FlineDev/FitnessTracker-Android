package com.flinesoft.fitnesstracker.ui.workouts

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.flinesoft.fitnesstracker.R
import com.flinesoft.fitnesstracker.globals.BETWEEN_WORKOUTS_POSITIVE_DAYS
import com.flinesoft.fitnesstracker.globals.BETWEEN_WORKOUTS_POSITIVE_PLUS_WARNING_DAYS
import com.flinesoft.fitnesstracker.model.Recoverable
import java.text.DateFormatSymbols
import java.util.*
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.days
import kotlin.time.hours

@ExperimentalTime
abstract class WorkoutsHistoryRecoverableCellViewModel<T : Recoverable>(
    application: Application,
    open val recoverable: T,
    open val betweenRecoverablesDuration: Duration
) : AndroidViewModel(application) {
    fun betweenRecoverablesIconDrawable(): Drawable? = when (betweenRecoverablesDuration) {
        in 0.hours..recoverable.recoveryDuration.div(2) ->
            ContextCompat.getDrawable(getApplication(), R.drawable.ic_workouts_time_between_negative)

        in recoverable.recoveryDuration.div(2)..recoverable.recoveryDuration ->
            ContextCompat.getDrawable(getApplication(), R.drawable.ic_workouts_time_between_warning)

        in recoverable.recoveryDuration..(recoverable.recoveryDuration + BETWEEN_WORKOUTS_POSITIVE_DAYS.days) ->
            ContextCompat.getDrawable(getApplication(), R.drawable.ic_workouts_time_between_positive)

        in (recoverable.recoveryDuration + BETWEEN_WORKOUTS_POSITIVE_DAYS.days)
                ..(recoverable.recoveryDuration + BETWEEN_WORKOUTS_POSITIVE_PLUS_WARNING_DAYS.days) ->
            ContextCompat.getDrawable(getApplication(), R.drawable.ic_workouts_time_between_warning)

        else ->
            ContextCompat.getDrawable(getApplication(), R.drawable.ic_workouts_time_between_negative)
    }

    fun betweenRecoverablesText(context: Context): String = betweenRecoverablesDuration.toComponents { days, hours, _, _, _ ->
        val daysString = context.resources.getQuantityString(R.plurals.global_duration_days, days, days)
        val hoursString = context.resources.getQuantityString(R.plurals.global_duration_hours, hours, hours)
        context.getString(R.string.workouts_history_cell_between_workouts_duration, daysString, hoursString)
    }

    fun monthText(): String = DateFormatSymbols().shortMonths[recoverable.startDate.monthOfYear - 1].substring(0..2).toUpperCase(Locale.getDefault())
    fun dayText(): String = recoverable.startDate.dayOfMonth.toString().padStart(2, '0')
}
