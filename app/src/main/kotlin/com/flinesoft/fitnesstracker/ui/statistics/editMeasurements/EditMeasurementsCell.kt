package com.flinesoft.fitnesstracker.ui.statistics.editMeasurements

import android.content.Context
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.flinesoft.fitnesstracker.R
import com.flinesoft.fitnesstracker.model.Measurement
import kotlinx.android.synthetic.main.edit_measurements_cell.view.*
import kotlin.time.ExperimentalTime

@ExperimentalTime
class EditMeasurementsCell(context: Context) : ConstraintLayout(context) {
    init {
        View.inflate(context, R.layout.edit_measurements_cell, this)
    }

    fun <T : Measurement> updateViewModel(viewModel: EditMeasurementsCellViewModel<T>) {
        valueTextView.text = viewModel.valueText
        measureDateTextView.text = viewModel.measureDateText
    }
}
