package com.flinesoft.fitnesstracker.ui.statistics

import android.content.Intent
import android.icu.util.MeasureUnit
import android.os.Bundle
import android.view.*
import androidx.appcompat.view.menu.MenuBuilder
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.flinesoft.fitnesstracker.R
import com.flinesoft.fitnesstracker.databinding.StatisticsFragmentBinding
import com.flinesoft.fitnesstracker.globals.*
import com.flinesoft.fitnesstracker.globals.extensions.*
import com.flinesoft.fitnesstracker.model.WaistCircumferenceMeasurement
import com.flinesoft.fitnesstracker.model.WeightMeasurement
import com.google.android.material.snackbar.Snackbar
import com.leinardi.android.speeddial.SpeedDialView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import timber.log.Timber
import kotlin.time.ExperimentalTime

@ExperimentalTime
class StatisticsFragment : Fragment() {
    private lateinit var binding: StatisticsFragmentBinding
    private lateinit var viewModel: StatisticsViewModel

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

        GlobalScope.launch {
            delay(300)
            MainScope().launch { requirePersonalDataAppPreferences() }
        }

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

    private fun requirePersonalDataAppPreferences() {
        if (AppPreferences.heightInCentimeters == null || AppPreferences.birthYear == null || AppPreferences.gender == null) {
            view?.snack(R.string.statistics_missing_personal_data_hint, Snackbar.LENGTH_LONG)
            showPersonalDataInput()
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
        showNumberPickerDialog(
            title = getString(R.string.statistics_speed_dial_waist_circumference),
            value = viewModel.latestWaistCircumferenceMeasurement()?.value ?: DEFAULT_INPUT_VALUE_WAIST_CIRCUMFERENCE_IN_CENTIMETERS,
            range = HUMAN_WAIST_CIRCUMFERENCE_RANGE_IN_CENTIMETERS,
            stepSize = 0.5,
            formatToString = { MeasureFormatExt.short().doubleToString(it, MeasureUnit.CENTIMETER) },
            valueChooseAction = {
                saveNewWaistCircumference(it)
                view?.snack(R.string.global_info_saved_successfully)
            }
        )
    }

    private fun showNewWeightForm() {
        showNumberPickerDialog(
            title = getString(R.string.statistics_speed_dial_weight),
            value = viewModel.latestWeightMeasurement()?.value ?: DEFAULT_INPUT_VALUE_WEIGHT_IN_KILOGRAMS,
            range = HUMAN_WEIGHT_RANGE_IN_KILOGRAMS,
            stepSize = 0.1,
            formatToString = { MeasureFormatExt.short().doubleToString(it, MeasureUnit.KILOGRAM) },
            valueChooseAction = {
                saveNewWeight(it)
                view?.snack(R.string.global_info_saved_successfully)
            }
        )
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
