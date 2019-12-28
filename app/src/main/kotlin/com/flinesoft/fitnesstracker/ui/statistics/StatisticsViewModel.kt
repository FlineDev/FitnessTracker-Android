package com.flinesoft.fitnesstracker.ui.statistics

import android.app.Application
import android.graphics.Color
import androidx.lifecycle.AndroidViewModel
import com.flinesoft.fitnesstracker.R
import com.flinesoft.fitnesstracker.globals.*
import com.flinesoft.fitnesstracker.model.WaistCircumferenceMeasurement
import com.flinesoft.fitnesstracker.model.WeightMeasurement
import com.flinesoft.fitnesstracker.ui.shared.StatisticsCellViewModel
import kotlin.time.ExperimentalTime

@ExperimentalTime
class StatisticsViewModel(application: Application) : AndroidViewModel(application) {
    enum class Gender { FEMALE, MALE }

    // TODO: store & load from shared preferences, make configurable through interface
    private var heightInCentimeters: Int = 176
    private var birthYear: Int = 1991
    private var gender: Gender = Gender.MALE

    private var weightMeasurements: List<WeightMeasurement> = emptyList()
    private var waistCircumferenceMeasurements: List<WaistCircumferenceMeasurement> = emptyList()

    val bodyMassIndexCellViewModel = StatisticsCellViewModel(
        listOf(
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
                value = BODY_MASS_INDEX_HIGH_RISK,
                legend = application.getString(R.string.statistics_body_mass_index_high_risk_treshold_legend),
                color = Color.RED
            )
        )
    )

    val bodyShapeIndexCellViewModel = StatisticsCellViewModel(
        listOf(
            StatisticsCellViewModel.TresholdEntry(
                value = BODY_SHAPE_INDEX_HEALTHY_MAX,
                legend = application.getString(R.string.statistics_body_shape_index_healthy_max_treshold_legend),
                color = Color.YELLOW
            ),
            StatisticsCellViewModel.TresholdEntry(
                value = BODY_SHAPE_INDEX_DOUBLE_RISK,
                legend = application.getString(R.string.statistics_body_shape_index_double_risk_treshold_legend),
                color = Color.RED
            )
        )
    )

    init {
//        database().weightMeasurementDao.allOrderedByMeasureDate().observeForever {
//            weightMeasurements = it
//            updateBodyMassIndexDataEntries()
//            updateBodyShapeIndexDataEntries()
//        }
//
//        database().waistCircumferenceMeasurementDao.allOrderedByMeasureDate().observeForever {
//            waistCircumferenceMeasurements = it
//            updateBodyShapeIndexDataEntries()
//        }
    }

    private fun updateBodyMassIndexDataEntries() {
        // TODO: calculate new data entries using BMI
    }

    private fun updateBodyShapeIndexDataEntries() {
        // TODO: calculate new data entries using BMI
    }
}
