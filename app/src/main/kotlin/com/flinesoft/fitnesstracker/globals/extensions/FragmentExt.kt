package com.flinesoft.fitnesstracker.globals.extensions

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.text.format.DateFormat
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import com.flinesoft.fitnesstracker.R
import com.flinesoft.fitnesstracker.persistence.FitnessTrackerDatabase
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.joda.time.DateTime
import kotlin.time.ExperimentalTime

@ExperimentalTime
fun Fragment.database(): FitnessTrackerDatabase = FitnessTrackerDatabase.getInstance(activity!!.application)

@ExperimentalTime
fun AndroidViewModel.database(): FitnessTrackerDatabase = FitnessTrackerDatabase.getInstance(getApplication())

fun Fragment.showDatePickerDialog(date: DateTime, listener: DatePickerDialog.OnDateSetListener) {
    // NOTE: `month - 1` is correct, see documentation: https://developer.android.com/reference/android/app/DatePickerDialog.OnDateSetListener
    DatePickerDialog(context!!, listener, date.year, date.monthOfYear - 1, date.dayOfMonth).show()
}

fun Fragment.showTimePickerDialog(date: DateTime, listener: TimePickerDialog.OnTimeSetListener) {
    TimePickerDialog(context!!, listener, date.hourOfDay, date.minuteOfHour, DateFormat.is24HourFormat(context)).show()
}

fun Fragment.showNumberPickerDialog(title: String, value: Int, range: IntRange, valueChangeAction: (Int) -> Unit) {
    val numberPicker = NumberPicker(context).apply {
        minValue = range.first
        maxValue = range.last
        this.value = value
    }

    MaterialAlertDialogBuilder(context)
        .setTitle(title)
        .setView(numberPicker, 50, 0, 50, 0)
        .setPositiveButton(R.string.global_action_confirm) { _, _ -> valueChangeAction(numberPicker.value) }
        .setNeutralButton(R.string.global_action_cancel) { _, _ -> /* do nothing, closes dialog automatically */ }
        .show()
}
