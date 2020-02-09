package com.flinesoft.fitnesstracker.helpers.extensions

import androidx.test.platform.app.InstrumentationRegistry
import com.flinesoft.fitnesstracker.globals.AppPreferences
import kotlin.time.ExperimentalTime

@ExperimentalTime
object TestContext {
    fun resetAll() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        AppPreferences.setup(appContext)
        AppPreferences.clear()
        // TODO: [2020-02-09] reset database to be empty
    }

    fun resetToOnboardingCompleted() {
        resetAll()
        AppPreferences.onboardingCompleted = true
    }

    fun resetToSampleData() {
        resetToOnboardingCompleted()
        // TODO: [2020-02-09] setup some data in database
    }
}
