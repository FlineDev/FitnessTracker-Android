package com.flinesoft.fitnesstracker.ui.statistics.editMeasurements

import android.app.Application
import androidx.lifecycle.LiveData
import com.flinesoft.fitnesstracker.globals.extensions.database
import com.flinesoft.fitnesstracker.model.WeightMeasurement
import kotlin.time.ExperimentalTime

@ExperimentalTime
class EditWeightMeasurementsViewModel(application: Application) : EditMeasurementsViewModel<WeightMeasurement>(application) {
    override val measurements: LiveData<List<WeightMeasurement>> = database().weightMeasurementDao.allOrderedByMeasureDateDescending()
}
