package com.flinesoft.fitnesstracker.ui.workouts

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.flinesoft.fitnesstracker.databinding.EditWorkoutFragmentBinding
import com.flinesoft.fitnesstracker.globals.BackNavigationFragment
import com.flinesoft.fitnesstracker.globals.extensions.DateFormatExt
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import kotlin.time.ExperimentalTime

@ExperimentalTime
class EditWorkoutFragment : BackNavigationFragment() {
    private lateinit var binding: EditWorkoutFragmentBinding
    private lateinit var viewModel: EditWorkoutViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = EditWorkoutFragmentBinding.inflate(inflater)
        viewModel = ViewModelProviders.of(this).get(EditWorkoutViewModel::class.java)

        setupBackNavigation()
        setupViewModelBinding()
        setupOnClickListeners()

        return binding.root
    }

    private fun setupViewModelBinding() {
        viewModel.startDate.observe(this, androidx.lifecycle.Observer { startDate ->
            binding.startDateEditText.setText(DateFormatExt.dateMedium().format(startDate.toDate()))
            binding.startTimeEditText.setText(DateFormatExt.timeShort().format(startDate.toDate()))
        })

        viewModel.endDate.observe(this, androidx.lifecycle.Observer { endDate ->
            binding.endDateEditText.setText(DateFormatExt.dateMedium().format(endDate.toDate()))
            binding.endTimeEditText.setText(DateFormatExt.timeShort().format(endDate.toDate()))
        })
    }

    private fun setupOnClickListeners() {
        binding.cancelButton.setOnClickListener { cancelButtonPressed() }
        binding.saveButton.setOnClickListener { saveButtonPressed() }

        binding.workoutTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                parent?.getItemAtPosition(position).toString().let { viewModel.updateWorkoutType(it) }
            }
        }

        binding.startDateEditText.setOnClickListener {
            showDatePickerDialog(
                viewModel.startDate.value!!,
                // NOTE: `month + 1` is correct, see documentation: https://developer.android.com/reference/android/app/DatePickerDialog.OnDateSetListener
                DatePickerDialog.OnDateSetListener { _, year, month, day -> viewModel.updateStartDate(year, month + 1, day) }
            )
        }

        binding.startTimeEditText.setOnClickListener {
            showTimePickerDialog(
                viewModel.startDate.value!!,
                TimePickerDialog.OnTimeSetListener { _, hour, minute -> viewModel.updateStartTime(hour, minute) }
            )
        }

        binding.endDateEditText.setOnClickListener {
            showDatePickerDialog(
                viewModel.endDate.value!!,
                // NOTE: `month + 1` is correct, see documentation: https://developer.android.com/reference/android/app/DatePickerDialog.OnDateSetListener
                DatePickerDialog.OnDateSetListener { _, year, month, day -> viewModel.updateEndDate(year, month + 1, day) }
            )
        }

        binding.endTimeEditText.setOnClickListener {
            showTimePickerDialog(
                viewModel.endDate.value!!,
                TimePickerDialog.OnTimeSetListener { _, hour, minute -> viewModel.updateEndTime(hour, minute) }
            )
        }
    }

    private fun cancelButtonPressed() {
        findNavController().navigateUp()
    }

    private fun saveButtonPressed() {
        // TODO: add validation that end date is after start date and duration is less than 4 hours
        GlobalScope.launch { viewModel.save() }
        findNavController().navigateUp()
    }

    private fun showDatePickerDialog(date: DateTime, listener: DatePickerDialog.OnDateSetListener) {
        DatePickerDialog(context!!, listener, date.year, date.monthOfYear, date.dayOfMonth).show()
    }

    private fun showTimePickerDialog(date: DateTime, listener: TimePickerDialog.OnTimeSetListener) {
        TimePickerDialog(context!!, listener, date.hourOfDay, date.minuteOfHour, DateFormat.is24HourFormat(context)).show()
    }
}
