package com.flinesoft.fitnesstracker

import com.flinesoft.fitnesstracker.helpers.EspressoTest
import com.flinesoft.fitnesstracker.helpers.TestContext
import org.junit.Test
import tools.fastlane.screengrab.Screengrab.screenshot
import kotlin.time.ExperimentalTime

@ExperimentalTime
class SampleDataTest: EspressoTest() {
    override fun prepareContext() {
        TestContext.resetAll()
        TestContext.withSampleData()
    }

    @Test
    fun workoutsHistoryTest() {
        clickOnFullyVisibleString(R.string.workouts_header_title) // workaround to fix pre-selected first item
        screenshot("1_SampleData_WorkoutsHistory")
    }

    @Test
    fun differentStatisticsTest() {
        clickOnFullyVisibleString(R.string.statistics_title)

        clickOnFullyVisibleString(R.string.statistics_body_mass_index_tab_name)
        screenshot("2_SampleData_StatisticsHistory_BMI")

        clickOnFullyVisibleString(R.string.statistics_body_shape_index_tab_name)
        screenshot("3_SampleData_StatisticsHistory_ABSI")

        clickOnFullyVisibleString(R.string.statistics_weight_tab_name)
        screenshot("4_SampleData_StatisticsHistory_Weight")

        clickOnFullyVisibleString(R.string.statistics_waist_circumference_tab_name)
        screenshot("5_SampleData_StatisticsHistory_WaistCircumference")
    }
}
