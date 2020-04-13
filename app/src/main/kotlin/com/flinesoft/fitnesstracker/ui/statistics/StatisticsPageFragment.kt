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
import com.flinesoft.fitnesstracker.calculation.MovingAverageCalculator
import com.flinesoft.fitnesstracker.databinding.StatisticsPageFragmentBinding
import com.flinesoft.fitnesstracker.globals.DEFAULT_MAX_STATISTIC_ENTRIES
import com.flinesoft.fitnesstracker.globals.DownPopLevel
import com.flinesoft.fitnesstracker.globals.MOVING_AVERAGE_DATE_ENTRY_STEP_DURATION
import com.flinesoft.fitnesstracker.globals.MOVING_AVERAGE_MAX_WEIGHT_FACTOR
import com.flinesoft.fitnesstracker.globals.MOVING_AVERAGE_MIN_DATA_ENTRIES
import com.flinesoft.fitnesstracker.globals.MOVING_AVERAGE_TIME_INTERVAL_TO_CONSIDER
import com.flinesoft.fitnesstracker.globals.extensions.durationSince
import com.flinesoft.fitnesstracker.globals.extensions.minusKt
import com.flinesoft.fitnesstracker.globals.extensions.withAlphaDownPoppedToLevel
import com.flinesoft.fitnesstracker.globals.runIfDebugForTesting
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import kotlin.math.max
import kotlin.math.min
import kotlin.time.ExperimentalTime
import kotlin.time.days

@ExperimentalTime
class StatisticsPageFragment(val viewModel: StatisticsPageViewModel) : Fragment() {
    private lateinit var binding: StatisticsPageFragmentBinding

    private val measurementsDataEntriesSet: LineDataSet = LineDataSet(emptyList(), "TODO")
    private val movingAveragesDataEntriesSet: LineDataSet = LineDataSet(emptyList(), "TODO")

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
        measurementsDataEntriesSet.label = viewModel.measurementsLegend
        movingAveragesDataEntriesSet.label = viewModel.movingAveragesLegend

        binding.lineChart.setNoDataTextColor(ContextCompat.getColor(requireContext(), R.color.primaryVariant))
        binding.lineChart.setNoDataText(viewModel.emptyStateText)

        binding.lineChart.data = LineData(styledMeasurementsDataSet(measurementsDataEntriesSet), styledMovingAveragesDataSet(movingAveragesDataEntriesSet))
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
        binding.lineChart.xAxis.setDrawLimitLinesBehindData(true)

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

    // TODO: [cg_2020-04-13] simplify this method, moving to the latest entries automatically is too complicated
    private fun updateDataEntries(dataEntries: List<StatisticsPageViewModel.DataEntry>) {
        // NOTE: Workaround for this issue: https://github.com/PhilJay/MPAndroidChart/issues/2203
        xAxisOffsetMillis = dataEntries.firstOrNull()?.measureDate?.millis

        measurementsDataEntriesSet.values = dataEntries.map { dataEntryToEntry(it) }
        movingAveragesDataEntriesSet.values = buildMovingAveragesDataEntries(dataEntries).map { dataEntryToEntry(it) }

        if (dataEntries.isNotEmpty()) {
            val limitLineYValues = binding.lineChart.axisLeft.limitLines.map { it.limit }
            val dataEntryValues = dataEntries.map { it.value.toFloat() }

            binding.lineChart.axisLeft.axisMinimum = dataEntryValues.min()!!
            binding.lineChart.axisLeft.axisMaximum = dataEntryValues.max()!!

            // NOTE: fixes an issue where big numbers with decimals don't fit into the axis space
            if (binding.lineChart.axisLeft.mAxisRange > 10) {
                binding.lineChart.axisLeft.granularity = 1f
            }

            if (limitLineYValues.isNotEmpty()) {
                binding.lineChart.axisLeft.axisMinimum = min(binding.lineChart.axisLeft.axisMinimum, limitLineYValues.min()!!)
                binding.lineChart.axisLeft.axisMaximum = max(binding.lineChart.axisLeft.axisMaximum, limitLineYValues.max()!!)
            }

            // add 10 percent of extra space to top and bottom of left axis range
            binding.lineChart.axisLeft.axisMinimum -= binding.lineChart.axisLeft.mAxisRange / 10f
            binding.lineChart.axisLeft.axisMaximum += binding.lineChart.axisLeft.mAxisRange / 10f
        }

        if (measurementsDataEntriesSet.values.isNotEmpty()) {
            binding.lineChart.data = LineData(styledMeasurementsDataSet(measurementsDataEntriesSet), styledMovingAveragesDataSet(movingAveragesDataEntriesSet))
        } else {
            binding.lineChart.clear()
        }

        if (dataEntries.size > DEFAULT_MAX_STATISTIC_ENTRIES) {
            val visibleDataEntries = dataEntries.takeLast(DEFAULT_MAX_STATISTIC_ENTRIES)
            val limitLineYValues = binding.lineChart.axisLeft.limitLines.map { it.limit }

            var minVisibleYValue = visibleDataEntries.map { it.value.toFloat() }.min()!!
            var maxVisibleYValue = visibleDataEntries.map { it.value.toFloat() }.max()!!

            if (limitLineYValues.isNotEmpty()) {
                minVisibleYValue = min(minVisibleYValue, limitLineYValues.min()!!)
                maxVisibleYValue = max(maxVisibleYValue, limitLineYValues.max()!!)
            }

            val minVisibleXValue = dataEntryToEntry(visibleDataEntries.first()!!).x
            val maxVisibleXValue = dataEntryToEntry(visibleDataEntries.last()!!).x

            binding.lineChart.zoom(
                binding.lineChart.xRange / (maxVisibleXValue - minVisibleXValue) * 0.9f,
                binding.lineChart.axisLeft.mAxisRange / (maxVisibleYValue - minVisibleYValue) * 0.9f,
                0f,
                0f
            )

            binding.lineChart.moveViewTo(binding.lineChart.xChartMax, (minVisibleYValue + maxVisibleYValue) / 2, YAxis.AxisDependency.LEFT)
        }

        binding.lineChart.invalidate()
    }

