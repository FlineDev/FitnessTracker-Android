package com.flinesoft.fitnesstracker.ui.statistics.editMeasurements

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flinesoft.fitnesstracker.R
import com.flinesoft.fitnesstracker.databinding.EditMeasurementsFragmentBinding
import com.flinesoft.fitnesstracker.globals.extensions.database
import com.flinesoft.fitnesstracker.model.Measurement
import com.flinesoft.fitnesstracker.model.WaistCircumferenceMeasurement
import com.flinesoft.fitnesstracker.model.WeightMeasurement
import com.flinesoft.fitnesstracker.ui.shared.BackNavigationFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.time.ExperimentalTime

@ExperimentalTime
abstract class EditMeasurementsFragment<T : Measurement> : BackNavigationFragment() {
    protected lateinit var binding: EditMeasurementsFragmentBinding
    protected lateinit var viewModel: EditMeasurementsViewModel<T>

    private lateinit var adapter: EditMeasurementsAdapter<T>
    private lateinit var layoutManager: RecyclerView.LayoutManager

    protected fun setupRecyclerView() {
        layoutManager = LinearLayoutManager(context)
        adapter = EditMeasurementsAdapter(
            application = requireActivity().application,
            measurements = viewModel.measurements,
            itemOnClickListener = View.OnClickListener { onItemClicked(it) },
            itemOnLongClickListener = View.OnLongClickListener { onItemLongClick(it) }
        )

        binding.measurementsRecyclerView.layoutManager = layoutManager
        binding.measurementsRecyclerView.adapter = adapter
    }

    protected fun setupViewModelBinding() {
        viewModel.measurements.observe(viewLifecycleOwner, Observer { adapter.notifyDataSetChanged() })
    }

    protected abstract fun showEditMeasurementForm(measurement: T)

    private fun onItemClicked(view: View) {
        val itemIndex = binding.measurementsRecyclerView.getChildLayoutPosition(view)
        val measurement = viewModel.measurements.value!![itemIndex]
        showEditMeasurementForm(measurement)
    }

    private fun onItemLongClick(view: View): Boolean {
        val itemIndex = binding.measurementsRecyclerView.getChildLayoutPosition(view)
        val measurement = viewModel.measurements.value!![itemIndex]

        MaterialAlertDialogBuilder(context)
            .setTitle(getString(R.string.global_common_dialogs_confirm_delete_title))
            .setMessage(R.string.global_common_dialogs_confirm_delete_message)
            .setNegativeButton(R.string.global_action_delete) { _, _ ->
                GlobalScope.launch {
                    when (measurement) {
                        is WeightMeasurement -> database().weightMeasurementDao.delete(measurement)
                        is WaistCircumferenceMeasurement -> database().waistCircumferenceMeasurementDao.delete(measurement)
                        else -> Timber.e("Found unknown measurement type in object $measurement while deleting")
                    }
                }
            }
            .setNeutralButton(R.string.global_action_cancel) { _, _ -> /* will auto-cancel */ }
            .show()

        return true
    }
}
