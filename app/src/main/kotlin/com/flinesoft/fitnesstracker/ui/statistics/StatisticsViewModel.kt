package com.flinesoft.fitnesstracker.ui.statistics

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.flinesoft.fitnesstracker.R
import com.flinesoft.fitnesstracker.globals.*
import com.flinesoft.fitnesstracker.ui.shared.StatisticsCellViewModel

class StatisticsViewModel(application: Application) : AndroidViewModel(application) {
    val bodyMassIndexCellViewModel = StatisticsCellViewModel(
        listOf(
            StatisticsCellViewModel.TresholdEntry(
                value = BODY_MASS_INDEX_HEALTHY_MIN,
                legend = application.getString(R.string.statistics_body_mass_index_healthy_min_treshold_legend)
            ),
            StatisticsCellViewModel.TresholdEntry(
                value = BODY_MASS_INDEX_HEALTHY_MAX,
                legend = application.getString(R.string.statistics_body_mass_index_healthy_max_treshold_legend)
            ),
            StatisticsCellViewModel.TresholdEntry(
                value = BODY_MASS_INDEX_HIGH_RISK,
                legend = application.getString(R.string.statistics_body_mass_index_high_risk_treshold_legend)
            )
        )
    )

    val bodyShapeIndexCellViewModel = StatisticsCellViewModel(
        listOf(
            StatisticsCellViewModel.TresholdEntry(
                value = BODY_SHAPE_INDEX_HEALTHY_MAX,
                legend = application.getString(R.string.statistics_body_shape_index_healthy_max_treshold_legend)
            ),
            StatisticsCellViewModel.TresholdEntry(
                value = BODY_SHAPE_INDEX_DOUBLE_RISK,
                legend = application.getString(R.string.statistics_body_shape_index_double_risk_treshold_legend)
            )
        )
    )
}
