package com.flinesoft.fitnesstracker.ui.statistics.editMeasurements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.flinesoft.fitnesstracker.databinding.EditMeasurementsFragmentBinding
import com.flinesoft.fitnesstracker.model.WeightMeasurement
import kotlin.time.ExperimentalTime

@UseExperimental(ExperimentalTime::class)
class EditWeightMeasurementsFragment : EditMeasurementsFragment<WeightMeasurement>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = EditMeasurementsFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(EditWeightMeasurementsViewModel::class.java)

        setupBackNavigation()
        setupRecyclerView()
        setupViewModelBinding()

        return binding.root
    }
}
