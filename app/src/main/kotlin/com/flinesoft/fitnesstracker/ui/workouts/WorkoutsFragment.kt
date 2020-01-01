package com.flinesoft.fitnesstracker.ui.workouts

import android.os.Bundle
import android.view.*
import androidx.appcompat.view.menu.MenuBuilder
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.flinesoft.fitnesstracker.R
import com.flinesoft.fitnesstracker.databinding.WorkoutsFragmentBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.leinardi.android.speeddial.SpeedDialView
import timber.log.Timber

class WorkoutsFragment : Fragment() {
    private lateinit var binding: WorkoutsFragmentBinding
    private lateinit var viewModel: WorkoutsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = WorkoutsFragmentBinding.inflate(inflater)
        viewModel = ViewModelProviders.of(this).get(WorkoutsViewModel::class.java)

        setupViewModelBinding()
        setHasOptionsMenu(true)
        configureFloatingActionButtonWithSpeedDial()

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.workouts_overflow_menu, menu)

        if (menu is MenuBuilder) {
            menu.setOptionalIconsVisible(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.workouts_overflow_reminder -> {
                showRemindersForm()
                true
            }

            else -> {
                Timber.e("unknown overflow item id clicked: '${item.itemId}'")
                false
            }
        }
    }

    private fun setupViewModelBinding() {
        // TODO: not yet implemented
    }

    private fun configureFloatingActionButtonWithSpeedDial() {
        binding.workoutsSpeedDial.inflate(R.menu.workouts_speed_dial_menu)
        binding.workoutsSpeedDial.setOnActionSelectedListener(SpeedDialView.OnActionSelectedListener { actionItem ->
            when (actionItem.id) {
                R.id.workouts_speed_dial_impediment -> {
                    showNewImpedimentForm()
                    return@OnActionSelectedListener true
                }

                R.id.workouts_speed_dial_workout -> {
                    showNewWorkoutForm()
                    return@OnActionSelectedListener true
                }

                else -> {
                    Timber.e("unknown speed dial action id clicked: '${actionItem.id}'")
                    return@OnActionSelectedListener false
                }
            }
        })
    }

    private fun showRemindersForm() {
        MaterialAlertDialogBuilder(context)
            .setTitle(R.string.workouts_overflow_reminder)
            // TODO: not yet implemented
            .show()
    }

    private fun showNewImpedimentForm() {
        MaterialAlertDialogBuilder(context)
            .setTitle(R.string.workouts_speed_dial_impediment)
            // TODO: not yet implemented
            .show()
    }

    private fun showNewWorkoutForm() {
        findNavController().navigate(R.id.action_workouts_to_edit_workout)
    }
}
