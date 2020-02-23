package com.flinesoft.fitnesstracker.ui.statistics.editMeasurements

import android.app.Application
import androidx.lifecycle.LiveData
import com.flinesoft.fitnesstracker.globals.extensions.database
import com.flinesoft.fitnesstracker.model.WaistCircumferenceMeasurement
import kotlin.time.ExperimentalTime

@ExperimentalTime
class EditWaistCircumferenceMeasurementsViewModel(application: Application) : EditMeasurementsViewModel<WaistCircumferenceMeasurement>(application) {
    override val measurements: LiveData<List<WaistCircumferenceMeasurement>> = database().waistCircumferenceMeasurementDao.allOrderedByMeasureDate()
}
