package com.flinesoft.fitnesstracker.helpers

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matchers.allOf

open class EspressoTest {
    fun clickOnFullyVisibleView(@IdRes id: Int): ViewInteraction = onView(allOf(withId(id), isCompletelyDisplayed())).perform(click())
    fun checkViewIsFullyVisible(@IdRes id: Int): ViewInteraction = onView(allOf(withId(id))).check(matches(isCompletelyDisplayed()))
    fun checkTextIsFullyVisible(@StringRes id: Int): ViewInteraction = onView(allOf(withText(id))).check(matches(isCompletelyDisplayed()))
}
