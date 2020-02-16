package com.flinesoft.fitnesstracker

import com.flinesoft.fitnesstracker.helpers.EspressoTest
import com.flinesoft.fitnesstracker.helpers.TestContext
import org.junit.Test
import tools.fastlane.screengrab.Screengrab.screenshot
import kotlin.time.ExperimentalTime

@ExperimentalTime
class StatisticsTest: EspressoTest() {
    override fun prepareContext() {
        TestContext.resetAll()
        TestContext.skipOnboarding()
    }

    @Test
    fun initialPersonalDataTest() {
        clickOnFullyVisibleString(R.string.statistics_title)

        Thread.sleep(1_000)
        checkStringsAreFullyVisible(
            R.string.statistics_edit_personal_data_title,
            R.string.statistics_edit_personal_data_height_overline,
            R.string.statistics_edit_personal_data_gender_overline,
            R.string.statistics_edit_personal_data_birth_year_overline,
            R.string.global_action_cancel,
            R.string.global_action_save
        )
        checkStringsDoNotExist(
            R.string.statistics_body_mass_index_tab_name,
            R.string.statistics_body_shape_index_tab_name,
            R.string.statistics_weight_tab_name,
            R.string.statistics_waist_circumference_tab_name
        )
        screenshot("Statistics_PersonalData_EmptyState")

        clickOnFullyVisibleString(R.string.global_action_save)
        checkStringsAreFullyVisible(R.string.global_error_invalid_input)
        screenshot("Statistics_PersonalData_InvalidData")
        waitForSnackBarToDisappear()

        clickOnFullyVisibleView(R.id.heightEditText)
        checkStringsAreFullyVisible(R.string.statistics_edit_personal_data_height_input_dialog_title)
        screenshot("Statistics_PersonalData_ChooseHeight")

        clickOnFullyVisibleString(R.string.global_action_confirm)
        checkStringsDoNotExist(R.string.statistics_edit_personal_data_height_input_dialog_title)
        clickOnFullyVisibleView(R.id.birthYearEditText)
        checkStringsAreFullyVisible(R.string.statistics_edit_personal_data_birth_year_input_dialog_title)
        screenshot("Statistics_PersonalData_ChooseBirthYear")

        clickOnFullyVisibleString(R.string.global_action_confirm)
        checkStringsDoNotExist(R.string.statistics_edit_personal_data_birth_year_input_dialog_title)
        screenshot("Statistics_PersonalData_Filled")

        clickOnFullyVisibleString(R.string.global_action_save)
        checkStringsDoNotExist(
            R.string.statistics_edit_personal_data_title,
            R.string.statistics_edit_personal_data_height_overline,
            R.string.statistics_edit_personal_data_gender_overline,
            R.string.statistics_edit_personal_data_birth_year_overline,
            R.string.global_action_cancel,
            R.string.global_action_save
        )
        checkStringsAreFullyVisible(
            R.string.statistics_body_mass_index_tab_name,
            R.string.statistics_body_shape_index_tab_name,
            R.string.statistics_weight_tab_name,
            R.string.statistics_waist_circumference_tab_name
        )
    }

