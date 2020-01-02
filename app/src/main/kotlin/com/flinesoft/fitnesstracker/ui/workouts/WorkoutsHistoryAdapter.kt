package com.flinesoft.fitnesstracker.ui.workouts

import android.app.Application
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.flinesoft.fitnesstracker.model.Workout
import org.joda.time.DateTime
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds

@ExperimentalTime
class WorkoutsHistoryAdapter(
    private val application: Application,
    val workouts: LiveData<List<Workout>>
) : RecyclerView.Adapter<WorkoutsHistoryAdapter.WorkoutsHistoryViewHolder>() {
    @ExperimentalTime
    class WorkoutsHistoryViewHolder(val cell: WorkoutsHistoryCell) : RecyclerView.ViewHolder(cell)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutsHistoryViewHolder {
        val cell = WorkoutsHistoryCell(parent.context)
        // TODO: setup layout parameters
        return WorkoutsHistoryViewHolder(cell)
    }

    override fun onBindViewHolder(holder: WorkoutsHistoryViewHolder, position: Int) {
        val workout: Workout = workouts.value!![position]
        val startDateOfNextWorkout: DateTime = if (position > 0) workouts.value!![position - 1].startDate else DateTime.now()
        val betweenWorkoutsDuration: Duration = (startDateOfNextWorkout.millis - workout.endDate.millis).milliseconds

        val viewModel = WorkoutsHistoryCellViewModel(
            application = application,
            workout = workout,
            betweenWorkoutsDuration = betweenWorkoutsDuration
        )
        holder.cell.updateViewModel(viewModel)
    }

    override fun getItemCount(): Int = workouts.value?.size ?: 0
}
