package com.flinesoft.fitnesstracker.ui.workouts

import android.content.Context
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.flinesoft.fitnesstracker.R
import com.flinesoft.fitnesstracker.databinding.WorkoutsHistoryCellBinding
import kotlin.time.ExperimentalTime

@ExperimentalTime
class WorkoutsHistoryCell(context: Context) : ConstraintLayout(context) {
    private var binding: WorkoutsHistoryCellBinding

    init {
        View.inflate(context, R.layout.workouts_history_cell, this)
        binding = WorkoutsHistoryCellBinding.bind(this)
    }

    fun updateViewModel(viewModel: WorkoutsHistoryCellViewModel) {
        binding.betweenWorkoutsIconImageView.setImageDrawable(viewModel.betweenWorkoutsIconDrawable())
        binding.betweenWorkoutsTextView.text = viewModel.betweenWorkoutsDuration.toComponents { days, hours, _, _, _ ->
            val daysString = context.resources.getQuantityString(R.plurals.global_duration_days, days, days)
            val hoursString = context.resources.getQuantityString(R.plurals.global_duration_hours, hours, days)
            context.getString(R.string.workouts_history_cell_between_workouts_duration, daysString, hoursString)
        }
    }
}