    @Test
    fun subsequentStatisticsTest() {
        TestContext.skipInitialPersonalDataModal()

        clickOnFullyVisibleString(R.string.statistics_title)
        checkStringsAreFullyVisible(
            R.string.statistics_body_mass_index_tab_name,
            R.string.statistics_body_shape_index_tab_name,
            R.string.statistics_weight_tab_name,
            R.string.statistics_waist_circumference_tab_name,
            R.string.statistics_body_mass_index_title,
            R.string.statistics_body_mass_index_explanation
        )
        checkStringsDoNotExist(
            R.string.statistics_edit_personal_data_title,
            R.string.statistics_edit_personal_data_height_overline,
            R.string.statistics_edit_personal_data_gender_overline,
            R.string.statistics_edit_personal_data_birth_year_overline,
            R.string.global_action_cancel,
            R.string.global_action_save
        )
        screenshot("Statistics_BodyMassIndex_EmptyState")

        clickOnFullyVisibleString(R.string.statistics_body_shape_index_tab_name)
        checkStringsAreFullyVisible(R.string.statistics_body_shape_index_title, R.string.statistics_body_shape_index_explanation)
        screenshot("Statistics_BodyShapeIndex_EmptyState")

        clickOnFullyVisibleString(R.string.statistics_weight_tab_name)
        checkStringIsFullyVisibleInView(R.string.statistics_weight_title, R.id.titleTextView)
        screenshot("Statistics_Weight_EmptyState")

        clickOnFullyVisibleString(R.string.statistics_waist_circumference_tab_name)
        checkStringsAreFullyVisible(R.string.statistics_waist_circumference_title)
        screenshot("Statistics_WaistCircumference_EmptyState")

        clickOnFullyVisibleView(R.id.statisticsSpeedDial)
        checkStringsAreFullyVisible(R.string.statistics_speed_dial_waist_circumference, R.string.statistics_speed_dial_weight)
        screenshot("Statistics_EmptyState_OpenSpeedDial")

        clickOnFullyVisibleString(R.string.statistics_speed_dial_weight)
        checkStringsAreFullyVisible(R.string.statistics_speed_dial_weight, R.string.global_action_cancel, R.string.global_action_confirm)

        clickOnFullyVisibleString(R.string.global_action_confirm)
        waitForSnackBarToDisappear()
        clickOnFullyVisibleString(R.string.statistics_body_mass_index_tab_name)
        checkStringsAreFullyVisible(R.string.statistics_body_mass_index_title, R.string.statistics_body_mass_index_explanation)
        screenshot("Statistics_FirstWeightEntry_BodyMassIndex")

        clickOnFullyVisibleString(R.string.statistics_body_shape_index_tab_name)
        checkStringsAreFullyVisible(R.string.statistics_body_shape_index_title, R.string.statistics_body_shape_index_explanation)
        screenshot("Statistics_FirstWeightEntry_BodyShapeIndex")

        clickOnFullyVisibleString(R.string.statistics_weight_tab_name)
        checkStringIsFullyVisibleInView(R.string.statistics_weight_title, R.id.titleTextView)
        screenshot("Statistics_FirstWeightEntry_Weight")

        clickOnFullyVisibleString(R.string.statistics_waist_circumference_tab_name)
        checkStringsAreFullyVisible(R.string.statistics_waist_circumference_title)
        screenshot("Statistics_FirstWeightEntry_WaistCircumference")

        clickOnFullyVisibleView(R.id.statisticsSpeedDial)
        checkStringsAreFullyVisible(R.string.statistics_speed_dial_waist_circumference, R.string.statistics_speed_dial_weight)
        screenshot("Statistics_FirstWeightEntry_OpenSpeedDial")

        clickOnFullyVisibleString(R.string.statistics_speed_dial_waist_circumference)
        checkStringsAreFullyVisible(R.string.statistics_speed_dial_waist_circumference, R.string.global_action_cancel, R.string.global_action_confirm)

        clickOnFullyVisibleString(R.string.global_action_confirm)
        waitForSnackBarToDisappear()
        clickOnFullyVisibleString(R.string.statistics_body_mass_index_tab_name)
        checkStringsAreFullyVisible(R.string.statistics_body_mass_index_title, R.string.statistics_body_mass_index_explanation)
        screenshot("Statistics_FirstBothEntries_BodyMassIndex")

        clickOnFullyVisibleString(R.string.statistics_body_shape_index_tab_name)
        checkStringsAreFullyVisible(R.string.statistics_body_shape_index_title, R.string.statistics_body_shape_index_explanation)
        screenshot("Statistics_FirstBothEntries_BodyShapeIndex")

        clickOnFullyVisibleString(R.string.statistics_weight_tab_name)
        checkStringIsFullyVisibleInView(R.string.statistics_weight_title, R.id.titleTextView)
        screenshot("Statistics_FirstBothEntries_Weight")

        clickOnFullyVisibleString(R.string.statistics_waist_circumference_tab_name)
        checkStringsAreFullyVisible(R.string.statistics_waist_circumference_title)
        screenshot("Statistics_FirstBothEntries_WaistCircumference")
    }
}
