package com.flinesoft.fitnesstracker.ui.statistics

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.flinesoft.fitnesstracker.R
import com.flinesoft.fitnesstracker.calculation.BodyMassIndexCalculator
import com.flinesoft.fitnesstracker.calculation.BodyShapeIndexCalculator
import com.flinesoft.fitnesstracker.globals.AppPreferences
import com.flinesoft.fitnesstracker.globals.extensions.database
import com.flinesoft.fitnesstracker.globals.extensions.reduceToLatestMeasureDatePerDay
import com.flinesoft.fitnesstracker.globals.extensions.reduceToLowestValuePerDay
import com.flinesoft.fitnesstracker.model.Gender
import com.flinesoft.fitnesstracker.model.WaistCircumferenceMeasurement
import com.flinesoft.fitnesstracker.model.WeightMeasurement
import org.joda.time.DateTime
import kotlin.time.ExperimentalTime

@ExperimentalTime
class StatisticsViewModel(application: Application) : AndroidViewModel(application) {
    private var weightMeasurements: List<WeightMeasurement> = emptyList()
    private var waistCircumferenceMeasurements: List<WaistCircumferenceMeasurement> = emptyList()

    private val bodyMassIndexPageViewModel = StatisticsPageViewModel(
        tabName = application.getString(R.string.statistics_body_mass_index_tab_name),
        title = application.getString(R.string.statistics_body_mass_index_title),
        tresholdEntries = listOf(
            StatisticsPageViewModel.TresholdEntry(
                value = BODY_MASS_INDEX_LOWER_HIGH_RISK,
                legend = application.getString(R.string.statistics_body_mass_index_lower_high_risk_treshold_legend),
                color = application.getColor(R.color.limitZoneSevereWarning)
            ),
            StatisticsPageViewModel.TresholdEntry(
                value = BODY_MASS_INDEX_HEALTHY_MIN,
                legend = application.getString(R.string.statistics_body_mass_index_healthy_min_treshold_legend),
                color = application.getColor(R.color.limitZoneSafe)
            ),
            StatisticsPageViewModel.TresholdEntry(
                value = BODY_MASS_INDEX_HEALTHY_MAX,
                legend = application.getString(R.string.statistics_body_mass_index_healthy_max_treshold_legend),
                color = application.getColor(R.color.limitZoneSafe)
            ),
            StatisticsPageViewModel.TresholdEntry(
                value = BODY_MASS_INDEX_UPPER_HIGH_RISK,
                legend = application.getString(R.string.statistics_body_mass_index_upper_high_risk_treshold_legend),
                color = application.getColor(R.color.limitZoneSevereWarning)
            )
        ),
        explanation = application.getString(R.string.statistics_body_mass_index_explanation),
        emptyStateText = application.getString(R.string.statistics_body_mass_index_empty_data),
        measurementsLegend = application.getString(R.string.statistics_body_mass_index_measurements_legend),
        movingAveragesLegend = application.getString(R.string.statistics_body_mass_index_moving_averages_legend),
        editDataNavDirections = null
    )

    // Source: https://www.mytecbits.com/tools/medical/absi-calculator
    private val bodyShapeIndexPageViewModel = StatisticsPageViewModel(
        tabName = application.getString(R.string.statistics_body_shape_index_tab_name),
        title = application.getString(R.string.statistics_body_shape_index_title),
        tresholdEntries = listOf(
            StatisticsPageViewModel.TresholdEntry(
                value = BODY_SHAPE_INDEX_VERY_LOW_RISK_MAX,
                legend = application.getString(R.string.statistics_body_shape_index_very_low_risk_max_legend),
                color = application.getColor(R.color.limitZoneSafe)
            ),
            StatisticsPageViewModel.TresholdEntry(
                value = BODY_SHAPE_INDEX_LOW_RISK_MAX,
                legend = application.getString(R.string.statistics_body_shape_index_low_risk_max_legend),
                color = application.getColor(R.color.limitZoneSafe)
            ),
            StatisticsPageViewModel.TresholdEntry(
                value = BODY_SHAPE_INDEX_AVERAGE_RISK_MAX,
                legend = application.getString(R.string.statistics_body_shape_index_average_risk_max_legend),
                color = application.getColor(R.color.limitZoneWarning)
            ),
            StatisticsPageViewModel.TresholdEntry(
                value = BODY_SHAPE_INDEX_HIGH_RISK_MAX,
                legend = application.getString(R.string.statistics_body_shape_index_high_risk_max_legend),
                color = application.getColor(R.color.limitZoneSevereWarning)
            )
        ),
        explanation = application.getString(R.string.statistics_body_shape_index_explanation),
        emptyStateText = application.getString(R.string.statistics_body_shape_index_empty_data),
        measurementsLegend = application.getString(R.string.statistics_body_shape_index_measurements_legend),
        movingAveragesLegend = application.getString(R.string.statistics_body_shape_index_moving_averages_legend),
        editDataNavDirections = null
    )

