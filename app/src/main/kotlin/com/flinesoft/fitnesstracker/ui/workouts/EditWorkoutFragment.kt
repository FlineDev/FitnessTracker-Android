package com.flinesoft.fitnesstracker.ui.workouts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.flinesoft.fitnesstracker.databinding.EditWorkoutFragmentBinding
import com.flinesoft.fitnesstracker.globals.BackNavigationFragment
import kotlin.time.ExperimentalTime

@ExperimentalTime
class EditWorkoutFragment : BackNavigationFragment() {
    private lateinit var binding: EditWorkoutFragmentBinding
    private lateinit var viewModel: EditWorkoutViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = EditWorkoutFragmentBinding.inflate(inflater)
        viewModel = ViewModelProviders.of(this).get(EditWorkoutViewModel::class.java)

        setupBackNavigation()
        setupOnClickListeners()

        return binding.root
    }

    private fun setupOnClickListeners() {
        binding.cancelButton.setOnClickListener { cancelButtonPressed() }
        binding.saveButton.setOnClickListener { saveButtonPressed() }
    }

    private fun cancelButtonPressed() {
        findNavController().navigateUp()
    }

    private fun saveButtonPressed() {
        // TODO: not yet implemented
    }
}
