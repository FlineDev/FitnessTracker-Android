package com.flinesoft.fitnesstracker.ui.statistics

import android.content.Intent
import android.icu.util.MeasureUnit
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.menu.MenuBuilder
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.flinesoft.fitnesstracker.R
import com.flinesoft.fitnesstracker.databinding.StatisticsFragmentBinding
import com.flinesoft.fitnesstracker.globals.APP_FEEDBACK_FORUM_URL
import com.flinesoft.fitnesstracker.globals.DIALOG_HORIZONTAL_SPACING
import com.flinesoft.fitnesstracker.globals.HUMAN_WAIST_CIRCUMFERENCE_IN_CENTIMETERS
import com.flinesoft.fitnesstracker.globals.HUMAN_WEIGHT_RANGE_IN_KILOGRAMS
import com.flinesoft.fitnesstracker.globals.extensions.*
import com.flinesoft.fitnesstracker.model.WaistCircumferenceMeasurement
import com.flinesoft.fitnesstracker.model.WeightMeasurement
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.leinardi.android.speeddial.SpeedDialView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import timber.log.Timber
import kotlin.time.ExperimentalTime

@ExperimentalTime
class StatisticsFragment : Fragment() {
    private lateinit var binding: StatisticsFragmentBinding
    private lateinit var viewModel: StatisticsViewModel

    private var inputAlertDialog: AlertDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = StatisticsFragmentBinding.inflate(inflater)
        viewModel = ViewModelProviders.of(this).get(StatisticsViewModel::class.java)

        setupViewModelBinding()
        setHasOptionsMenu(true)
        configureFloatingActionButtonWithSpeedDial()

        return binding.root
    }

    override fun onResume() {
        viewModel.updateAllCharts()

        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.statistics_overflow_menu, menu)

        if (menu is MenuBuilder) {
            menu.setOptionalIconsVisible(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.statistics_overflow_data -> {
            showPersonalDataInput()
            true
        }

        R.id.statistics_overflow_feedback -> {
            showFeedbackForum()
            true
        }

        else -> {
            Timber.e("unknown overflow item id clicked: '${item.itemId}'")
            false
        }
    }

    private fun setupViewModelBinding() {
        val viewModel = ViewModelProviders.of(this)[StatisticsViewModel::class.java]
        binding.bodyMassIndexCell.setup(viewModel.bodyMassIndexCellViewModel, viewLifecycleOwner)
        binding.bodyShapeIndexCell.setup(viewModel.bodyShapeIndexCellViewModel, viewLifecycleOwner)
    }

    private fun configureFloatingActionButtonWithSpeedDial() {
        binding.statisticsSpeedDial.inflate(R.menu.statistics_speed_dial_menu)
        binding.statisticsSpeedDial.setOnActionSelectedListener(SpeedDialView.OnActionSelectedListener { actionItem ->
            binding.statisticsSpeedDial.close()

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

    private fun showPersonalDataInput() {
        findNavController().navigate(StatisticsFragmentDirections.actionStatisticsToEditPersonalData())
    }

    private fun showFeedbackForum() {
        startActivity(Intent(Intent.ACTION_VIEW, APP_FEEDBACK_FORUM_URL))
    }

    private fun showNewWaistCircumferenceForm() {
        val inputTextField = EditText(context).apply {
            inputType = InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_DECIMAL
            setHint(R.string.statistics_speed_dial_waist_circumference_hint)
            afterTextChanged(skipDeletion = NumberFormatExt.default.decimalSeparator()) { _, textWatcher ->
                MeasureFormatExt.short().stringToDouble(text.toString(), MeasureUnit.CENTIMETER)?.let { newValue: Double ->
                    inputAlertDialog?.getButton(AlertDialog.BUTTON_POSITIVE)?.isEnabled = HUMAN_WAIST_CIRCUMFERENCE_IN_CENTIMETERS.contains(newValue)
                    setTextIgnoringTextWatcher(MeasureFormatExt.short().doubleToString(newValue, MeasureUnit.CENTIMETER), textWatcher)
                }
            }
        }

        inputAlertDialog = MaterialAlertDialogBuilder(context)
            .setTitle(R.string.statistics_speed_dial_waist_circumference)
            .setMessage(R.string.statistics_speed_dial_waist_circumference_input_message)
            .setView(inputTextField, DIALOG_HORIZONTAL_SPACING, 0, DIALOG_HORIZONTAL_SPACING, 0)
            .setPositiveButton(R.string.global_action_save) { _, _ ->
                MeasureFormatExt.short().stringToDouble(inputTextField.text.toString(), MeasureUnit.CENTIMETER)?.let { value: Double ->
                    saveNewWaistCircumference(value)
                    view?.snack(R.string.global_info_saved_successfully)
                } ?: run {
                    view?.snack(R.string.global_error_invalid_input)
                }
                inputAlertDialog = null
            }
            .setNeutralButton(R.string.global_action_cancel) { _, _ -> inputAlertDialog = null }
            .show()
        inputAlertDialog?.getButton(AlertDialog.BUTTON_POSITIVE)?.isEnabled = false
    }

    private fun showNewWeightForm() {
        val inputTextField = EditText(context).apply {
            inputType = InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_DECIMAL

            setHint(R.string.statistics_speed_dial_weight_hint)
            afterTextChanged(skipDeletion = NumberFormatExt.default.decimalSeparator()) { _, textWatcher ->
                MeasureFormatExt.short().stringToDouble(text.toString(), MeasureUnit.KILOGRAM)?.let { newValue ->
                    inputAlertDialog?.getButton(AlertDialog.BUTTON_POSITIVE)?.isEnabled = HUMAN_WEIGHT_RANGE_IN_KILOGRAMS.contains(newValue)
                    setTextIgnoringTextWatcher(MeasureFormatExt.short().doubleToString(newValue, MeasureUnit.KILOGRAM), textWatcher)
                }
            }
        }

        inputAlertDialog = MaterialAlertDialogBuilder(context)
            .setTitle(R.string.statistics_speed_dial_weight)
            .setMessage(R.string.statistics_speed_dial_weight_input_message)
            .setView(inputTextField, DIALOG_HORIZONTAL_SPACING, 0, DIALOG_HORIZONTAL_SPACING, 0)
            .setPositiveButton(R.string.global_action_save) { _, _ ->
                MeasureFormatExt.short().stringToDouble(inputTextField.text.toString(), MeasureUnit.KILOGRAM)?.let { value: Double ->
                    saveNewWeight(value)
                    view?.snack(R.string.global_info_saved_successfully)
                } ?: run {
                    view?.snack(R.string.global_error_invalid_input)
                }
                inputAlertDialog = null
            }
            .setNeutralButton(R.string.global_action_cancel) { _, _ -> inputAlertDialog = null }
            .show()
        inputAlertDialog?.getButton(AlertDialog.BUTTON_POSITIVE)?.isEnabled = false
    }

    private fun saveNewWaistCircumference(waistCircumference: Double) {
        val measurement = WaistCircumferenceMeasurement(
            circumferenceInCentimeters = waistCircumference,
            measureDate = DateTime.now()
        )
        GlobalScope.launch { database().waistCircumferenceMeasurementDao.create(measurement) }
    }

    private fun saveNewWeight(weight: Double) {
        val measurement = WeightMeasurement(
            weightInKilograms = weight,
            measureDate = DateTime.now()
        )
        GlobalScope.launch { database().weightMeasurementDao.create(measurement) }
    }
}
