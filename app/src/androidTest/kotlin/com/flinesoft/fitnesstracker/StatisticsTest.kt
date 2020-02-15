package com.flinesoft.fitnesstracker

import com.flinesoft.fitnesstracker.helpers.EspressoTest
import com.flinesoft.fitnesstracker.helpers.extensions.TestContext
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
        screenshot("Statistics_PersonalDataPopup")
    }

    @Test
    fun subsequentStatisticsTest() {
        TestContext.skipInitialPersonalDataModal()

        clickOnFullyVisibleString(R.string.statistics_title)
        checkStringsAreFullyVisible(
            R.string.statistics_body_mass_index_tab_name,
            R.string.statistics_body_shape_index_tab_name,
            R.string.statistics_weight_tab_name,
            R.string.statistics_waist_circumference_tab_name
        )
        checkStringsDoNotExist(
            R.string.statistics_edit_personal_data_title,
            R.string.statistics_edit_personal_data_height_overline,
            R.string.statistics_edit_personal_data_gender_overline,
            R.string.statistics_edit_personal_data_birth_year_overline,
            R.string.global_action_cancel,
            R.string.global_action_save
        )
        screenshot("Statistics_EmptyState")

        // TODO: [2020-02-15] click through different tabs with empty state, add weight data and click through, add waist circumference and click through
    }
}
