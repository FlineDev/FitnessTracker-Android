package com.flinesoft.fitnesstracker

import com.flinesoft.fitnesstracker.helpers.EspressoTest
import com.flinesoft.fitnesstracker.helpers.extensions.TestContext
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
        screenshot("1_SampleData_WorkoutsHistory")
    }

    @Test
    fun differentStatisticsTest() {
        clickOnFullyVisibleString(R.string.statistics_title)
        screenshot("2_SampleData_StatisticsHistory")
    }
}
