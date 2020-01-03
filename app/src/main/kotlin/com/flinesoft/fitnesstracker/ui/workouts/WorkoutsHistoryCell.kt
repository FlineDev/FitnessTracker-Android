package com.flinesoft.fitnesstracker.ui.workouts

import android.content.Context
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.flinesoft.fitnesstracker.R
import kotlinx.android.synthetic.main.workouts_history_cell.view.*
import kotlin.time.ExperimentalTime

@ExperimentalTime
class WorkoutsHistoryCell(context: Context) : ConstraintLayout(context) {
    init {
        View.inflate(context, R.layout.workouts_history_cell, this)
    }

    fun updateViewModel(viewModel: WorkoutsHistoryCellViewModel) {
        betweenWorkoutsIconImageView.setImageDrawable(viewModel.betweenWorkoutsIconDrawable())
        betweenWorkoutsTextView.text = viewModel.betweenWorkoutsText(context)

        monthTextView.text = viewModel.monthText()
        dayTextView.text = viewModel.dayText()

        workoutTypeIconImageView.setImageDrawable(viewModel.workoutTypeIconDrawable(context))
        workoutTypeTextView.text = viewModel.workoutTypeText(context)
        timeTextView.text = viewModel.timeText(context)
    }
}
