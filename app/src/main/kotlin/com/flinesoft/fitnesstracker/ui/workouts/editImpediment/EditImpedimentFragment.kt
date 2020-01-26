package com.flinesoft.fitnesstracker.ui.workouts.editImpediment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.flinesoft.fitnesstracker.R
import com.flinesoft.fitnesstracker.databinding.EditImpedimentFragmentBinding
import com.flinesoft.fitnesstracker.globals.extensions.DateFormatExt
import com.flinesoft.fitnesstracker.globals.extensions.database
import com.flinesoft.fitnesstracker.globals.extensions.showDatePickerDialog
import com.flinesoft.fitnesstracker.globals.extensions.snack
import com.flinesoft.fitnesstracker.ui.shared.BackNavigationFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime

@ExperimentalTime
class EditImpedimentFragment : BackNavigationFragment() {
    private lateinit var binding: EditImpedimentFragmentBinding
    private lateinit var viewModel: EditImpedimentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = EditImpedimentFragmentBinding.inflate(inflater)
        viewModel = ViewModelProviders.of(this).get(EditImpedimentViewModel::class.java)

        setupViewModelWithArguments()
        setupBackNavigation()
        setupViewModelBinding()
        setupListeners()

        return binding.root
    }

    private fun setupViewModelWithArguments() {
        val editImpedimentFragmentArgs: EditImpedimentFragmentArgs by navArgs()
        if (editImpedimentFragmentArgs.existingImpedimentId >= 0) {
            viewModel.existingImpediment = database().impedimentDao.read(editImpedimentFragmentArgs.existingImpedimentId)
        }
    }

    private fun setupViewModelBinding() {
        viewModel.startDate.observe(this, Observer { startDate ->
            binding.startDateEditText.setText(DateFormatExt.dateMedium().format(startDate.toDate()))
        })

        viewModel.endDate.observe(this, Observer { endDate ->
            binding.endDateEditText.setText(DateFormatExt.dateMedium().format(endDate.toDate()))
        })
    }

    private fun setupListeners() {
        binding.cancelButton.setOnClickListener { cancelButtonPressed() }
        binding.saveButton.setOnClickListener { saveButtonPressed() }

        binding.startDateEditText.setOnClickListener {
            showDatePickerDialog(
                date = viewModel.startDate.value!!,
                // NOTE: `month + 1` is correct, see documentation: https://developer.android.com/reference/android/app/DatePickerDialog.OnDateSetListener
                listener = DatePickerDialog.OnDateSetListener { _, year, month, day -> viewModel.updateStartDate(year, month + 1, day) }
            )
        }

        binding.endDateEditText.setOnClickListener {
            showDatePickerDialog(
                date = viewModel.endDate.value!!,
                // NOTE: `month + 1` is correct, see documentation: https://developer.android.com/reference/android/app/DatePickerDialog.OnDateSetListener
                listener = DatePickerDialog.OnDateSetListener { _, year, month, day -> viewModel.updateEndDate(year, month + 1, day) }
            )
        }
    }

    private fun cancelButtonPressed() = findNavController().navigateUp()

    private fun saveButtonPressed() {
        GlobalScope.launch {
            val saveSuccess = viewModel.save()

            MainScope().launch {
                if (saveSuccess) {
                    findNavController().navigateUp()
                } else {
                    view?.snack(R.string.global_error_invalid_input)
                }
            }
        }
    }
}
