package com.flinesoft.fitnesstracker.ui.workouts

import android.app.Application
import android.content.Context
import com.flinesoft.fitnesstracker.R
import com.flinesoft.fitnesstracker.model.Impediment
import com.flinesoft.fitnesstracker.model.Recoverable
import kotlin.math.roundToInt
import kotlin.time.ExperimentalTime

@ExperimentalTime
class WorkoutsHistoryImpedimentCellViewModel(
    application: Application,
    override val recoverable: Impediment,
    override val recoverableAbove: Recoverable?
) : WorkoutsHistoryRecoverableCellViewModel<Impediment>(
    application,
    recoverable,
    recoverableAbove
) {
    fun timeIntervalText(context: Context): String = context.resources.getQuantityString(
        R.plurals.global_duration_days,
        recoverable.recoveryDuration.inDays.roundToInt(),
        recoverable.recoveryDuration.inDays.roundToInt()
    )
}
