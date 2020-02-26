package com.flinesoft.fitnesstracker.ui.statistics.editMeasurements

import android.app.Application
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.flinesoft.fitnesstracker.model.Measurement
import kotlin.time.ExperimentalTime

@ExperimentalTime
class EditMeasurementsAdapter<T : Measurement>(
    private val application: Application,
    private val measurements: LiveData<List<T>>,
    private val itemOnClickListener: View.OnClickListener,
    private val itemOnLongClickListener: View.OnLongClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    @ExperimentalTime
    class MeasurementViewHolder(val cell: EditMeasurementsCell) : RecyclerView.ViewHolder(cell)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val cell = EditMeasurementsCell(parent.context).apply {
            layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setOnClickListener(itemOnClickListener)
            setOnLongClickListener(itemOnLongClickListener)
        }
        return MeasurementViewHolder(cell)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val measurement = measurements.value!![position] as T
        val viewModel = EditMeasurementsCellViewModel(application = application, measurement = measurement)
        (holder as MeasurementViewHolder).cell.updateViewModel(viewModel)
    }

    override fun getItemCount(): Int = measurements.value?.size ?: 0
}
