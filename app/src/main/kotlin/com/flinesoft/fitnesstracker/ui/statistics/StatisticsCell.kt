package com.flinesoft.fitnesstracker.ui.statistics

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.flinesoft.fitnesstracker.R
import com.flinesoft.fitnesstracker.globals.DownPopLevel
import com.flinesoft.fitnesstracker.globals.extensions.withAlphaDownPoppedToLevel
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlinx.android.synthetic.main.statistics_cell.view.*
import org.joda.time.format.DateTimeFormat
import kotlin.math.max
import kotlin.math.min
import kotlin.time.ExperimentalTime
import kotlin.time.days

@ExperimentalTime
class StatisticsCell(context: Context, attributes: AttributeSet) : ConstraintLayout(context, attributes) {
    private val dataEntriesSet: LineDataSet = LineDataSet(emptyList(), "TODO")

    private val defaultTextSize: Float = 13.0f

    // NOTE: Workaround for this issue: https://github.com/PhilJay/MPAndroidChart/issues/2203
    private var xAxisOffsetMillis: Long? = null

    init {
        View.inflate(context, R.layout.statistics_cell, this)

        setupLineChart()

        context.theme.obtainStyledAttributes(attributes, R.styleable.StatisticsCell, 0, 0).apply {
            try {
                titleTextView.text = getString(R.styleable.StatisticsCell_title)
            } finally {
                recycle()
            }
        }
    }

    fun setup(viewModel: StatisticsCellViewModel, lifecycleOwner: LifecycleOwner) {
        dataEntriesSet.label = viewModel.legend
        explanationTextView.text = viewModel.explanation

        lineChart.setNoDataTextColor(ContextCompat.getColor(context, R.color.primaryVariant))
        lineChart.setNoDataText(viewModel.emptyStateText)

        lineChart.data = LineData(styledDataSet(dataEntriesSet))
        lineChart.invalidate()

        viewModel.tresholdEntries.forEach { addTresholdEntry(it) }
        viewModel.dataEntries.observe(lifecycleOwner, Observer { updateDataEntries(it) })
    }

    @SuppressLint("ResourceType")
    private fun setupLineChart() {
        lineChart.setGridBackgroundColor(ContextCompat.getColor(context, R.color.primary).withAlphaDownPoppedToLevel(DownPopLevel.LEVEL5))
        lineChart.setDrawGridBackground(true)

        lineChart.description.isEnabled = false
        lineChart.axisRight.isEnabled = false

        lineChart.legend.textColor = ContextCompat.getColor(context, R.color.primary)
        lineChart.legend.textSize = defaultTextSize
        lineChart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER

        lineChart.axisLeft.textColor = ContextCompat.getColor(context, R.color.onBackground).withAlphaDownPoppedToLevel(DownPopLevel.LEVEL2)
        lineChart.axisLeft.setDrawGridLines(false)
        lineChart.xAxis.textColor = ContextCompat.getColor(context, R.color.onBackground).withAlphaDownPoppedToLevel(DownPopLevel.LEVEL2)
        lineChart.xAxis.setDrawGridLines(false)

        lineChart.xAxis.granularity = 1.days.inMilliseconds.toFloat()
        lineChart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                // NOTE: Workaround for this issue: https://github.com/PhilJay/MPAndroidChart/issues/2203 (the `+ xAxisOffsetMillis!!` part)
                return DateTimeFormat.shortDate().print(value.toLong() + xAxisOffsetMillis!!)
            }
        }

        lineChart.setBorderColor(ContextCompat.getColor(context, R.color.onBackground))
    }

    private fun addTresholdEntry(tresholdEntry: StatisticsCellViewModel.TresholdEntry) {
        val limitLine = LimitLine(tresholdEntry.value.toFloat(), tresholdEntry.legend)

        limitLine.lineColor = tresholdEntry.color
        limitLine.textColor = ContextCompat.getColor(context, R.color.onBackground).withAlphaDownPoppedToLevel(DownPopLevel.LEVEL2)
        limitLine.textSize = defaultTextSize
        limitLine.lineWidth = 3.0f

        lineChart.axisLeft.addLimitLine(limitLine)
        lineChart.invalidate()
    }

    private fun updateDataEntries(dataEntries: List<StatisticsCellViewModel.DataEntry>) {
        // NOTE: Workaround for this issue: https://github.com/PhilJay/MPAndroidChart/issues/2203
        xAxisOffsetMillis = dataEntries.firstOrNull()?.dateTime?.millis

        dataEntriesSet.values = dataEntries.map { dataEntryToEntry(it) }

        if (dataEntries.isNotEmpty() && lineChart.axisLeft.limitLines.isNotEmpty()) {
            lineChart.axisLeft.axisMinimum = min(dataEntries.map { it.value.toFloat() }.min()!!, lineChart.axisLeft.limitLines.map { it.limit }.min()!!)
            lineChart.axisLeft.axisMaximum = max(dataEntries.map { it.value.toFloat() }.max()!!, lineChart.axisLeft.limitLines.map { it.limit }.max()!!)

            // add 10 percent of extra space to top and bottom of left axis range
            lineChart.axisLeft.axisMinimum -= lineChart.axisLeft.mAxisRange / 10f
            lineChart.axisLeft.axisMaximum += lineChart.axisLeft.mAxisRange / 10f
        }

        if (dataEntriesSet.values.isNotEmpty()) {
            lineChart.data = LineData(styledDataSet(dataEntriesSet))
        } else {
            lineChart.clear()
        }

        lineChart.invalidate()
    }

    private fun styledDataSet(dataSet: LineDataSet): LineDataSet = dataSet.apply {
        setCircleColor(ContextCompat.getColor(context, R.color.primary))
        circleHoleColor = ContextCompat.getColor(context, R.color.background)
        circleRadius = 5f
        circleHoleRadius = 3f

        color = ContextCompat.getColor(context, R.color.primary)
        valueTextColor = ContextCompat.getColor(context, R.color.primary)
        highLightColor = ContextCompat.getColor(context, R.color.secondary)
        valueTextSize = defaultTextSize

        lineWidth = 2.0f
    }

    private fun dataEntryToEntry(dataEntry: StatisticsCellViewModel.DataEntry): Entry {
        // NOTE: Workaround for this issue: https://github.com/PhilJay/MPAndroidChart/issues/2203 (the `- xAxisOffsetMillis!!` part)
        return Entry((dataEntry.dateTime.millis - xAxisOffsetMillis!!).toFloat(), dataEntry.value.toFloat())
    }
}
