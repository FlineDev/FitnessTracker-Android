package com.flinesoft.fitnesstracker

import com.flinesoft.fitnesstracker.helpers.EspressoTest
import com.flinesoft.fitnesstracker.helpers.extensions.TestContext
import org.junit.Test
import tools.fastlane.screengrab.Screengrab.screenshot
import kotlin.time.ExperimentalTime

@ExperimentalTime
class WorkoutsTest: EspressoTest() {
    override fun prepareContext() {
        TestContext.resetAll()
        TestContext.skipOnboarding()
    }

    @Test
    fun workoutsTest() {
        checkStringsAreFullyVisible(R.string.workouts_header_title, R.string.workouts_history_empty_state)
        checkViewsDoNotExist(R.id.workoutEntry)
        checkViewsAreFullyVisible(R.id.nextWorkoutDateTextView, R.id.workoutsSpeedDial, R.id.historyEmptyStateTextView)
        screenshot("Workouts_EmptyHistory")

        clickOnFullyVisibleView(R.id.workoutsSpeedDial)
        checkStringsAreFullyVisible(R.string.workouts_speed_dial_impediment)
        checkStringsAreFullyVisible(R.string.workouts_speed_dial_workout)
        screenshot("Workouts_EmptyHistory_OpenSpeedDial")

        clickOnFullyVisibleText(R.string.workouts_speed_dial_workout)
        checkStringsAreFullyVisible(R.string.workouts_edit_workout_title_new)
        checkViewsAreFullyVisible(R.id.workoutTypeSpinner, R.id.startDateEditText, R.id.startTimeEditText, R.id.startDateEditText, R.id.startTimeEditText)
        screenshot("Workouts_NewWorkout")

        clickOnFullyVisibleText(R.string.global_action_save)
        checkStringsAreFullyVisible(R.string.workouts_header_title)
        checkStringsDoNotExist(R.string.workouts_history_empty_state)
        checkViewsDoNotExist(R.id.impedimentEntry)
        checkViewsAreFullyVisible(R.id.nextWorkoutDateTextView, R.id.workoutsSpeedDial, R.id.workoutEntry)
        screenshot("Workouts_FirstEntryHistory")

        clickOnFullyVisibleView(R.id.workoutsSpeedDial)
        clickOnFullyVisibleText(R.string.workouts_speed_dial_impediment)
        checkStringsAreFullyVisible(R.string.workouts_edit_impediment_title_new)
        checkViewsAreFullyVisible(R.id.nameEditText, R.id.startDateEditText, R.id.endDateEditText)
        screenshot("Workouts_NewImpediment")

        clickOnFullyVisibleText(R.string.global_action_save)
        checkStringsAreFullyVisible(R.string.global_error_invalid_input, R.string.workouts_edit_impediment_title_new)
        screenshot("Workouts_NewImpediment_InvalidInput")
        Thread.sleep(3_000)

        typeTextInFullyVisibleView("Cold", R.id.nameEditText)
        clickOnFullyVisibleText(R.string.global_action_save)
        checkTextsAreFullyVisible("Cold")
        checkStringsAreFullyVisible(R.string.workouts_header_title)
        checkStringsDoNotExist(R.string.workouts_history_empty_state)
        checkViewsAreFullyVisible(R.id.nextWorkoutDateTextView, R.id.workoutsSpeedDial, R.id.workoutEntry, R.id.impedimentEntry)
        screenshot("Workouts_TwoEntriesHistory")

        // TODO: [2020-02-09] also test editing of workout, impediment and reminder settings
    }
}
