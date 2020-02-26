package com.flinesoft.fitnesstracker.ui.workouts

import android.app.Application
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.flinesoft.fitnesstracker.model.Impediment
import com.flinesoft.fitnesstracker.model.Recoverable
import com.flinesoft.fitnesstracker.model.Workout
import timber.log.Timber
import kotlin.time.ExperimentalTime

@ExperimentalTime
class WorkoutsHistoryAdapter(
    private val application: Application,
    val recoverables: LiveData<List<Recoverable>>,
    private val itemOnClickListener: View.OnClickListener,
    private val itemOnLongClickListener: View.OnLongClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    enum class ViewType { WORKOUT_CELL, IMPEDIMENT_CELL }

    @ExperimentalTime
    class WorkoutViewHolder(val cell: WorkoutsHistoryWorkoutCell) : RecyclerView.ViewHolder(cell)
    @ExperimentalTime
    class ImpedimentViewHolder(val cell: WorkoutsHistoryImpedimentCell) : RecyclerView.ViewHolder(cell)

    override fun getItemViewType(position: Int): Int = when (recoverables.value!![position]) {
        is Workout -> ViewType.WORKOUT_CELL.ordinal
        is Impediment -> ViewType.IMPEDIMENT_CELL.ordinal
        else -> Timber.e("Unexpected type of recoverable found in position $position").let { -1 }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (ViewType.values()[viewType]) {
            ViewType.WORKOUT_CELL -> {
                val cell = WorkoutsHistoryWorkoutCell(parent.context).apply {
                    layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    setOnClickListener(itemOnClickListener)
                    setOnLongClickListener(itemOnLongClickListener)
                }
                return WorkoutViewHolder(cell)
            }

            ViewType.IMPEDIMENT_CELL -> {
                val cell = WorkoutsHistoryImpedimentCell(parent.context).apply {
                    layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    setOnClickListener(itemOnClickListener)
                    setOnLongClickListener(itemOnLongClickListener)
                }
                return ImpedimentViewHolder(cell)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is WorkoutViewHolder -> {
                val workout = recoverables.value!![position] as Workout
                val recoverableAbove: Recoverable? = if (position > 0) recoverables.value!![position - 1] else null

                val viewModel = WorkoutsHistoryWorkoutCellViewModel(
                    application = application,
                    recoverable = workout,
                    recoverableAbove = recoverableAbove
                )
                holder.cell.updateViewModel(viewModel)
            }

            is ImpedimentViewHolder -> {
                val impediment = recoverables.value!![position] as Impediment
                val recoverableAbove: Recoverable? = if (position > 0) recoverables.value!![position - 1] else null

                val viewModel = WorkoutsHistoryImpedimentCellViewModel(
                    application = application,
                    recoverable = impediment,
                    recoverableAbove = recoverableAbove
                )
                holder.cell.updateViewModel(viewModel)
            }
        }
    }

    override fun getItemCount(): Int = recoverables.value?.size ?: 0
}
