package com.flinesoft.fitnesstracker

import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import com.flinesoft.fitnesstracker.helpers.EspressoTest
import com.flinesoft.fitnesstracker.helpers.TestContext
import org.junit.Test
import org.junit.runner.RunWith
import tools.fastlane.screengrab.Screengrab.screenshot
import kotlin.time.ExperimentalTime

@ExperimentalTime
@LargeTest
@RunWith(AndroidJUnit4::class)
class OnboardingTest : EspressoTest() {
    override fun prepareContext() {
        TestContext.resetAll()
    }

    @Test
    fun onboardingTest() {
        checkStringsAreFullyVisible(R.string.onboarding_workouts_title)
        checkStringsAreFullyVisible(R.string.onboarding_workouts_description)
        screenshot("Onboarding_Page1")
        clickOnFullyVisibleView(R.id.next)

        checkStringsAreFullyVisible(R.string.onboarding_workout_reminders_title)
        checkStringsAreFullyVisible(R.string.onboarding_workout_reminders_description)
        screenshot("Onboarding_Page2")
        clickOnFullyVisibleView(R.id.next)

        checkStringsAreFullyVisible(R.string.onboarding_impediments_title)
        checkStringsAreFullyVisible(R.string.onboarding_impediments_description)
        screenshot("Onboarding_Page3")
        clickOnFullyVisibleView(R.id.next)

        checkStringsAreFullyVisible(R.string.onboarding_body_mass_index_title)
        checkStringsAreFullyVisible(R.string.onboarding_body_mass_index_description)
        screenshot("Onboarding_Page4")
        clickOnFullyVisibleView(R.id.next)

        checkStringsAreFullyVisible(R.string.onboarding_body_shape_index_title)
        checkStringsAreFullyVisible(R.string.onboarding_body_shape_index_description)
        screenshot("Onboarding_Page5")
        clickOnFullyVisibleView(R.id.done)

        checkStringsAreFullyVisible(R.string.workouts_header_title, R.string.workouts_history_empty_state)
        checkViewsDoNotExist(R.id.workoutEntry)
        checkViewsAreFullyVisible(R.id.nextWorkoutDateTextView, R.id.workoutsSpeedDial, R.id.historyEmptyStateTextView)
    }
}
