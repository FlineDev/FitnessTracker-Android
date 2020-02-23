package com.flinesoft.fitnesstracker.ui.statistics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.flinesoft.fitnesstracker.model.Measurement
import org.joda.time.DateTime

class StatisticsPageViewModel(
    val tabName: String,
    val title: String,
    val tresholdEntries: List<TresholdEntry>,
    val explanation: String?,
    val emptyStateText: String,
    val legend: String,
    val editDataNavDirections: NavDirections?
) : ViewModel() {
    data class DataEntry(val dateTime: DateTime, override val value: Double) : Measurement {
        override val measureDate: DateTime
            get() = dateTime
    }
    data class TresholdEntry(val value: Double, val legend: String, val color: Int)

    val dataEntries = MutableLiveData<List<DataEntry>>().apply { value = emptyList() }
}
