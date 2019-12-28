package com.flinesoft.fitnesstracker.ui.statistics

import android.app.Application
import android.graphics.Color
import androidx.lifecycle.AndroidViewModel
import com.flinesoft.fitnesstracker.R
import com.flinesoft.fitnesstracker.calculation.BodyMassIndexCalculator
import com.flinesoft.fitnesstracker.calculation.BodyShapeIndexCalculator
import com.flinesoft.fitnesstracker.globals.*
import com.flinesoft.fitnesstracker.globals.extensions.database
import com.flinesoft.fitnesstracker.model.WaistCircumferenceMeasurement
import com.flinesoft.fitnesstracker.model.WeightMeasurement
import com.flinesoft.fitnesstracker.ui.shared.StatisticsCellViewModel
import kotlin.time.ExperimentalTime

@ExperimentalTime
class StatisticsViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: store & load from shared preferences, make configurable through interface
    private var heightInCentimeters: Int = 176
    private var birthYear: Int = 1991
    private var gender: BodyShapeIndexCalculator.Gender = BodyShapeIndexCalculator.Gender.MALE

    private var weightMeasurements: List<WeightMeasurement> = emptyList()
    private var waistCircumferenceMeasurements: List<WaistCircumferenceMeasurement> = emptyList()

    val bodyMassIndexCellViewModel = StatisticsCellViewModel(
        listOf(
            StatisticsCellViewModel.TresholdEntry(
                value = BODY_MASS_INDEX_LOWER_HIGH_RISK,
                legend = application.getString(R.string.statistics_body_mass_index_lower_high_risk_treshold_legend),
                color = Color.RED
            ),
            StatisticsCellViewModel.TresholdEntry(
                value = BODY_MASS_INDEX_HEALTHY_MIN,
                legend = application.getString(R.string.statistics_body_mass_index_healthy_min_treshold_legend),
                color = Color.YELLOW
            ),
            StatisticsCellViewModel.TresholdEntry(
                value = BODY_MASS_INDEX_HEALTHY_MAX,
                legend = application.getString(R.string.statistics_body_mass_index_healthy_max_treshold_legend),
                color = Color.YELLOW
            ),
            StatisticsCellViewModel.TresholdEntry(
                value = BODY_MASS_INDEX_UPPER_HIGH_RISK,
                legend = application.getString(R.string.statistics_body_mass_index_upper_high_risk_treshold_legend),
                color = Color.RED
            )
        ),
        alwaysShowValueRange = (BODY_MASS_INDEX_LOWER_HIGH_RISK - 1.0)..(BODY_MASS_INDEX_UPPER_HIGH_RISK + 1.0)
    )

    // Source: https://www.mytecbits.com/tools/medical/absi-calculator
    val bodyShapeIndexCellViewModel = StatisticsCellViewModel(
        listOf(
            StatisticsCellViewModel.TresholdEntry(
                value = BODY_SHAPE_INDEX_VERY_LOW_RISK_MAX,
                legend = application.getString(R.string.statistics_body_shape_index_very_low_risk_max_legend),
                color = Color.GREEN
            ),
            StatisticsCellViewModel.TresholdEntry(
                value = BODY_SHAPE_INDEX_LOW_RISK_MAX,
                legend = application.getString(R.string.statistics_body_shape_index_low_risk_max_legend),
                color = Color.YELLOW
            ),
            StatisticsCellViewModel.TresholdEntry(
                value = BODY_SHAPE_INDEX_AVERAGE_RISK_MAX,
                legend = application.getString(R.string.statistics_body_shape_index_average_risk_max_legend),
                color = Color.RED
            ),
            StatisticsCellViewModel.TresholdEntry(
                value = BODY_SHAPE_INDEX_HIGH_RISK_MAX,
                legend = application.getString(R.string.statistics_body_shape_index_high_risk_max_legend),
                color = Color.RED
            )
        ),
        alwaysShowValueRange = (BODY_SHAPE_INDEX_VERY_LOW_RISK_MAX - 0.1)..(BODY_SHAPE_INDEX_HIGH_RISK_MAX + 0.1)
    )

    init {
        database().weightMeasurementDao.allOrderedByMeasureDate().observeForever {
            weightMeasurements = it
            updateBodyMassIndexDataEntries()
            updateBodyShapeIndexDataEntries()
        }

        database().waistCircumferenceMeasurementDao.allOrderedByMeasureDate().observeForever {
            waistCircumferenceMeasurements = it
            updateBodyShapeIndexDataEntries()
        }
    }

    private fun updateBodyMassIndexDataEntries() {
        bodyMassIndexCellViewModel.dataEntries.value = weightMeasurements.map { weightMeasurement ->
            val bodyMassIndex = BodyMassIndexCalculator.calculateIndex(weightMeasurement.weightInKilograms, heightInCentimeters / 100.0)
            StatisticsCellViewModel.DataEntry(weightMeasurement.measureDate, bodyMassIndex)
        }
    }

    private fun updateBodyShapeIndexDataEntries() {
        // TODO: calculate new data entries using BMI
    }
}
