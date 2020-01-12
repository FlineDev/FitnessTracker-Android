package com.flinesoft.fitnesstracker.globals.extensions

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.text.format.DateFormat
import android.widget.EditText
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

fun Fragment.showNumberPickerDialog(
    title: String,
    value: Int,
    range: IntRange,
    formatToString: (Int) -> String,
    valueChooseAction: (Int) -> Unit
) {
    showNumberPickerDialog(
        title = title,
        value = value.toDouble(),
        range = range.first.toDouble()..range.last.toDouble(),
        stepSize = 1.0,
        formatToString = { formatToString(it.toInt()) },
        valueChooseAction = { valueChooseAction(it.toInt()) }
    )
}

fun Fragment.showNumberPickerDialog(
    title: String,
    value: Double,
    range: ClosedRange<Double>,
    stepSize: Double,
    formatToString: (Double) -> String,
    valueChooseAction: (Double) -> Unit
) {
    val numberPicker = NumberPicker(context).apply {
        setFormatter { formatToString(it.toDouble() * stepSize) }
        wrapSelectorWheel = false

        minValue = (range.start / stepSize).toInt()
        maxValue = (range.endInclusive / stepSize).toInt()
        this.value = (value / stepSize).toInt()

        // NOTE: workaround for a bug that rendered the selected value wrong until user scrolled, see also: https://stackoverflow.com/q/27343772/3451975
        (NumberPicker::class.java.getDeclaredField("mInputText").apply { isAccessible = true }.get(this) as EditText).filters = emptyArray()
    }

    MaterialAlertDialogBuilder(context)
        .setTitle(title)
        .setView(numberPicker)
        .setPositiveButton(R.string.global_action_confirm) { _, _ -> valueChooseAction(numberPicker.value.toDouble() * stepSize) }
        .setNeutralButton(R.string.global_action_cancel) { _, _ -> /* do nothing, closes dialog automatically */ }
        .show()
}
