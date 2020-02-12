package com.flinesoft.fitnesstracker.helpers

import android.widget.DatePicker
import android.widget.TimePicker
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.util.HumanReadables
import androidx.test.rule.ActivityTestRule
import com.flinesoft.fitnesstracker.MainActivity
import com.flinesoft.fitnesstracker.helpers.extensions.TestContext
import org.hamcrest.Matchers.*
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
    var mainActivityTestRule = ActivityTestRule(MainActivity::class.java, false, false)

    /**
     * Override this method and make calls to `TestContext` in the overridden method. No need to call it on `super`.
     */
    open fun prepareContext() {}

    /**
     * Do not override this to put any context changes (like setting up database / shared preferences). Use `prepareContext` instead.
     * This method will call `prepareContext` before each test and will call it in the correct order.
     */
    @Before
    open fun setUp() {
        prepareContext()
        launchApplication()
        CleanStatusBar.enableWithDefaults()
    }

    @After
    open fun tearDown() {
        CleanStatusBar.disable()
        TestContext.resetAll()
    }

    private fun launchApplication() {
        mainActivityTestRule.launchActivity(null)
        Thread.sleep(1_000)
    }

    fun clickOnFullyVisibleView(@IdRes id: Int): ViewInteraction = onView(allOf(withId(id), isCompletelyDisplayed())).perform(click())
    fun clickOnFullyVisibleString(@StringRes id: Int): ViewInteraction = onView(allOf(withText(id), isCompletelyDisplayed())).perform(click())
    fun clickOnFullyVisibleText(text: String): ViewInteraction = onView(allOf(withText(text), isCompletelyDisplayed())).perform(click())
    fun openActionBarOverflowOrOptionsMenu() = openActionBarOverflowOrOptionsMenu(mainActivityTestRule.activity.applicationContext)

    fun typeTextInFullyVisibleView(text: String, @IdRes id: Int): ViewInteraction = onView(
        allOf(withId(id), isCompletelyDisplayed())
    ).perform(scrollTo(), click(), clearText(), typeText(text), closeSoftKeyboard())

    fun chooseDateInFullyVisibleDatePicker(year: Int, monthOfYear: Int, dayOfMonth: Int): ViewInteraction = onView(
        withClassName(equalTo(DatePicker::class.java.name))
    ).check(
        matches(isCompletelyDisplayed())
    ).perform(PickerActions.setDate(year, monthOfYear, dayOfMonth))

    fun chooseTimeInFullyVisibleTimePicker(hours: Int, minutes: Int): ViewInteraction = onView(
        withClassName(equalTo(TimePicker::class.java.name))
    ).check(
        matches(isCompletelyDisplayed())
    ).perform(PickerActions.setTime(hours, minutes))

    fun checkViewsDoNotExist(@IdRes vararg ids: Int) = ids.forEach { onView(withId(it)).check(isNotDisplayed()) }
    fun checkStringsDoNotExist(@IdRes vararg ids: Int) = ids.forEach { onView(withText(it)).check(isNotDisplayed()) }
    fun checkTextsDoNotExist(vararg texts: String) = texts.forEach { onView(withText(it)).check(isNotDisplayed()) }

    fun checkViewsAreFullyVisible(@IdRes vararg ids: Int) = ids.forEach { onView(withId(it)).check(matches(isCompletelyDisplayed())) }
    fun checkStringsAreFullyVisible(@IdRes vararg ids: Int) = ids.forEach { onView(withText(it)).check(matches(isCompletelyDisplayed())) }
    fun checkTextsAreFullyVisible(vararg texts: String) = texts.forEach { onView(withText(it)).check(matches(isCompletelyDisplayed())) }

    fun checkEditTextsAreEnabled(@IdRes vararg ids: Int) = ids.forEach { onView(withId(it)).check(matches(isEnabled())) }
    fun checkEditTextsAreDisabled(@IdRes vararg ids: Int) = ids.forEach { onView(withId(it)).check(matches(not(isEnabled()))) }

    private fun isNotDisplayed(): ViewAssertion? {
        return ViewAssertion { view, _ ->
            if (view != null && isDisplayed().matches(view)) {
                throw AssertionError("View is present in the hierarchy and displayed: " + HumanReadables.describe(view))
            }
        }
    }
}
