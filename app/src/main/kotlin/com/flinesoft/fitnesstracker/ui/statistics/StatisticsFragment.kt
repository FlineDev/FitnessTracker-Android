package com.flinesoft.fitnesstracker.ui.statistics

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuBuilder
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.flinesoft.fitnesstracker.R
import com.flinesoft.fitnesstracker.databinding.StatisticsFragmentBinding
import com.flinesoft.fitnesstracker.globals.APP_FEEDBACK_FORUM_URL
import com.flinesoft.fitnesstracker.globals.AppPreferences
import com.flinesoft.fitnesstracker.globals.DEFAULT_INPUT_VALUE_WAIST_CIRCUMFERENCE_IN_CENTIMETERS
import com.flinesoft.fitnesstracker.globals.DEFAULT_INPUT_VALUE_WEIGHT_IN_KILOGRAMS
import com.flinesoft.fitnesstracker.globals.DEFAULT_MODAL_PRESENTATION_DELAY
import com.flinesoft.fitnesstracker.globals.extensions.database
import com.flinesoft.fitnesstracker.globals.extensions.showWaistCircumferencePickerDialog
import com.flinesoft.fitnesstracker.globals.extensions.showWeightPickerDialog
import com.flinesoft.fitnesstracker.globals.extensions.snack
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
    private lateinit var pagerAdapter: StatisticsPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = StatisticsFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(StatisticsViewModel::class.java)
        pagerAdapter = StatisticsPagerAdapter(viewModel, childFragmentManager)

        setHasOptionsMenu(true)
        setupViewBinding()
        configureFloatingActionButtonWithSpeedDial()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.updateAllCharts()

        GlobalScope.launch {
            delay(DEFAULT_MODAL_PRESENTATION_DELAY)
            MainScope().launch { requirePersonalDataAppPreferences() }
        }
    }

    @SuppressLint("RestrictedApi")
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

    private fun setupViewBinding() {
        binding.statisticsViewPager.adapter = pagerAdapter
    }

    private fun requirePersonalDataAppPreferences() {
        if (AppPreferences.heightInCentimeters == null || AppPreferences.birthYear == null || AppPreferences.gender == null) {
            view?.snack(R.string.statistics_missing_personal_data_hint, Snackbar.LENGTH_LONG)
            showPersonalDataInput()
        }
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
        showWaistCircumferencePickerDialog(
            title = getString(R.string.statistics_speed_dial_waist_circumference),
            value = viewModel.latestWaistCircumferenceMeasurement()?.value ?: DEFAULT_INPUT_VALUE_WAIST_CIRCUMFERENCE_IN_CENTIMETERS,
            valueChooseAction = {
                saveNewWaistCircumference(it)
                view?.snack(R.string.global_info_saved_successfully)
            }
        )
    }

    private fun showNewWeightForm() {
        showWeightPickerDialog(
            title = getString(R.string.statistics_speed_dial_weight),
            value = viewModel.latestWeightMeasurement()?.value ?: DEFAULT_INPUT_VALUE_WEIGHT_IN_KILOGRAMS,
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
