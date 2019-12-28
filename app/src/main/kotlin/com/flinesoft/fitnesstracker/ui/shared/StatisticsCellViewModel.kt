package com.flinesoft.fitnesstracker.ui.shared

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.joda.time.DateTime

class StatisticsCellViewModel(val tresholdEntries: List<TresholdEntry>) : ViewModel() {
    data class DataEntry(val dateTime: DateTime, val value: Double)
    data class TresholdEntry(val value: Double, val legend: String, val color: Int)

    val dataEntries = MutableLiveData<List<DataEntry>>().apply { value = emptyList() }
}
