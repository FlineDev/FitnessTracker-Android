package com.flinesoft.fitnesstracker.ui.statistics

import android.icu.text.MeasureFormat
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import android.icu.util.ULocale
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.appcompat.view.menu.MenuBuilder
import com.flinesoft.fitnesstracker.R
import com.flinesoft.fitnesstracker.globals.extensions.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.leinardi.android.speeddial.SpeedDialView
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
            when(actionItem.id) {
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
            inputType = InputType.TYPE_CLASS_NUMBER
            setHint(R.string.statistics_speed_dial_waist_circumference_hint)
            afterTextChanged { _, textWatcher ->
                val newValue: Int = MeasureFormatExt.short.stringToInt(text.toString(), MeasureUnit.CENTIMETER)
                setTextIgnoringTextWatcher(MeasureFormatExt.short.valueToString(newValue, MeasureUnit.CENTIMETER), textWatcher)
            }
        }

        MaterialAlertDialogBuilder(context)
            .setTitle(R.string.statistics_speed_dial_waist_circumference)
            .setMessage(R.string.statistics_speed_dial_waist_circumference_input_message)
            .setView(inputTextField, 50, 0, 50, 0)
            .setPositiveButton(R.string.global_action_save) { _, _ ->
                saveNewWaistCircumference(MeasureFormatExt.short.stringToInt(inputTextField.text.toString(), MeasureUnit.CENTIMETER))
            }
            .setNegativeButton(R.string.global_action_cancel) { _, _ -> /* will auto-cancel */ }
            .show()
    }

    private fun showNewWeightForm() {
        val inputTextField = EditText(context).apply {
            inputType = InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_DECIMAL
            setHint(R.string.statistics_speed_dial_weight_hint)
            afterTextChanged { _, textWatcher ->
                val newValue: Double = MeasureFormatExt.short.stringToDouble(text.toString(), MeasureUnit.KILOGRAM)
                setTextIgnoringTextWatcher(MeasureFormatExt.short.valueToString(newValue, MeasureUnit.KILOGRAM), textWatcher)
            }
        }

        MaterialAlertDialogBuilder(context)
            .setTitle(R.string.statistics_speed_dial_weight)
            .setMessage(R.string.statistics_speed_dial_weight_input_message)
            .setView(inputTextField, 50, 0, 50, 0)
            .setPositiveButton(R.string.global_action_save) { _, _ ->
                saveNewWeight(MeasureFormatExt.short.stringToDouble(inputTextField.text.toString(), MeasureUnit.KILOGRAM))
            }
            .setNegativeButton(R.string.global_action_cancel) { _, _ -> /* will auto-cancel */ }
            .show()
    }

    private fun saveNewWaistCircumference(waistCircumference: Int) {
        // TODO: not yet implemented
    }

    private fun saveNewWeight(weight: Double) {
        // TODO: not yet implemented
    }
}