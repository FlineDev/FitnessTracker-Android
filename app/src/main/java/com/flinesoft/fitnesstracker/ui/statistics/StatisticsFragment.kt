package com.flinesoft.fitnesstracker.ui.statistics

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.appcompat.view.menu.MenuBuilder
import com.flinesoft.fitnesstracker.R
import com.leinardi.android.speeddial.SpeedDialView
import timber.log.Timber

class StatisticsFragment : Fragment() {
    private lateinit var statisticsViewModel: StatisticsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        statisticsViewModel = ViewModelProviders.of(this).get(StatisticsViewModel::class.java)

        val rootView: View = inflater.inflate(R.layout.fragment_statistics, container, false)
        val textView: TextView = rootView.findViewById(R.id.text_statistics)
        statisticsViewModel.text.observe(this, Observer { textView.text = it })

        setHasOptionsMenu(true)
        configureFloatingActionButtonWithSpeedDial(rootView)

        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.statistics_overflow_menu, menu)

        if (menu is MenuBuilder) {
            menu.setOptionalIconsVisible(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.statistics_overflow_data -> {
                Timber.d("data button pressed")
                return true
            }

            R.id.statistics_overflow_calc -> {
                Timber.d("calc button pressed")
                return true
            }

            else -> {
                Timber.e("unknown overflow item id clicked: '${item.itemId}'")
                return false
            }
        }
    }

    private fun configureFloatingActionButtonWithSpeedDial(rootView: View) {
        val speedDialView: SpeedDialView = rootView.findViewById(R.id.statistics_speed_dial)
        speedDialView.inflate(R.menu.statistics_speed_dial_menu)

        speedDialView.setOnActionSelectedListener(SpeedDialView.OnActionSelectedListener { actionItem ->
            when(actionItem.id) {
                R.id.statistics_speed_dial_waist_circumference -> {
                    Timber.d("waist circumference button pressed")
                    return@OnActionSelectedListener true
                }

                R.id.statistics_speed_dial_weight -> {
                    Timber.d("weight button pressed")
                    return@OnActionSelectedListener true
                }

                else -> {
                    Timber.e("unknown speed dial action id clicked: '${actionItem.id}'")
                    return@OnActionSelectedListener false
                }
            }
        })
    }
}