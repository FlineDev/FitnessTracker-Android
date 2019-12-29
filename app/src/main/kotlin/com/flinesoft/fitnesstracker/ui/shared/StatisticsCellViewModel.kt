package com.flinesoft.fitnesstracker.ui.shared

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flinesoft.fitnesstracker.model.Measurement
import org.joda.time.DateTime

class StatisticsCellViewModel(val tresholdEntries: List<TresholdEntry>, val alwaysShowValueRange: ClosedFloatingPointRange<Double>) : ViewModel() {
    data class DataEntry(val dateTime: DateTime, override val value: Double) : Measurement {
        override val measureDate: DateTime
            get() = dateTime
    }
    data class TresholdEntry(val value: Double, val legend: String, val color: Int)

    val dataEntries = MutableLiveData<List<DataEntry>>().apply { value = emptyList() }
}
