package com.flinesoft.fitnesstracker.ui.workouts

import android.app.Application
import android.content.Context
import com.flinesoft.fitnesstracker.R
import com.flinesoft.fitnesstracker.model.Impediment
import kotlin.math.roundToInt
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
class WorkoutsHistoryImpedimentCellViewModel(
    application: Application,
    override val recoverable: Impediment,
    override val betweenRecoverablesDuration: Duration,
    override val hideBetweenRecoverablesEntry: Boolean
) : WorkoutsHistoryRecoverableCellViewModel<Impediment>(
    application,
    recoverable,
    betweenRecoverablesDuration,
    hideBetweenRecoverablesEntry
) {
    fun timeIntervalText(context: Context): String = context.resources.getQuantityString(
        R.plurals.global_duration_days,
        recoverable.recoveryDuration.inDays.roundToInt(),
        recoverable.recoveryDuration.inDays.roundToInt()
    )
}
