package com.flinesoft.fitnesstracker.globals.extensions

import androidx.fragment.app.Fragment
import com.flinesoft.fitnesstracker.persistence.FitnessTrackerDatabase

fun Fragment.database(): FitnessTrackerDatabase = FitnessTrackerDatabase.getInstance(activity!!.application)
