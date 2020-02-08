package com.flinesoft.fitnesstracker


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.flinesoft.fitnesstracker.globals.AppPreferences
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.*
import org.junit.runner.RunWith
import tools.fastlane.screengrab.Screengrab
import tools.fastlane.screengrab.locale.LocaleTestRule
import kotlin.time.ExperimentalTime

@ExperimentalTime
@LargeTest
@RunWith(AndroidJUnit4::class)
class OnboardingTest {
    companion object {
        @ClassRule
        @JvmField
        public val localeTestRule = LocaleTestRule()
    }

    @Before
    fun setUp() {
        // TODO: [2020-02-08] waiting for https://github.com/fastlane/fastlane/issues/15777 being fixed
//        CleanStatusBar.enableWithDefaults()
        AppPreferences.setup(InstrumentationRegistry.getInstrumentation().targetContext.applicationContext)
        AppPreferences.clear()
    }

    @After
    fun tearDown() {
        // TODO: [2020-02-08] waiting for https://github.com/fastlane/fastlane/issues/15777 being fixed
//        CleanStatusBar.disable()
        AppPreferences.clear()
    }

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun onboardingTest() {
        Screengrab.screenshot("Onboarding_Page1")
        val appCompatImageButton = onView(
            allOf(
                withId(R.id.next),
                childAtPosition(
                    allOf(
                        withId(R.id.bottomContainer),
                        childAtPosition(
                            withId(R.id.bottom),
                            1
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        Screengrab.screenshot("Onboarding_Page2")
        val appCompatImageButton2 = onView(
            allOf(
                withId(R.id.next),
                childAtPosition(
                    allOf(
                        withId(R.id.bottomContainer),
                        childAtPosition(
                            withId(R.id.bottom),
                            1
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatImageButton2.perform(click())

        Screengrab.screenshot("Onboarding_Page3")
        val appCompatImageButton3 = onView(
            allOf(
                withId(R.id.next),
                childAtPosition(
                    allOf(
                        withId(R.id.bottomContainer),
                        childAtPosition(
                            withId(R.id.bottom),
                            1
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatImageButton3.perform(click())

        Screengrab.screenshot("Onboarding_Page4")
        val appCompatImageButton4 = onView(
            allOf(
                withId(R.id.next),
                childAtPosition(
                    allOf(
                        withId(R.id.bottomContainer),
                        childAtPosition(
                            withId(R.id.bottom),
                            1
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatImageButton4.perform(click())

        Screengrab.screenshot("Onboarding_Page6")
        val materialButton = onView(
            allOf(
                withId(R.id.done),
                childAtPosition(
                    allOf(
                        withId(R.id.bottomContainer),
                        childAtPosition(
                            withId(R.id.bottom),
                            1
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
