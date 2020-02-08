package com.flinesoft.fitnesstracker

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.ClassRule
import org.junit.Test
import org.junit.runner.RunWith
import tools.fastlane.screengrab.Screengrab
import tools.fastlane.screengrab.cleanstatusbar.CleanStatusBar
import tools.fastlane.screengrab.locale.LocaleTestRule

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    companion object {
        @ClassRule
        @JvmField
        public val localeTestRule = LocaleTestRule()
    }

    @Before
    fun setUp() {
        CleanStatusBar.enableWithDefaults()
    }

    @After
    fun tearDown() {
        CleanStatusBar.disable()
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.flinesoft.fitnesstracker", appContext.packageName)

        Screengrab.screenshot("MainScreen");
    }
}
