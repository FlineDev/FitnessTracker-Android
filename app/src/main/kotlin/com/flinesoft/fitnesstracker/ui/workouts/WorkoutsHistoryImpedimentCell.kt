package com.flinesoft.fitnesstracker.ui.workouts

import android.content.Context
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.flinesoft.fitnesstracker.R
import kotlinx.android.synthetic.main.workouts_history_impediment_cell.view.*
import kotlinx.android.synthetic.main.workouts_history_workout_cell.view.betweenWorkoutsIconImageView
import kotlinx.android.synthetic.main.workouts_history_workout_cell.view.betweenWorkoutsTextView
import kotlinx.android.synthetic.main.workouts_history_workout_cell.view.dayTextView
import kotlinx.android.synthetic.main.workouts_history_workout_cell.view.monthTextView
import kotlin.time.ExperimentalTime

@ExperimentalTime
class WorkoutsHistoryImpedimentCell(context: Context) : ConstraintLayout(context) {
    init {
        View.inflate(context, R.layout.workouts_history_impediment_cell, this)
    }

    fun updateViewModel(viewModel: WorkoutsHistoryImpedimentCellViewModel) {
        betweenWorkoutsIconImageView.setImageDrawable(viewModel.betweenRecoverablesIconDrawable())
        betweenWorkoutsTextView.text = viewModel.betweenRecoverablesText(context)

        monthTextView.text = viewModel.monthText()
        dayTextView.text = viewModel.dayText()

        timeIntervalTextView.text = viewModel.timeIntervalText(context)
    }
}
