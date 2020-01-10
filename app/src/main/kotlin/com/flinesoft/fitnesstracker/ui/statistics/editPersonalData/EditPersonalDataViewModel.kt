package com.flinesoft.fitnesstracker.ui.statistics.editPersonalData

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.flinesoft.fitnesstracker.R
import com.flinesoft.fitnesstracker.globals.AppPreferences
import com.flinesoft.fitnesstracker.globals.HUMAN_AGE_RANGE
import com.flinesoft.fitnesstracker.globals.HUMAN_HEIGHT_RANGE_IN_CENTIMETERS
import com.flinesoft.fitnesstracker.model.Gender
import org.joda.time.DateTime
import timber.log.Timber
import kotlin.time.ExperimentalTime

@ExperimentalTime
class EditPersonalDataViewModel(application: Application) : AndroidViewModel(application) {
    var heightInCentimeters = MutableLiveData<Int?>(AppPreferences.heightInCentimeters)
    var gender = MutableLiveData<Gender?>(AppPreferences.gender)
    var birthYear = MutableLiveData<Int?>(AppPreferences.birthYear)

    fun updateGender(spinnerItemLocalizedString: String) {
        when (spinnerItemLocalizedString) {
            getApplication<Application>().getString(R.string.models_gender_female) ->
                gender.value = Gender.FEMALE

            getApplication<Application>().getString(R.string.models_gender_male) ->
                gender.value = Gender.MALE

            else -> Timber.e("Found unexpected spinner item localized string: $spinnerItemLocalizedString")
        }
    }

    suspend fun save(): Boolean {
        // TODO: return more exact errors which point to the invalid field & have a message to show below the field
        if (anyValueIsNull() || heightIsInhuman()!! || ageIsInhuman()!!) return false

        AppPreferences.heightInCentimeters = heightInCentimeters.value!!
        AppPreferences.birthYear = birthYear.value!!
        AppPreferences.gender = gender.value!!

        return true
    }

    private fun anyValueIsNull(): Boolean = heightInCentimeters.value == null || birthYear.value == null || gender.value == null
    private fun heightIsInhuman(): Boolean? = heightInCentimeters.value?.let { !HUMAN_HEIGHT_RANGE_IN_CENTIMETERS.contains(it) }
    private fun ageIsInhuman(): Boolean? = birthYear.value?.let { !HUMAN_AGE_RANGE.contains(DateTime.now().year - it) }
}
