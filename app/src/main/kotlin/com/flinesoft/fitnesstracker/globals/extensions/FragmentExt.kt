package com.flinesoft.fitnesstracker.globals.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import com.flinesoft.fitnesstracker.persistence.FitnessTrackerDatabase
import kotlin.time.ExperimentalTime

@ExperimentalTime
fun Fragment.database(): FitnessTrackerDatabase = FitnessTrackerDatabase.getInstance(activity!!.application)

@ExperimentalTime
fun AndroidViewModel.database(): FitnessTrackerDatabase = FitnessTrackerDatabase.getInstance(getApplication())
