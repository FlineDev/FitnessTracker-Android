package com.flinesoft.fitnesstracker.ui.workouts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.flinesoft.fitnesstracker.R

class WorkoutsFragment : Fragment() {

    private lateinit var workoutsViewModel: WorkoutsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        workoutsViewModel =
            ViewModelProviders.of(this).get(WorkoutsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_workouts, container, false)
        val textView: TextView = root.findViewById(R.id.text_workouts)
        workoutsViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}