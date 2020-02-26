package com.flinesoft.fitnesstracker.ui.statistics.editMeasurements

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.flinesoft.fitnesstracker.model.Measurement
import kotlin.time.ExperimentalTime

@ExperimentalTime
abstract class EditMeasurementsViewModel<T : Measurement>(application: Application) : AndroidViewModel(application) {
    abstract val measurements: LiveData<List<T>>
}
