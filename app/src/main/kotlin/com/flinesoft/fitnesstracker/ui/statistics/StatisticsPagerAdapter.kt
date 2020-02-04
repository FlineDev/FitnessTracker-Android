package com.flinesoft.fitnesstracker.ui.statistics

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import kotlin.time.ExperimentalTime

@ExperimentalTime
class StatisticsPagerAdapter(
    val viewModel: StatisticsViewModel,
    manager: FragmentManager
) : FragmentPagerAdapter(manager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    enum class Page { BODY_MASS_INDEX, BODY_SHAPE_INDEX }

    override fun getItem(position: Int): Fragment = StatisticsPageFragment(pageViewModel(position))

    override fun getCount(): Int = Page.values().size

    override fun getPageTitle(position: Int): CharSequence? = pageViewModel(position).tabName

    private fun pageViewModel(position: Int): StatisticsPageViewModel = viewModel.pageViewModel(Page.values()[position])
}
