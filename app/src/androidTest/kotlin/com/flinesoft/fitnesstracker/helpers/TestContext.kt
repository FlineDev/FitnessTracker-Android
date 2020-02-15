package com.flinesoft.fitnesstracker.helpers.extensions

import androidx.test.platform.app.InstrumentationRegistry
import com.flinesoft.fitnesstracker.globals.AppPreferences
import com.flinesoft.fitnesstracker.model.Gender
import com.flinesoft.fitnesstracker.persistence.FitnessTrackerDatabase
import kotlin.time.ExperimentalTime

@ExperimentalTime
object TestContext {
    fun resetAll() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        AppPreferences.setup(appContext)
        AppPreferences.clear()

        FitnessTrackerDatabase.getInstance(appContext).clearAllTables()
    }

    fun skipOnboarding() {
        AppPreferences.onboardingCompleted = true
    }

    fun skipInitialPersonalDataModal() {
        AppPreferences.heightInCentimeters = 170
        AppPreferences.birthYear = 1985
        AppPreferences.gender = Gender.FEMALE
    }

    fun withSampleData() {
        skipOnboarding()
        // TODO: [2020-02-09] setup some data in database
    }
}
