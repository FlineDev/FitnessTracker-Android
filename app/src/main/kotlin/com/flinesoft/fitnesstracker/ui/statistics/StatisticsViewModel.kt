package com.flinesoft.fitnesstracker.ui.statistics

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.flinesoft.fitnesstracker.R
import com.flinesoft.fitnesstracker.calculation.BodyMassIndexCalculator
import com.flinesoft.fitnesstracker.calculation.BodyShapeIndexCalculator
import com.flinesoft.fitnesstracker.globals.*
import com.flinesoft.fitnesstracker.globals.extensions.database
import com.flinesoft.fitnesstracker.globals.extensions.reduceToLatestMeasureDatePerDay
import com.flinesoft.fitnesstracker.globals.extensions.reduceToLowestValuePerDay
import com.flinesoft.fitnesstracker.model.WaistCircumferenceMeasurement
import com.flinesoft.fitnesstracker.model.WeightMeasurement
import org.joda.time.DateTime
import kotlin.time.ExperimentalTime

@ExperimentalTime
class StatisticsViewModel(application: Application) : AndroidViewModel(application) {
    private var weightMeasurements: List<WeightMeasurement> = emptyList()
    private var waistCircumferenceMeasurements: List<WaistCircumferenceMeasurement> = emptyList()

    val bodyMassIndexCellViewModel = StatisticsCellViewModel(
        listOf(
            StatisticsCellViewModel.TresholdEntry(
                value = BODY_MASS_INDEX_LOWER_HIGH_RISK,
                legend = application.getString(R.string.statistics_body_mass_index_lower_high_risk_treshold_legend),
                color = application.getColor(R.color.limitZoneSevereWarning)
            ),
            StatisticsCellViewModel.TresholdEntry(
                value = BODY_MASS_INDEX_HEALTHY_MIN,
                legend = application.getString(R.string.statistics_body_mass_index_healthy_min_treshold_legend),
                color = application.getColor(R.color.limitZoneSafe)
            ),
            StatisticsCellViewModel.TresholdEntry(
                value = BODY_MASS_INDEX_HEALTHY_MAX,
                legend = application.getString(R.string.statistics_body_mass_index_healthy_max_treshold_legend),
                color = application.getColor(R.color.limitZoneSafe)
            ),
            StatisticsCellViewModel.TresholdEntry(
                value = BODY_MASS_INDEX_UPPER_HIGH_RISK,
                legend = application.getString(R.string.statistics_body_mass_index_upper_high_risk_treshold_legend),
                color = application.getColor(R.color.limitZoneSevereWarning)
            )
        ),
        explanation = application.getString(R.string.statistics_body_mass_index_explanation),
        emptyStateText = application.getString(R.string.statistics_body_mass_index_empty_data),
        legend = application.getString(R.string.statistics_body_mass_index_legend)
    )

    // Source: https://www.mytecbits.com/tools/medical/absi-calculator
    val bodyShapeIndexCellViewModel = StatisticsCellViewModel(
        listOf(
            StatisticsCellViewModel.TresholdEntry(
                value = BODY_SHAPE_INDEX_VERY_LOW_RISK_MAX,
                legend = application.getString(R.string.statistics_body_shape_index_very_low_risk_max_legend),
                color = application.getColor(R.color.limitZoneSafe)
            ),
            StatisticsCellViewModel.TresholdEntry(
                value = BODY_SHAPE_INDEX_LOW_RISK_MAX,
                legend = application.getString(R.string.statistics_body_shape_index_low_risk_max_legend),
                color = application.getColor(R.color.limitZoneSafe)
            ),
            StatisticsCellViewModel.TresholdEntry(
                value = BODY_SHAPE_INDEX_AVERAGE_RISK_MAX,
                legend = application.getString(R.string.statistics_body_shape_index_average_risk_max_legend),
                color = application.getColor(R.color.limitZoneWarning)
            ),
            StatisticsCellViewModel.TresholdEntry(
                value = BODY_SHAPE_INDEX_HIGH_RISK_MAX,
                legend = application.getString(R.string.statistics_body_shape_index_high_risk_max_legend),
                color = application.getColor(R.color.limitZoneSevereWarning)
            )
        ),
        explanation = application.getString(R.string.statistics_body_shape_index_explanation),
        emptyStateText = application.getString(R.string.statistics_body_shape_index_empty_data),
        legend = application.getString(R.string.statistics_body_shape_index_legend)
    )

    init {
        database().weightMeasurementDao.allOrderedByMeasureDate().observeForever {
            weightMeasurements = it.reduceToLowestValuePerDay()
            updateBodyMassIndexDataEntries()
            updateBodyShapeIndexDataEntries()
        }

        database().waistCircumferenceMeasurementDao.allOrderedByMeasureDate().observeForever {
            waistCircumferenceMeasurements = it.reduceToLowestValuePerDay()
            updateBodyShapeIndexDataEntries()
        }
    }

    fun updateAllCharts() {
        updateBodyMassIndexDataEntries()
        updateBodyShapeIndexDataEntries()
    }

    private fun updateBodyMassIndexDataEntries() {
        bodyMassIndexCellViewModel.dataEntries.value = weightMeasurements.map { weightMeasurement ->
            val bodyMassIndex = BodyMassIndexCalculator.calculateIndex(
                weightInKilograms = weightMeasurement.weightInKilograms,
                heightInMeters = AppPreferences.heightInCentimeters!! / 100.0
            )
            StatisticsCellViewModel.DataEntry(weightMeasurement.measureDate, bodyMassIndex)
        }
    }

    private fun updateBodyShapeIndexDataEntries() {
        if (waistCircumferenceMeasurements.isEmpty() || weightMeasurements.isEmpty()) return

        val waistCircumferenceDataEntries: List<StatisticsCellViewModel.DataEntry> = waistCircumferenceMeasurements.map { waistCircumferenceMeasurement ->
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
            StatisticsCellViewModel.DataEntry(waistCircumferenceMeasurement.measureDate, bodyShapeZIndex)
        }

        val weightDataEntries: List<StatisticsCellViewModel.DataEntry> = weightMeasurements.map { weightMeasurement ->
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
            StatisticsCellViewModel.DataEntry(weightMeasurement.measureDate, bodyShapeZIndex)
        }

        val combinedOrderedDataEntries = (waistCircumferenceDataEntries + weightDataEntries).sortedBy { it.dateTime }
        bodyShapeIndexCellViewModel.dataEntries.value = combinedOrderedDataEntries.reduceToLatestMeasureDatePerDay()
    }
}
