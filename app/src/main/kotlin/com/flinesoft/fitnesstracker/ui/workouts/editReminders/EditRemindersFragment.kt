package com.flinesoft.fitnesstracker.ui.workouts.editReminders

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.flinesoft.fitnesstracker.databinding.EditRemindersBinding
import com.flinesoft.fitnesstracker.globals.extensions.DateFormatExt
import com.flinesoft.fitnesstracker.globals.extensions.showTimePickerDialog
import com.flinesoft.fitnesstracker.ui.shared.BackNavigationFragment
import kotlin.time.ExperimentalTime

@ExperimentalTime
class EditRemindersFragment : BackNavigationFragment() {
    private lateinit var binding: EditRemindersBinding
    private lateinit var viewModel: EditRemindersViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = EditRemindersBinding.inflate(inflater)
        viewModel = ViewModelProviders.of(this).get(EditRemindersViewModel::class.java)

        setupBackNavigation()
        setupViewModelBinding()
        setupListeners()

        return binding.root
    }

    private fun setupViewModelBinding() {
        viewModel.remindersOn.observe(this, Observer { remindersOn ->
            binding.reminderOnCheckBox.isChecked = remindersOn
            binding.reminderTimeEditText.isEnabled = remindersOn
        })

        viewModel.remindersDayDelay.observe(this, Observer { remindersDayDelay ->
            val dateWithDelay = viewModel.dateWithDayDelay(remindersDayDelay)
            val formattedTimeText = DateFormatExt.timeShort().format(dateWithDelay.toDate())
            binding.reminderTimeEditText.setText(formattedTimeText)
        })
    }

    private fun setupListeners() {
        binding.cancelButton.setOnClickListener { cancelButtonPressed() }
        binding.saveButton.setOnClickListener { saveButtonPressed() }

        binding.reminderOnCheckBox.setOnCheckedChangeListener { _, checked -> viewModel.remindersOn.value = checked }
        binding.reminderTimeEditText.setOnClickListener {
            showTimePickerDialog(
                date = viewModel.dateWithRemindersDayDelay(),
                listener = TimePickerDialog.OnTimeSetListener { _, hour, minute -> viewModel.updateDayDelay(hour, minute) }
            )
        }
    }

    private fun cancelButtonPressed() = findNavController().navigateUp()
    private fun saveButtonPressed() = viewModel.save().also { findNavController().navigateUp() }
}
