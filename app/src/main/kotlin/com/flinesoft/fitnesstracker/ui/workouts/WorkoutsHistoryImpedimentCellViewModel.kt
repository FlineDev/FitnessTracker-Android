package com.flinesoft.fitnesstracker.ui.workouts

import android.app.Application
import android.content.Context
import com.flinesoft.fitnesstracker.R
import com.flinesoft.fitnesstracker.model.Impediment
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
class WorkoutsHistoryImpedimentCellViewModel(
    application: Application,
    override val recoverable: Impediment,
    override val betweenRecoverablesDuration: Duration
) : WorkoutsHistoryRecoverableCellViewModel<Impediment>(application, recoverable, betweenRecoverablesDuration) {
    fun timeIntervalText(context: Context): String = context.resources.getQuantityString(
        R.plurals.global_duration_days,
        recoverable.recoveryDuration.inDays.toInt() + 1,
        recoverable.recoveryDuration.inDays.toInt() + 1
    )
}
