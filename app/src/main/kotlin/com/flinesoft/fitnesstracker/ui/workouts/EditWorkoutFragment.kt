package com.flinesoft.fitnesstracker.ui.workouts

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.flinesoft.fitnesstracker.databinding.EditWorkoutFragmentBinding
import com.flinesoft.fitnesstracker.globals.BackNavigationFragment
import com.flinesoft.fitnesstracker.globals.extensions.DateFormatExt
import org.joda.time.DateTime
import java.util.*
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

        // TODO: also apply new picked date value to view model
        binding.startDateEditText.setOnClickListener { showDatePickerDialog(viewModel.startDate, dateSetListener(binding.startDateEditText)) }
        binding.startTimeEditText.setOnClickListener { showTimePickerDialog(viewModel.startDate, timeSetListener(binding.startTimeEditText)) }
        binding.endDateEditText.setOnClickListener { showDatePickerDialog(viewModel.endDate, dateSetListener(binding.endDateEditText)) }
        binding.endTimeEditText.setOnClickListener { showTimePickerDialog(viewModel.endDate, timeSetListener(binding.endTimeEditText)) }
    }

    private fun cancelButtonPressed() {
        findNavController().navigateUp()
    }

    private fun saveButtonPressed() {
        // TODO: not yet implemented
    }

    private fun showDatePickerDialog(date: DateTime, listener: DatePickerDialog.OnDateSetListener) {
        DatePickerDialog(context!!, listener, date.year, date.monthOfYear, date.dayOfMonth).show()
    }

    private fun showTimePickerDialog(date: DateTime, listener: TimePickerDialog.OnTimeSetListener) {
        TimePickerDialog(context!!, listener, date.hourOfDay, date.minuteOfHour, DateFormat.is24HourFormat(context)).show()
    }

    private fun dateSetListener(editText: EditText): DatePickerDialog.OnDateSetListener {
        val calendar = Calendar.getInstance(Locale.getDefault())
        return DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            editText.setText(DateFormatExt.dateMedium().format(calendar.time))
        }
    }

    private fun timeSetListener(editText: EditText): TimePickerDialog.OnTimeSetListener {
        val calendar = Calendar.getInstance(Locale.getDefault())
        return TimePickerDialog.OnTimeSetListener { _, hourOfDay, minuteOfHour ->
            calendar.set(Calendar.HOUR, hourOfDay)
            calendar.set(Calendar.MINUTE, minuteOfHour)

            editText.setText(DateFormatExt.timeMedium().format(calendar.time))
        }
    }
}
