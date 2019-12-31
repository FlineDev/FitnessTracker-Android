package com.flinesoft.fitnesstracker.ui.workouts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.flinesoft.fitnesstracker.R
import com.flinesoft.fitnesstracker.globals.BackNavigationFragment

class EditWorkoutFragment : BackNavigationFragment() {
    private lateinit var viewModel: EditWorkoutViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(EditWorkoutViewModel::class.java)
        val rootView: View = inflater.inflate(R.layout.fragment_edit_workout, container, false)

        // TODO: do something with rootView

        setupBackNavigation()
        return rootView
    }
}
