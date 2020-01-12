package com.flinesoft.fitnesstracker.ui.statistics.editPersonalData

import android.icu.util.MeasureUnit
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.flinesoft.fitnesstracker.R
import com.flinesoft.fitnesstracker.databinding.EditPersonalDataBinding
import com.flinesoft.fitnesstracker.globals.HUMAN_AGE_RANGE
import com.flinesoft.fitnesstracker.globals.HUMAN_HEIGHT_RANGE_IN_CENTIMETERS
import com.flinesoft.fitnesstracker.globals.extensions.MeasureFormatExt
import com.flinesoft.fitnesstracker.globals.extensions.intToString
import com.flinesoft.fitnesstracker.globals.extensions.showNumberPickerDialog
import com.flinesoft.fitnesstracker.globals.extensions.snack
import com.flinesoft.fitnesstracker.ui.shared.BackNavigationFragment
import org.joda.time.DateTime
import kotlin.time.ExperimentalTime

@ExperimentalTime
class EditPersonalDataFragment : BackNavigationFragment() {
    private lateinit var binding: EditPersonalDataBinding
    private lateinit var viewModel: EditPersonalDataViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = EditPersonalDataBinding.inflate(inflater)
        viewModel = ViewModelProviders.of(this).get(EditPersonalDataViewModel::class.java)

        setupBackNavigation()
        setupViewModelBinding()
        setupListeners()

        return binding.root
    }

    private fun setupViewModelBinding() {
        viewModel.heightInCentimeters.observe(this, Observer { heightInCentimeters ->
            heightInCentimeters?.let { binding.heightEditText.setText(it.toString()) } ?: run { binding.heightEditText.text?.clear() }
        })

        viewModel.gender.observe(this, Observer { gender ->
            gender?.let { binding.genderSpinner.setSelection(gender.ordinal) }
        })

        viewModel.birthYear.observe(this, Observer { birthYear ->
            birthYear?.let { binding.birthYearEditText.setText(it.toString()) } ?: run { binding.birthYearEditText.text?.clear() }
        })
    }

    private fun setupListeners() {
        binding.cancelButton.setOnClickListener { cancelButtonPressed() }
        binding.saveButton.setOnClickListener { saveButtonPressed() }

        binding.heightEditText.setOnClickListener {
            showNumberPickerDialog(
                title = getString(R.string.statistics_edit_personal_data_height_input_dialog_title),
                value = viewModel.heightInCentimeters.value ?: 170,
                range = HUMAN_HEIGHT_RANGE_IN_CENTIMETERS,
                formatToString = { MeasureFormatExt.short().intToString(it, MeasureUnit.CENTIMETER) },
                valueChooseAction = { viewModel.heightInCentimeters.value = it }
            )
        }

        binding.genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                parent?.getItemAtPosition(position).toString().let { viewModel.updateGender(it) }
            }
        }

        binding.birthYearEditText.setOnClickListener {
            showNumberPickerDialog(
                title = getString(R.string.statistics_edit_personal_data_birth_year_input_dialog_title),
                value = viewModel.birthYear.value ?: 1990,
                range = HUMAN_AGE_RANGE.let { (DateTime.now().year - it.last)..(DateTime.now().year - it.first) },
                formatToString = { it.toString() },
                valueChooseAction = { viewModel.birthYear.value = it }
            )
        }
    }

    private fun cancelButtonPressed() = findNavController().navigateUp()
    private fun saveButtonPressed() = if (viewModel.save()) findNavController().navigateUp() else view?.snack(R.string.global_error_invalid_input)
}