    private val weightPageViewModel = StatisticsPageViewModel(
        tabName = application.getString(R.string.statistics_weight_tab_name),
        title = application.getString(R.string.statistics_weight_title),
        tresholdEntries = emptyList(),
        explanation = null,
        emptyStateText = application.getString(R.string.statistics_weight_empty_data),
        measurementsLegend = application.getString(R.string.statistics_weight_measurements_legend),
        movingAveragesLegend = application.getString(R.string.statistics_weight_moving_averages_legend),
        editDataNavDirections = StatisticsFragmentDirections.actionStatisticsToEditWeightMeasurements()
    )

    private val waistCircumferencePageViewModel = StatisticsPageViewModel(
        tabName = application.getString(R.string.statistics_waist_circumference_tab_name),
        title = application.getString(R.string.statistics_waist_circumference_title),
        tresholdEntries = listOf(
            StatisticsPageViewModel.TresholdEntry(
                // TODO: [2020-02-06] make sure that the value changes together with the gender
                value = if (AppPreferences.gender == Gender.FEMALE) WAIST_CIRCUMFERENCE_FEMALE_HEALTHY_MAX else WAIST_CIRCUMFERENCE_MALE_HEALTHY_MAX,
                legend = application.getString(R.string.statistics_waist_circumference_healthy_max_legend),
                color = application.getColor(R.color.limitZoneWarning)
            ),
            StatisticsPageViewModel.TresholdEntry(
                // TODO: [2020-02-06] make sure that the value changes together with the gender
                value = if (AppPreferences.gender == Gender.FEMALE) WAIST_CIRCUMFERENCE_FEMALE_HIGH_RISK else WAIST_CIRCUMFERENCE_MALE_HIGH_RISK,
                legend = application.getString(R.string.statistics_waist_circumference_high_risk_legend),
                color = application.getColor(R.color.limitZoneSevereWarning)
            )
        ),
        explanation = null,
        emptyStateText = application.getString(R.string.statistics_waist_circumference_empty_data),
        measurementsLegend = application.getString(R.string.statistics_waist_circumference_measurements_legend),
        movingAveragesLegend = application.getString(R.string.statistics_waist_circumference_moving_averages_legend),
        editDataNavDirections = StatisticsFragmentDirections.actionStatisticsToEditWaistCircumferenceMeasurements()
    )

    init {
        database().weightMeasurementDao.allOrderedByMeasureDate().observeForever {
            weightMeasurements = it.reduceToLowestValuePerDay()
            updateBodyMassIndexDataEntries()
            updateBodyShapeIndexDataEntries()
            updateWeightDataEntries()
        }

        database().waistCircumferenceMeasurementDao.allOrderedByMeasureDate().observeForever {
            waistCircumferenceMeasurements = it.reduceToLowestValuePerDay()
            updateBodyShapeIndexDataEntries()
            updateWaistCircumferenceDataEntries()
        }
    }

    fun pageViewModel(page: StatisticsPagerAdapter.Page): StatisticsPageViewModel = when (page) {
        StatisticsPagerAdapter.Page.BODY_MASS_INDEX ->
            bodyMassIndexPageViewModel

        StatisticsPagerAdapter.Page.BODY_SHAPE_INDEX ->
            bodyShapeIndexPageViewModel

        StatisticsPagerAdapter.Page.WEIGHT ->
            weightPageViewModel

        StatisticsPagerAdapter.Page.WAIST_CIRCUMFERENCE ->
            waistCircumferencePageViewModel
    }

