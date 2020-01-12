package com.flinesoft.fitnesstracker

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.flinesoft.fitnesstracker.globals.AppPreferences
import com.github.paolorotolo.appintro.AppIntro
import com.github.paolorotolo.appintro.AppIntroFragment
import com.github.paolorotolo.appintro.model.SliderPage
import kotlin.time.ExperimentalTime

@ExperimentalTime
class OnboardingActivity : AppIntro() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupSlides()
        setupDesign()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)

        AppPreferences.onboardingCompleted = true
        closeAndOpenMainActivity()
    }

    private fun setupSlides() {
        addSlide(AppIntroFragment.newInstance(workoutsSliderPage()))
        addSlide(AppIntroFragment.newInstance(workoutRemindersSliderPage()))
        addSlide(AppIntroFragment.newInstance(bodyMassIndexSliderPage()))
        addSlide(AppIntroFragment.newInstance(bodyShapeIndexSliderPage()))
    }

    private fun setupDesign() {
        showSkipButton(false)

        setBarColor(getColor(R.color.primaryVariant))
        doneButton.setBackgroundColor(getColor(R.color.primaryVariant))

        setSeparatorColor(getColor(R.color.onPrimaryGray))
        setIndicatorColor(getColor(R.color.onPrimary), getColor(R.color.onPrimaryGray))

        setNextArrowColor(getColor(R.color.secondary))
        setColorDoneText(getColor(R.color.secondary))

        supportActionBar?.hide()
    }

    private fun closeAndOpenMainActivity() {
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
        finish()
    }

    private fun workoutsSliderPage(): SliderPage = sliderPage(
        title = getString(R.string.onboarding_workouts_title),
        description = getString(R.string.onboarding_workouts_description),
        imageDrawable = R.drawable.ic_workouts
    )

    private fun workoutRemindersSliderPage(): SliderPage = sliderPage(
        title = getString(R.string.onboarding_workout_reminders_title),
        description = getString(R.string.onboarding_workout_reminders_description),
        imageDrawable = R.drawable.ic_workouts
    )

    private fun bodyMassIndexSliderPage(): SliderPage = sliderPage(
        title = getString(R.string.onboarding_body_mass_index_title),
        description = getString(R.string.onboarding_body_mass_index_description),
        imageDrawable = R.drawable.ic_statistics
    )

    private fun bodyShapeIndexSliderPage(): SliderPage = sliderPage(
        title = getString(R.string.onboarding_body_shape_index_title),
        description = getString(R.string.onboarding_body_shape_index_description),
        imageDrawable = R.drawable.ic_statistics
    )

    private fun sliderPage(title: String, description: String, imageDrawable: Int): SliderPage = SliderPage().apply {
        bgColor = getColor(R.color.primary)
        titleColor = getColor(R.color.onPrimary)
        descColor = getColor(R.color.onPrimary)

        this.title = title
        this.description = description
        this.imageDrawable = imageDrawable
    }
}
