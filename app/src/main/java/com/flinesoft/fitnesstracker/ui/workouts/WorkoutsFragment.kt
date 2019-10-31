package com.flinesoft.fitnesstracker.ui.workouts

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.view.menu.MenuBuilder
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.flinesoft.fitnesstracker.R
import com.leinardi.android.speeddial.SpeedDialView

class WorkoutsFragment : Fragment() {
    private lateinit var workoutsViewModel: WorkoutsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        workoutsViewModel = ViewModelProviders.of(this).get(WorkoutsViewModel::class.java)

        val rootView: View = inflater.inflate(R.layout.fragment_workouts, container, false)
        val textView: TextView = rootView.findViewById(R.id.text_workouts)
        workoutsViewModel.text.observe(this, Observer { textView.text = it })

        setHasOptionsMenu(true)
        configureFloatingActionButtonWithSpeedDial(rootView)

        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.workouts_overflow_menu, menu)

        if (menu is MenuBuilder) {
            menu.setOptionalIconsVisible(true)
        }
    }

    private fun configureFloatingActionButtonWithSpeedDial(rootView: View) {
        val speedDialView = rootView.findViewById<SpeedDialView>(R.id.workouts_speed_dial)
        speedDialView.inflate(R.menu.workouts_speed_dial_menu)
    }
}