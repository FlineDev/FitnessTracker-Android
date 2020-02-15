package com.flinesoft.fitnesstracker

import com.flinesoft.fitnesstracker.helpers.EspressoTest
import com.flinesoft.fitnesstracker.helpers.extensions.TestContext
import org.joda.time.DateTime
import org.junit.Test
//import tools.fastlane.screengrab.Screengrab.screenshot
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
//        screenshot("Workouts_EmptyHistory")

        clickOnFullyVisibleView(R.id.workoutsSpeedDial)
        checkStringsAreFullyVisible(R.string.workouts_speed_dial_impediment)
        checkStringsAreFullyVisible(R.string.workouts_speed_dial_workout)
//        screenshot("Workouts_EmptyHistory_OpenSpeedDial")

        clickOnFullyVisibleString(R.string.workouts_speed_dial_workout)
        checkStringsAreFullyVisible(R.string.workouts_edit_workout_title_new)
        checkViewsAreFullyVisible(R.id.workoutTypeSpinner, R.id.startDateEditText, R.id.startTimeEditText, R.id.startDateEditText, R.id.startTimeEditText)
//        screenshot("Workouts_NewWorkout")

        clickOnFullyVisibleString(R.string.global_action_save)
        checkStringsAreFullyVisible(R.string.workouts_header_title)
        checkStringsDoNotExist(R.string.workouts_history_empty_state)
        checkViewsDoNotExist(R.id.impedimentEntry)
        checkViewsAreFullyVisible(R.id.nextWorkoutDateTextView, R.id.workoutsSpeedDial, R.id.workoutEntry)
//        screenshot("Workouts_FirstEntryHistory")

        clickOnFullyVisibleView(R.id.workoutsSpeedDial)
        clickOnFullyVisibleString(R.string.workouts_speed_dial_impediment)
        checkStringsAreFullyVisible(R.string.workouts_edit_impediment_title_new)
        checkViewsAreFullyVisible(R.id.nameEditText, R.id.startDateEditText, R.id.endDateEditText)
//        screenshot("Workouts_NewImpediment")

        clickOnFullyVisibleString(R.string.global_action_save)
        checkStringsAreFullyVisible(R.string.global_error_invalid_input, R.string.workouts_edit_impediment_title_new)
//        screenshot("Workouts_NewImpediment_InvalidInput")
        Thread.sleep(3_000)

        typeTextInFullyVisibleView("Cold", R.id.nameEditText)
        clickOnFullyVisibleString(R.string.global_action_save)
        checkTextsAreFullyVisible("Cold")
        checkStringsAreFullyVisible(R.string.workouts_header_title)
        checkStringsDoNotExist(R.string.workouts_history_empty_state)
        checkViewsAreFullyVisible(R.id.nextWorkoutDateTextView, R.id.workoutsSpeedDial, R.id.workoutEntry, R.id.impedimentEntry)
//        screenshot("Workouts_TwoEntriesHistory")

        clickOnFullyVisibleString(R.string.models_workout_type_cardio)
        checkStringsAreFullyVisible(R.string.workouts_edit_workout_title_edit, R.string.global_action_cancel, R.string.global_action_save)
        checkStringsDoNotExist(R.string.workouts_header_title)
//        screenshot("Workouts_EditWorkout")

        clickOnFullyVisibleView(R.id.workoutTypeSpinner)
        clickOnFullyVisibleString(R.string.models_workout_type_muscle_building)
        clickOnFullyVisibleView(R.id.endTimeEditText)
//        screenshot("Workouts_EditWorkout_EndTime")

        val tenMinutesAgo = DateTime.now().minusMinutes(10)
        chooseTimeInFullyVisibleTimePicker(tenMinutesAgo.hourOfDay, tenMinutesAgo.minuteOfHour)
        clickOnFullyVisibleView(android.R.id.button1) // OK button
        clickOnFullyVisibleString(R.string.global_action_save)
        checkStringsDoNotExist(R.string.workouts_edit_workout_title_edit, R.string.global_action_cancel, R.string.global_action_save)
        checkStringsAreFullyVisible(R.string.workouts_header_title, R.string.models_workout_type_muscle_building)
//        screenshot("Workouts_TwoEntriesHistory_AfterEditWorkout")

        clickOnFullyVisibleText("Cold")
        checkStringsAreFullyVisible(R.string.workouts_edit_impediment_title_edit, R.string.global_action_cancel, R.string.global_action_save)
        checkStringsDoNotExist(R.string.workouts_header_title)
//        screenshot("Workouts_EditImpediment")

        typeTextInFullyVisibleView("Flu", R.id.nameEditText)
        clickOnFullyVisibleView(R.id.endDateEditText)
//        screenshot("Workouts_EditImpediment_EndTime")

        val aWeekFromNow = DateTime.now().plusDays(7)
        chooseDateInFullyVisibleDatePicker(aWeekFromNow.year, aWeekFromNow.monthOfYear, aWeekFromNow.dayOfMonth)
        clickOnFullyVisibleView(android.R.id.button1) // OK button
        clickOnFullyVisibleString(R.string.global_action_save)
        checkStringsDoNotExist(R.string.workouts_edit_impediment_title_edit, R.string.global_action_cancel, R.string.global_action_save)
        checkStringsAreFullyVisible(R.string.workouts_header_title)
        checkTextsAreFullyVisible("Flu")
//        screenshot("Workouts_TwoEntriesHistory_AfterEditImpediment")

        openActionBarOverflowOrOptionsMenu()
//        screenshot("Workouts_OverflowMenuOpened")

        checkStringsAreFullyVisible(R.string.workouts_overflow_reminder, R.string.shared_overflow_feedback)
        clickOnFullyVisibleString(R.string.workouts_overflow_reminder)
        checkEditTextsAreEnabled(R.id.reminderTimeEditText)
//        screenshot("Workouts_ConfigureReminders")

        clickOnFullyVisibleString(R.string.workouts_edit_reminders_reminder_on_title)
        checkEditTextsAreDisabled(R.id.reminderTimeEditText)
//        screenshot("Workouts_ConfigureReminders_TurnedOff")

        clickOnFullyVisibleString(R.string.global_action_save)
        checkStringsDoNotExist(R.string.workouts_overflow_reminder, R.string.shared_overflow_feedback, R.string.workouts_edit_reminders_reminder_on_title)
        checkStringsAreFullyVisible(R.string.workouts_header_title)
    }
}
