package com.flinesoft.fitnesstracker.ui.statistics.editMeasurements

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.flinesoft.fitnesstracker.globals.extensions.DateFormatExt
import com.flinesoft.fitnesstracker.model.Measurement
import kotlin.time.ExperimentalTime

@ExperimentalTime
class EditMeasurementsCellViewModel<T : Measurement>(application: Application, val measurement: T) : AndroidViewModel(application) {
    val valueText: String
        get() = measurement.formattedValue()

    val measureDateText: String
        get() = DateFormatExt.dateMediumTimeShort().format(measurement.measureDate.toDate())
}
