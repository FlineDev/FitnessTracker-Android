package com.flinesoft.fitnesstracker.helpers

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.flinesoft.fitnesstracker.MainActivity
import com.flinesoft.fitnesstracker.helpers.extensions.TestContext
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import tools.fastlane.screengrab.cleanstatusbar.CleanStatusBar
import tools.fastlane.screengrab.locale.LocaleTestRule
import kotlin.time.ExperimentalTime

@ExperimentalTime
open class EspressoTest {
    companion object {
        @ClassRule
        @JvmField
        public val localeTestRule = LocaleTestRule()
    }

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    open fun setUp() {
        TestContext.resetAll()
        CleanStatusBar.enableWithDefaults()
    }

    @After
    open fun tearDown() {
        CleanStatusBar.disable()
        TestContext.resetAll()
    }

    fun clickOnFullyVisibleView(@IdRes id: Int): ViewInteraction = onView(allOf(withId(id), isCompletelyDisplayed())).perform(click())
    fun checkViewIsFullyVisible(@IdRes id: Int): ViewInteraction = onView(allOf(withId(id))).check(matches(isCompletelyDisplayed()))
    fun checkTextIsFullyVisible(@StringRes id: Int): ViewInteraction = onView(allOf(withText(id))).check(matches(isCompletelyDisplayed()))
}
