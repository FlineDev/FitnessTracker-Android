package com.flinesoft.fitnesstracker.ui.workouts

import android.content.Context
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import com.flinesoft.fitnesstracker.R
import kotlinx.android.synthetic.main.workouts_history_workout_cell.view.*
import kotlin.time.ExperimentalTime

@ExperimentalTime
class WorkoutsHistoryWorkoutCell(context: Context) : ConstraintLayout(context) {
    init {
        View.inflate(context, R.layout.workouts_history_workout_cell, this)
    }

    fun updateViewModel(viewModel: WorkoutsHistoryWorkoutCellViewModel) {
        betweenWorkoutsIconImageView.setImageDrawable(viewModel.betweenRecoverablesIconDrawable())
        betweenWorkoutsTextView.text = viewModel.betweenRecoverablesText(context)

        betweenWorkoutsEntry.isGone = viewModel.hideBetweenRecoverablesEntry()

        monthTextView.text = viewModel.monthText()
        dayTextView.text = viewModel.dayText()

        workoutTypeIconImageView.setImageDrawable(viewModel.workoutTypeIconDrawable(context))
        workoutTypeTextView.text = viewModel.workoutTypeText(context)
        timeTextView.text = viewModel.timeText(context)
    }
}