    fun latestWeightMeasurement(): WeightMeasurement? = weightMeasurements.lastOrNull()
    fun latestWaistCircumferenceMeasurement(): WaistCircumferenceMeasurement? = waistCircumferenceMeasurements.lastOrNull()

    fun updateAllCharts() {
        updateBodyMassIndexDataEntries()
        updateBodyShapeIndexDataEntries()
        updateWeightDataEntries()
        updateWaistCircumferenceDataEntries()
    }

    private fun updateBodyMassIndexDataEntries() {
        bodyMassIndexPageViewModel.dataEntries.value = weightMeasurements.map { weightMeasurement ->
            val bodyMassIndex = BodyMassIndexCalculator.calculateIndex(
                weightInKilograms = weightMeasurement.weightInKilograms,
                heightInMeters = AppPreferences.heightInCentimeters!! / 100.0
            )
            StatisticsPageViewModel.DataEntry(weightMeasurement.measureDate, bodyMassIndex)
        }
    }

    private fun updateBodyShapeIndexDataEntries() {
        if (waistCircumferenceMeasurements.isEmpty() || weightMeasurements.isEmpty()) return

        val waistCircumferenceDataEntries: List<StatisticsPageViewModel.DataEntry> = waistCircumferenceMeasurements.map { waistCircumferenceMeasurement ->
            val relatedWeightMeasurement = weightMeasurements.lastOrNull {
                it.measureDate < waistCircumferenceMeasurement.measureDate
            } ?: weightMeasurements.first()
            val bodyShapeZIndex = BodyShapeIndexCalculator.calculateZIndex(
                weightInKilograms = relatedWeightMeasurement.weightInKilograms,
                heightInMeters = AppPreferences.heightInCentimeters!! / 100.0,
                waistCircumferenceInMeters = waistCircumferenceMeasurement.circumferenceInCentimeters / 100.0,
                ageInYears = DateTime.now().year - AppPreferences.birthYear!!,
                gender = AppPreferences.gender!!
            )
            StatisticsPageViewModel.DataEntry(waistCircumferenceMeasurement.measureDate, bodyShapeZIndex)
        }

        val weightDataEntries: List<StatisticsPageViewModel.DataEntry> = weightMeasurements.map { weightMeasurement ->
            val relatedWaistCircumferenceMeasurement = waistCircumferenceMeasurements.lastOrNull {
                it.measureDate < weightMeasurement.measureDate
            } ?: waistCircumferenceMeasurements.first()
            val bodyShapeZIndex = BodyShapeIndexCalculator.calculateZIndex(
                weightInKilograms = weightMeasurement.weightInKilograms,
                heightInMeters = AppPreferences.heightInCentimeters!! / 100.0,
                waistCircumferenceInMeters = relatedWaistCircumferenceMeasurement.circumferenceInCentimeters / 100.0,
                ageInYears = DateTime.now().year - AppPreferences.birthYear!!,
                gender = AppPreferences.gender!!
            )
            StatisticsPageViewModel.DataEntry(weightMeasurement.measureDate, bodyShapeZIndex)
        }

        val combinedOrderedDataEntries = (waistCircumferenceDataEntries + weightDataEntries).sortedBy { it.measureDate }
        bodyShapeIndexPageViewModel.dataEntries.value = combinedOrderedDataEntries.reduceToLatestMeasureDatePerDay()
    }

    private fun updateWeightDataEntries() {
        weightPageViewModel.dataEntries.value = weightMeasurements
            .map { StatisticsPageViewModel.DataEntry(it.measureDate, it.value) }
            .reduceToLowestValuePerDay()
    }

    private fun updateWaistCircumferenceDataEntries() {
        waistCircumferencePageViewModel.dataEntries.value = waistCircumferenceMeasurements
            .map { StatisticsPageViewModel.DataEntry(it.measureDate, it.value) }
            .reduceToLowestValuePerDay()
    }
}
