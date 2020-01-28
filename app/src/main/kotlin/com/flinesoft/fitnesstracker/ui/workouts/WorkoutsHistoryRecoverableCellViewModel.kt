package com.flinesoft.fitnesstracker.ui.workouts

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.flinesoft.fitnesstracker.R
import com.flinesoft.fitnesstracker.model.Recoverable
import java.text.DateFormatSymbols
import java.util.*
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
abstract class WorkoutsHistoryRecoverableCellViewModel<T : Recoverable>(
    application: Application,
    open val recoverable: T,
    open val betweenRecoverablesDuration: Duration,
    open val hideBetweenRecoverablesEntry: Boolean
) : AndroidViewModel(application) {
    fun betweenRecoverablesIconDrawable(): Drawable? = when (recoverable.betweenRecoverablesDurationRating(betweenRecoverablesDuration)) {
        Recoverable.BetweenDurationRating.POSITIVE ->
            ContextCompat.getDrawable(getApplication(), R.drawable.ic_workouts_time_between_positive)

        Recoverable.BetweenDurationRating.WARNING ->
            ContextCompat.getDrawable(getApplication(), R.drawable.ic_workouts_time_between_warning)

        Recoverable.BetweenDurationRating.NEGATIVE ->
            ContextCompat.getDrawable(getApplication(), R.drawable.ic_workouts_time_between_negative)
    }

    fun betweenRecoverablesText(context: Context): String = if (betweenRecoverablesDuration.isPositive()) {
        betweenRecoverablesDuration.toComponents { days, hours, _, _, _ ->
            val daysString = context.resources.getQuantityString(R.plurals.global_duration_days, days, days)
            val hoursString = context.resources.getQuantityString(R.plurals.global_duration_hours, hours, hours)
            context.getString(R.string.workouts_history_cell_between_workouts_duration, daysString, hoursString)
        }
    } else {
        betweenRecoverablesDuration.absoluteValue.toComponents { days, hours, _, _, _ ->
            val daysString = context.resources.getQuantityString(R.plurals.global_duration_days, days, days)
            val hoursString = context.resources.getQuantityString(R.plurals.global_duration_hours, hours, hours)
            context.getString(R.string.workouts_history_cell_between_workouts_duration_overlapping, daysString, hoursString)
        }
    }

    fun monthText(): String = DateFormatSymbols().shortMonths[recoverable.startDate.monthOfYear - 1].substring(0..2).toUpperCase(Locale.getDefault())
    fun dayText(): String = recoverable.startDate.dayOfMonth.toString().padStart(2, '0')
}
