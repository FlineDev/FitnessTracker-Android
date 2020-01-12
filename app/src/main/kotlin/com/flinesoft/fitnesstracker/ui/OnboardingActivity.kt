package com.flinesoft.fitnesstracker.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.flinesoft.fitnesstracker.MainActivity
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
        addSlide(AppIntroFragment.newInstance(bodyMassIndexSliderPage()))
        addSlide(AppIntroFragment.newInstance(bodyShapeIndexSliderPage()))
    }

    private fun setupDesign() {
        showSkipButton(false)
    }

    private fun closeAndOpenMainActivity() {
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
        finish()
    }

    private fun workoutsSliderPage(): SliderPage = SliderPage().apply {
        // TODO("not yet implemented")
    }

    private fun bodyMassIndexSliderPage(): SliderPage = SliderPage().apply {
        // TODO("not yet implemented")
    }

    private fun bodyShapeIndexSliderPage(): SliderPage = SliderPage().apply {
        // TODO("not yet implemented")
    }
}
