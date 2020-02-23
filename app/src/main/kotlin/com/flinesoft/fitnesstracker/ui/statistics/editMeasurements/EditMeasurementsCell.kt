package com.flinesoft.fitnesstracker.ui.statistics.editMeasurements

import android.content.Context
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.flinesoft.fitnesstracker.R
import kotlin.time.ExperimentalTime

@ExperimentalTime
class EditMeasurementsCell(context: Context) : ConstraintLayout(context) {
    init {
        View.inflate(context, R.layout.edit_measurements_cell, this)
    }

    fun <T> updateViewModel(viewModel: EditMeasurementsCellViewModel<T>) {
        // TODO: [2020-02-23] not yet implemented
    }
}
