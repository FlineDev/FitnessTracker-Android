package com.flinesoft.fitnesstracker.ui.workouts.editImpediment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.flinesoft.fitnesstracker.globals.DEFAULT_IMPEDIMENT_DAYS
import com.flinesoft.fitnesstracker.globals.MAX_IMPEDIMENT_DURATION_IN_DAYS
import com.flinesoft.fitnesstracker.globals.extensions.database
import com.flinesoft.fitnesstracker.globals.extensions.observeOnce
import com.flinesoft.fitnesstracker.model.Impediment
import org.joda.time.DateTime
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds

@ExperimentalTime
class EditImpedimentViewModel(application: Application) : AndroidViewModel(application) {
    var existingImpediment: LiveData<Impediment>? = null
        set(value) {
            field = value
            value?.observeOnce { existingImpediment ->
                name.value = existingImpediment.name
                startDate.value = existingImpediment.startDate
                endDate.value = existingImpediment.endDate
            }
        }

    var name = MutableLiveData<String>()
    var startDate = MutableLiveData(DateTime.now())
    var endDate = MutableLiveData(DateTime.now().plusDays(DEFAULT_IMPEDIMENT_DAYS))

    fun updateStartDate(year: Int, month: Int, day: Int) {
        startDate.value = startDate.value!!.withYear(year).withMonthOfYear(month).withDayOfMonth(day)

        if (endDate.value!! < startDate.value!!) {
            endDate.value = startDate.value!!.plusDays(DEFAULT_IMPEDIMENT_DAYS)
        }
    }

    fun updateEndDate(year: Int, month: Int, day: Int) {
        endDate.value = endDate.value!!.withYear(year).withMonthOfYear(month).withDayOfMonth(day)
    }

    suspend fun save(): Boolean {
        // TODO: return more exact errors which point to the invalid field & have a message to show below the field
        if (invalidDuration() || name.value.isNullOrBlank()) return false

        existingImpediment?.value?.let { impediment ->
            impediment.name = name.value!!
            impediment.startDate = startDate.value!!
            impediment.endDate = endDate.value!!

            database().impedimentDao.update(impediment)
        } ?: run {
            database().impedimentDao.create(
                Impediment(
                    name = name.value!!,
                    startDate = startDate.value!!,
                    endDate = endDate.value!!
                )
            )
        }

        return true
    }

    private fun invalidDuration(): Boolean = impedimentDuration().inDays < 0 || impedimentDuration().inDays > MAX_IMPEDIMENT_DURATION_IN_DAYS
    private fun impedimentDuration(): Duration = (endDate.value!!.millis - startDate.value!!.millis).milliseconds
}
