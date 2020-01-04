package com.flinesoft.fitnesstracker.ui.workouts

import android.os.Bundle
import android.view.*
import androidx.appcompat.view.menu.MenuBuilder
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flinesoft.fitnesstracker.R
import com.flinesoft.fitnesstracker.databinding.WorkoutsFragmentBinding
import com.flinesoft.fitnesstracker.globals.extensions.database
import com.flinesoft.fitnesstracker.model.Workout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.leinardi.android.speeddial.SpeedDialView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.time.ExperimentalTime

@ExperimentalTime
class WorkoutsFragment : Fragment() {
    private lateinit var binding: WorkoutsFragmentBinding
    private lateinit var viewModel: WorkoutsViewModel

    private lateinit var historyAdapter: WorkoutsHistoryAdapter
    private lateinit var historyManager: RecyclerView.LayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = WorkoutsFragmentBinding.inflate(inflater)
        viewModel = ViewModelProviders.of(this).get(WorkoutsViewModel::class.java)

        setupRecyclerView()
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

    private fun setupRecyclerView() {
        historyManager = LinearLayoutManager(context)
        historyAdapter = WorkoutsHistoryAdapter(
            application = activity!!.application,
            workouts = viewModel.workouts,
            itemOnClickListener = View.OnClickListener { onItemClicked(it) },
            itemOnLongClickListener = View.OnLongClickListener { onItemLongClick(it) }
        )

        binding.historyRecyclerView.layoutManager = historyManager
        binding.historyRecyclerView.adapter = historyAdapter
    }

    private fun setupViewModelBinding() {
        viewModel.workouts.observe(this, Observer {
            historyAdapter.notifyDataSetChanged()
            binding.nextWorkoutDateTextView.text = viewModel.suggestedNextWorkoutDateString(context!!)
        })
    }

    private fun configureFloatingActionButtonWithSpeedDial() {
        binding.workoutsSpeedDial.inflate(R.menu.workouts_speed_dial_menu)
        binding.workoutsSpeedDial.setOnActionSelectedListener(SpeedDialView.OnActionSelectedListener { actionItem ->
            when (actionItem.id) {
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

    private fun showNewWorkoutForm() {
        findNavController().navigate(
            WorkoutsFragmentDirections.actionWorkoutsToEditWorkout(
                title = getString(R.string.workouts_edit_workout_title_new)
            )
        )
    }

    private fun showEditWorkoutForm(workout: Workout) {
        findNavController().navigate(
            WorkoutsFragmentDirections.actionWorkoutsToEditWorkout(
                existingWorkoutId = workout.id,
                title = getString(R.string.workouts_edit_workout_title_edit)
            )
        )
    }

    private fun onItemClicked(view: View) {
        val itemIndex = binding.historyRecyclerView.getChildLayoutPosition(view)
        val workout = viewModel.workouts.value!![itemIndex]
        showEditWorkoutForm(workout)
    }

    private fun onItemLongClick(view: View): Boolean {
        val itemIndex = binding.historyRecyclerView.getChildLayoutPosition(view)
        val workout = viewModel.workouts.value!![itemIndex]

        MaterialAlertDialogBuilder(context)
            .setTitle(getString(R.string.global_common_dialogs_confirm_delete_title))
            .setMessage(R.string.global_common_dialogs_confirm_delete_message)
            .setNegativeButton(R.string.global_action_delete) { _, _ ->
                GlobalScope.launch { database().workoutDao.delete(workout) }
            }
            .setNeutralButton(R.string.global_action_cancel) { _, _ -> /* will auto-cancel */ }
            .show()

        return true
    }
}
