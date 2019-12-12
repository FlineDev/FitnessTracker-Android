package com.flinesoft.fitnesstracker.ui.statistics

import android.icu.util.MeasureUnit
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.view.menu.MenuBuilder
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.flinesoft.fitnesstracker.R
import com.flinesoft.fitnesstracker.globals.extensions.*
import com.flinesoft.fitnesstracker.model.WaistCircumferenceMeasurement
import com.flinesoft.fitnesstracker.persistence.FitnessTrackerDatabase
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.leinardi.android.speeddial.SpeedDialView
import org.joda.time.DateTime
import timber.log.Timber

class StatisticsFragment : Fragment() {
    private lateinit var statisticsViewModel: StatisticsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        statisticsViewModel = ViewModelProviders.of(this).get(StatisticsViewModel::class.java)

        val rootView: View = inflater.inflate(R.layout.fragment_statistics, container, false)
        val textView: TextView = rootView.findViewById(R.id.text_statistics)
        statisticsViewModel.text.observe(this, Observer { textView.text = it })

        setHasOptionsMenu(true)
        configureFloatingActionButtonWithSpeedDial(rootView)

        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.statistics_overflow_menu, menu)

        if (menu is MenuBuilder) {
            menu.setOptionalIconsVisible(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.statistics_overflow_data -> {
                showBaseDataInput()
                return true
            }

            R.id.statistics_overflow_calc -> {
                showCalculationMethods()
                return true
            }

            else -> {
                Timber.e("unknown overflow item id clicked: '${item.itemId}'")
                return false
            }
        }
    }

    private fun configureFloatingActionButtonWithSpeedDial(rootView: View) {
        val speedDialView: SpeedDialView = rootView.findViewById(R.id.statistics_speed_dial)
        speedDialView.inflate(R.menu.statistics_speed_dial_menu)

        speedDialView.setOnActionSelectedListener(SpeedDialView.OnActionSelectedListener { actionItem ->
            when (actionItem.id) {
                R.id.statistics_speed_dial_waist_circumference -> {
                    showNewWaistCircumferenceForm()
                    return@OnActionSelectedListener true
                }

                R.id.statistics_speed_dial_weight -> {
                    showNewWeightForm()
                    return@OnActionSelectedListener true
                }

                else -> {
                    Timber.e("unknown speed dial action id clicked: '${actionItem.id}'")
                    return@OnActionSelectedListener false
                }
            }
        })
    }

    private fun showBaseDataInput() {
        MaterialAlertDialogBuilder(context)
            .setTitle(R.string.statistics_overflow_data)
            // TODO: not yet implemented
            .show()
    }

    private fun showCalculationMethods() {
        MaterialAlertDialogBuilder(context)
            .setTitle(R.string.statistics_overflow_calc)
            // TODO: not yet implemented
            .show()
    }

    private fun showNewWaistCircumferenceForm() {
        val inputTextField = EditText(context).apply {
            inputType = InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_DECIMAL
            setHint(R.string.statistics_speed_dial_waist_circumference_hint)
            afterTextChanged { _, textWatcher ->
                MeasureFormatExt.short.stringToDouble(text.toString(), MeasureUnit.CENTIMETER)?.let { newValue: Double ->
                    setTextIgnoringTextWatcher(MeasureFormatExt.short.valueToString(newValue, MeasureUnit.CENTIMETER), textWatcher)
                    setSelection(MeasureFormatExt.short.numberFormat.format(newValue).length)
                }
            }
        }

        MaterialAlertDialogBuilder(context)
            .setTitle(R.string.statistics_speed_dial_waist_circumference)
            .setMessage(R.string.statistics_speed_dial_waist_circumference_input_message)
            .setView(inputTextField, 50, 0, 50, 0)
            .setPositiveButton(R.string.global_action_save) { _, _ ->
                MeasureFormatExt.short.stringToDouble(inputTextField.text.toString(), MeasureUnit.CENTIMETER)?.let { value: Double ->
                    saveNewWaistCircumference(value)
                    view?.snack(R.string.global_info_saved_successfully)
                } ?: run {
                    view?.snack(R.string.global_error_invalid_input)
                }
            }
            .setNegativeButton(R.string.global_action_cancel) { _, _ -> /* will auto-cancel */ }
            .show()
    }

    private fun showNewWeightForm() {
        val inputTextField = EditText(context).apply {
            inputType = InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_DECIMAL
            setHint(R.string.statistics_speed_dial_weight_hint)
            afterTextChanged { _, textWatcher ->
                MeasureFormatExt.short.stringToDouble(text.toString(), MeasureUnit.KILOGRAM)?.let { newValue ->
                    setTextIgnoringTextWatcher(MeasureFormatExt.short.valueToString(newValue, MeasureUnit.KILOGRAM), textWatcher)
                    setSelection(MeasureFormatExt.short.numberFormat.format(newValue).length)
                }
            }
        }

        MaterialAlertDialogBuilder(context)
            .setTitle(R.string.statistics_speed_dial_weight)
            .setMessage(R.string.statistics_speed_dial_weight_input_message)
            .setView(inputTextField, 50, 0, 50, 0)
            .setPositiveButton(R.string.global_action_save) { _, _ ->
                MeasureFormatExt.short.stringToDouble(inputTextField.text.toString(), MeasureUnit.KILOGRAM)?.let { value: Double ->
                    saveNewWeight(value)
                    view?.snack(R.string.global_info_saved_successfully)
                } ?: run {
                    view?.snack(R.string.global_error_invalid_input)
                }
            }
            .setNegativeButton(R.string.global_action_cancel) { _, _ -> /* will auto-cancel */ }
            .show()
    }

    private fun saveNewWaistCircumference(waistCircumference: Double) {
        val measurement = WaistCircumferenceMeasurement(
            circumferenceInCentimeters = waistCircumference,
            measureDate = DateTime.now()
        )
        val application = requireNotNull(this.activity).application
        val database = FitnessTrackerDatabase.getInstance(application)
        database.waistCircumferenceMeasurementDao.create(measurement)
    }

    private fun saveNewWeight(weight: Double) {
        // TODO: not yet implemented
    }
}
