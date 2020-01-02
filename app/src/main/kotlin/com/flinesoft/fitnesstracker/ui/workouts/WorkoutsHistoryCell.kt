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
        binding.betweenWorkoutsTextView.text = viewModel.betweenWorkoutsText(context)

        binding.dayTextView.text = viewModel.dayText()
        binding.weekdayTextView.text = viewModel.weekDayText()

        binding.workoutTypeIconImageView.setImageDrawable(viewModel.workoutTypeIconDrawable(context))
        binding.workoutTypeTextView.text = viewModel.workoutTypeText(context)
        binding.timeTextView.text = viewModel.timeText(context)
    }
}
