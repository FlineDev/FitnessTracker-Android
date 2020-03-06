package com.flinesoft.fitnesstracker.ui.statistics.editMeasurements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.flinesoft.fitnesstracker.R
import com.flinesoft.fitnesstracker.databinding.EditMeasurementsFragmentBinding
import com.flinesoft.fitnesstracker.globals.extensions.database
import com.flinesoft.fitnesstracker.globals.extensions.showWaistCircumferencePickerDialog
import com.flinesoft.fitnesstracker.model.WaistCircumferenceMeasurement
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class EditWaistCircumferenceMeasurementsFragment : EditMeasurementsFragment<WaistCircumferenceMeasurement>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = EditMeasurementsFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(EditWaistCircumferenceMeasurementsViewModel::class.java)

        setupBackNavigation()
        setupRecyclerView()
        setupViewModelBinding()

        return binding.root
    }

    override fun showEditMeasurementForm(measurement: WaistCircumferenceMeasurement) {
        showWaistCircumferencePickerDialog(
            title = getString(R.string.statistics_edit_measurements_edit_value_title),
            value = measurement.value,
            valueChooseAction = {
                GlobalScope.launch {
                    measurement.circumferenceInCentimeters = it
                    database().waistCircumferenceMeasurementDao.update(measurement)
                }
            }
        )
    }
}
