package com.flinesoft.fitnesstracker.ui.statistics

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.flinesoft.fitnesstracker.R
import com.flinesoft.fitnesstracker.databinding.StatisticsPageFragmentBinding
import com.flinesoft.fitnesstracker.globals.DownPopLevel
import com.flinesoft.fitnesstracker.globals.extensions.withAlphaDownPoppedToLevel
import com.flinesoft.fitnesstracker.globals.runIfDebugForTesting
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import org.joda.time.format.DateTimeFormat
import kotlin.math.max
import kotlin.math.min
import kotlin.time.ExperimentalTime
import kotlin.time.days

@ExperimentalTime
class StatisticsPageFragment(val viewModel: StatisticsPageViewModel) : Fragment() {
    private lateinit var binding: StatisticsPageFragmentBinding

    private val dataEntriesSet: LineDataSet = LineDataSet(emptyList(), "TODO")

    private val defaultTextSize: Float = 13.0f

    // NOTE: Workaround for this issue: https://github.com/PhilJay/MPAndroidChart/issues/2203
    private var xAxisOffsetMillis: Long? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = StatisticsPageFragmentBinding.inflate(inflater)

        setupTextViews()
        setupLineChart()
        setupNoDataSetButtonBinding()

        return binding.root
    }

    private fun setupTextViews() {
        binding.titleTextView.text = viewModel.title
        binding.explanationTextView.isGone = viewModel.explanation.isNullOrBlank()
        binding.explanationTextView.text = viewModel.explanation

        runIfDebugForTesting {
            binding.root.tag = viewModel.tabName
        }
    }

    @SuppressLint("ResourceType")
    private fun setupLineChart() {
        dataEntriesSet.label = viewModel.legend

        binding.lineChart.setNoDataTextColor(ContextCompat.getColor(requireContext(), R.color.primaryVariant))
        binding.lineChart.setNoDataText(viewModel.emptyStateText)

        binding.lineChart.data = LineData(styledDataSet(dataEntriesSet))
        binding.lineChart.invalidate()

        viewModel.tresholdEntries.forEach { addTresholdEntry(it) }
        viewModel.dataEntries.observe(viewLifecycleOwner, Observer { updateDataEntries(it) })

        binding.lineChart.setGridBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary).withAlphaDownPoppedToLevel(DownPopLevel.LEVEL5))
        binding.lineChart.setDrawGridBackground(true)

        binding.lineChart.description.isEnabled = false
        binding.lineChart.axisRight.isEnabled = false

        binding.lineChart.legend.textColor = ContextCompat.getColor(requireContext(), R.color.primary)
        binding.lineChart.legend.textSize = defaultTextSize
        binding.lineChart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER

        binding.lineChart.axisLeft.textColor = ContextCompat.getColor(requireContext(), R.color.onBackground).withAlphaDownPoppedToLevel(DownPopLevel.LEVEL2)
        binding.lineChart.axisLeft.setDrawGridLines(false)
        binding.lineChart.xAxis.textColor = ContextCompat.getColor(requireContext(), R.color.onBackground).withAlphaDownPoppedToLevel(DownPopLevel.LEVEL2)
        binding.lineChart.xAxis.setDrawGridLines(false)

        binding.lineChart.xAxis.granularity = 1.days.inMilliseconds.toFloat()
        binding.lineChart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                // NOTE: Workaround for this issue: https://github.com/PhilJay/MPAndroidChart/issues/2203 (the `+ xAxisOffsetMillis!!` part)
                return DateTimeFormat.shortDate().print(value.toLong() + xAxisOffsetMillis!!)
            }
        }

        binding.lineChart.setBorderColor(ContextCompat.getColor(requireContext(), R.color.onBackground))
    }

    private fun addTresholdEntry(tresholdEntry: StatisticsPageViewModel.TresholdEntry) {
        val limitLine = LimitLine(tresholdEntry.value.toFloat(), tresholdEntry.legend)

        limitLine.lineColor = tresholdEntry.color
        limitLine.textColor = ContextCompat.getColor(requireContext(), R.color.onBackground).withAlphaDownPoppedToLevel(DownPopLevel.LEVEL2)
        limitLine.textSize = defaultTextSize
        limitLine.lineWidth = 3.0f

        binding.lineChart.axisLeft.addLimitLine(limitLine)
        binding.lineChart.invalidate()
    }

    private fun updateDataEntries(dataEntries: List<StatisticsPageViewModel.DataEntry>) {
        // NOTE: Workaround for this issue: https://github.com/PhilJay/MPAndroidChart/issues/2203
        xAxisOffsetMillis = dataEntries.firstOrNull()?.measureDate?.millis

        dataEntriesSet.values = dataEntries.map { dataEntryToEntry(it) }

        if (dataEntries.isNotEmpty() && binding.lineChart.axisLeft.limitLines.isNotEmpty()) {
            binding.lineChart.axisLeft.axisMinimum =
                min(dataEntries.map { it.value.toFloat() }.min()!!, binding.lineChart.axisLeft.limitLines.map { it.limit }.min()!!)

            binding.lineChart.axisLeft.axisMaximum =
                max(dataEntries.map { it.value.toFloat() }.max()!!, binding.lineChart.axisLeft.limitLines.map { it.limit }.max()!!)

            // add 10 percent of extra space to top and bottom of left axis range
            binding.lineChart.axisLeft.axisMinimum -= binding.lineChart.axisLeft.mAxisRange / 10f
            binding.lineChart.axisLeft.axisMaximum += binding.lineChart.axisLeft.mAxisRange / 10f
        }

        if (dataEntriesSet.values.isNotEmpty()) {
            binding.lineChart.data = LineData(styledDataSet(dataEntriesSet))
        } else {
            binding.lineChart.clear()
        }

        binding.lineChart.invalidate()
    }

    private fun setupNoDataSetButtonBinding() {
        viewModel.editDataNavDirections?.let { editDataNavDirections ->
            viewModel.dataEntries.observe(viewLifecycleOwner, Observer { binding.editDataButton.isEnabled = it.isNotEmpty() })
            binding.editDataButton.setOnClickListener { findNavController().navigate(editDataNavDirections) }
        } ?: run {
            binding.editDataButton.isGone = true
        }
    }

    private fun styledDataSet(dataSet: LineDataSet): LineDataSet = dataSet.apply {
        setCircleColor(ContextCompat.getColor(requireContext(), R.color.primary))
        circleHoleColor = ContextCompat.getColor(requireContext(), R.color.background)
        circleRadius = 5f
        circleHoleRadius = 3f

        color = ContextCompat.getColor(requireContext(), R.color.primary)
        valueTextColor = ContextCompat.getColor(requireContext(), R.color.primary)
        highLightColor = ContextCompat.getColor(requireContext(), R.color.secondary)
        valueTextSize = defaultTextSize

        lineWidth = 2.0f
        setDrawValues(false)
    }

    private fun dataEntryToEntry(dataEntry: StatisticsPageViewModel.DataEntry): Entry {
        // NOTE: Workaround for this issue: https://github.com/PhilJay/MPAndroidChart/issues/2203 (the `- xAxisOffsetMillis!!` part)
        return Entry((dataEntry.measureDate.millis - xAxisOffsetMillis!!).toFloat(), dataEntry.value.toFloat())
    }
}
