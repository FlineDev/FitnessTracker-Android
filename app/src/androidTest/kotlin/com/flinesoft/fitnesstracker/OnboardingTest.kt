package com.flinesoft.fitnesstracker


import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import com.flinesoft.fitnesstracker.helpers.EspressoTest
import org.junit.Test
import org.junit.runner.RunWith
import tools.fastlane.screengrab.Screengrab.screenshot
import kotlin.time.ExperimentalTime

@ExperimentalTime
@LargeTest
@RunWith(AndroidJUnit4::class)
class OnboardingTest: EspressoTest() {
    @Test
    fun onboardingTest() {
        checkTextIsFullyVisible(R.string.onboarding_workouts_title)
        checkTextIsFullyVisible(R.string.onboarding_workouts_description)
        screenshot("Onboarding_Page1")
        clickOnFullyVisibleView(R.id.next)

        checkTextIsFullyVisible(R.string.onboarding_workout_reminders_title)
        checkTextIsFullyVisible(R.string.onboarding_workout_reminders_description)
        screenshot("Onboarding_Page2")
        clickOnFullyVisibleView(R.id.next)

        checkTextIsFullyVisible(R.string.onboarding_impediments_title)
        checkTextIsFullyVisible(R.string.onboarding_impediments_description)
        screenshot("Onboarding_Page3")
        clickOnFullyVisibleView(R.id.next)

        checkTextIsFullyVisible(R.string.onboarding_body_mass_index_title)
        checkTextIsFullyVisible(R.string.onboarding_body_mass_index_description)
        screenshot("Onboarding_Page4")
        clickOnFullyVisibleView(R.id.next)

        checkTextIsFullyVisible(R.string.onboarding_body_shape_index_title)
        checkTextIsFullyVisible(R.string.onboarding_body_shape_index_description)
        screenshot("Onboarding_Page5")
        clickOnFullyVisibleView(R.id.done)
    }
}
