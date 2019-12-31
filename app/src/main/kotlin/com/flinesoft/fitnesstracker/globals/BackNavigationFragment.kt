package com.flinesoft.fitnesstracker.globals

import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

abstract class BackNavigationFragment : Fragment() {
    fun setupBackNavigation() {
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().navigateUp()
        return true
    }
}