    private fun buildMovingAveragesDataEntries(dataEntries: List<StatisticsPageViewModel.DataEntry>): List<StatisticsPageViewModel.DataEntry> {
        dataEntries.firstOrNull()?.let { firstDataEntry ->
            val movingAverageDataEntries = dataEntries.map { MovingAverageCalculator.DataEntry(measureDate = it.measureDate, value = it.value) }
            val durationSinceFirstDataEntry = DateTime.now().durationSince(firstDataEntry.measureDate)
            val dataEntriesCount = (durationSinceFirstDataEntry / MOVING_AVERAGE_DATE_ENTRY_STEP_DURATION).toInt()
            val entries: MutableList<StatisticsPageViewModel.DataEntry> = mutableListOf()

            for (step in 0..dataEntriesCount) {
                val date = DateTime.now().minusKt(MOVING_AVERAGE_DATE_ENTRY_STEP_DURATION.times(dataEntriesCount - step))
                val movingAverage = MovingAverageCalculator.calculateMovingAverageAt(
                    date = date,
                    timeIntervalToConsider = MOVING_AVERAGE_TIME_INTERVAL_TO_CONSIDER,
                    dataEntries = movingAverageDataEntries,
                    minDataEntriesCount = MOVING_AVERAGE_MIN_DATA_ENTRIES,
                    maxWeightFactor = MOVING_AVERAGE_MAX_WEIGHT_FACTOR
                )
                movingAverage?.let { entries.add(StatisticsPageViewModel.DataEntry(measureDate = date, value = it)) }
            }

            return entries
        } ?: return emptyList()
    }

    private fun setupNoDataSetButtonBinding() {
        viewModel.editDataNavDirections?.let { editDataNavDirections ->
            viewModel.dataEntries.observe(viewLifecycleOwner, Observer { binding.editDataButton.isEnabled = it.isNotEmpty() })
            binding.editDataButton.setOnClickListener { findNavController().navigate(editDataNavDirections) }
        } ?: run {
            binding.editDataButton.isGone = true
        }
    }

    private fun styledMeasurementsDataSet(dataSet: LineDataSet): LineDataSet = dataSet.apply {
        setCircleColor(ContextCompat.getColor(requireContext(), R.color.primary))
        circleHoleColor = ContextCompat.getColor(requireContext(), R.color.background)
        circleRadius = 5f
        circleHoleRadius = 3f

        color = ContextCompat.getColor(requireContext(), R.color.primary)
        valueTextColor = ContextCompat.getColor(requireContext(), R.color.primary)
        highLightColor = ContextCompat.getColor(requireContext(), R.color.secondary)
        valueTextSize = defaultTextSize

        lineWidth = 1.5f
        mode = LineDataSet.Mode.LINEAR
        setDrawValues(false)
    }

    private fun styledMovingAveragesDataSet(dataSet: LineDataSet): LineDataSet = dataSet.apply {
        color = ContextCompat.getColor(requireContext(), R.color.secondary)
        valueTextColor = ContextCompat.getColor(requireContext(), R.color.secondary)
        highLightColor = ContextCompat.getColor(requireContext(), R.color.primary)
        valueTextSize = defaultTextSize

        lineWidth = 3.0f
        mode = LineDataSet.Mode.CUBIC_BEZIER
        setDrawCircles(false)
        setDrawValues(false)
    }

    private fun dataEntryToEntry(dataEntry: StatisticsPageViewModel.DataEntry): Entry {
        // NOTE: Workaround for this issue: https://github.com/PhilJay/MPAndroidChart/issues/2203 (the `- xAxisOffsetMillis!!` part)
        return Entry((dataEntry.measureDate.millis - xAxisOffsetMillis!!).toFloat(), dataEntry.value.toFloat())
    }
}
