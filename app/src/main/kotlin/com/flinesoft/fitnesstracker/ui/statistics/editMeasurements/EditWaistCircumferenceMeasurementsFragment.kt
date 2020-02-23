package com.flinesoft.fitnesstracker.ui.statistics.editMeasurements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.flinesoft.fitnesstracker.databinding.EditMeasurementsFragmentBinding
import com.flinesoft.fitnesstracker.model.WaistCircumferenceMeasurement
import kotlin.time.ExperimentalTime

@UseExperimental(ExperimentalTime::class)
class EditWaistCircumferenceMeasurementsFragment : EditMeasurementsFragment<WaistCircumferenceMeasurement>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = EditMeasurementsFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(EditWaistCircumferenceMeasurementsViewModel::class.java)

        setupBackNavigation()
        setupRecyclerView()
        setupViewModelBinding()

        return binding.root
    }
}
