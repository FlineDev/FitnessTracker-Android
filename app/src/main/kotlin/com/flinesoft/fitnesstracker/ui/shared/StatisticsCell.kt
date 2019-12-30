package com.flinesoft.fitnesstracker.ui.shared

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.flinesoft.fitnesstracker.R
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlinx.android.synthetic.main.cell_statistics.view.*
import org.joda.time.format.DateTimeFormat
import kotlin.math.max
import kotlin.math.min
import kotlin.time.ExperimentalTime
import kotlin.time.days

@ExperimentalTime
class StatisticsCell(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    @SuppressLint("ResourceType")
    // TODO: use the localized string
    private val dataEntriesSet = LineDataSet(emptyList(), /*context.getString(R.styleable.StatisticsCell_legend)*/ "TODO")

    // NOTE: Workaround for this issue: https://github.com/PhilJay/MPAndroidChart/issues/2203
    private var xAxisOffsetMillis: Long? = null

    init {
        View.inflate(context, R.layout.cell_statistics, this)
        setupLineChart()

        context.theme.obtainStyledAttributes(attrs, R.styleable.StatisticsCell, 0, 0).apply {
            try {
                titleTextView.text = getString(R.styleable.StatisticsCell_title)
            } finally {
                recycle()
            }
        }
    }

    fun setup(viewModel: StatisticsCellViewModel, lifecycleOwner: LifecycleOwner) {
        lineChart.data = LineData(styledDataSet(dataEntriesSet))
        lineChart.invalidate()

        viewModel.tresholdEntries.forEach { addTresholdEntry(it) }
        viewModel.dataEntries.observe(lifecycleOwner, Observer { updateDataEntries(it) })
    }

    @SuppressLint("ResourceType")
    private fun setupLineChart() {
        lineChart.description.isEnabled = false
        lineChart.axisRight.isEnabled = false

        lineChart.xAxis.granularity = 1.days.inMilliseconds.toFloat()
        lineChart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                // NOTE: Workaround for this issue: https://github.com/PhilJay/MPAndroidChart/issues/2203 (the `+ xAxisOffsetMillis!!` part)
                return DateTimeFormat.shortDate().print(value.toLong() + xAxisOffsetMillis!!)
            }
        }

        lineChart.setGridBackgroundColor(ContextCompat.getColor(context, R.color.color_background))
        lineChart.setBorderColor(ContextCompat.getColor(context, R.color.color_on_background))
    }

    private fun addTresholdEntry(tresholdEntry: StatisticsCellViewModel.TresholdEntry) {
        val limitLine = LimitLine(tresholdEntry.value.toFloat(), tresholdEntry.legend)
        limitLine.lineColor = tresholdEntry.color
        lineChart.axisLeft.addLimitLine(limitLine)
        lineChart.invalidate()
    }

    private fun updateDataEntries(dataEntries: List<StatisticsCellViewModel.DataEntry>) {
        // NOTE: Workaround for this issue: https://github.com/PhilJay/MPAndroidChart/issues/2203
        xAxisOffsetMillis = dataEntries.firstOrNull()?.dateTime?.millis

        dataEntriesSet.values = dataEntries.map { dataEntryToEntry(it) }
        lineChart.data = LineData(styledDataSet(dataEntriesSet))

        if (dataEntries.isNotEmpty() && lineChart.axisLeft.limitLines.isNotEmpty()) {
            lineChart.axisLeft.axisMinimum = min(dataEntries.map { it.value.toFloat() }.min()!!, lineChart.axisLeft.limitLines.map { it.limit }.min()!!)
            lineChart.axisLeft.axisMaximum = max(dataEntries.map { it.value.toFloat() }.max()!!, lineChart.axisLeft.limitLines.map { it.limit }.max()!!)

            // add 10 percent of extra space to top and bottom of left axis range
            lineChart.axisLeft.axisMinimum -= lineChart.axisLeft.mAxisRange / 10f
            lineChart.axisLeft.axisMaximum += lineChart.axisLeft.mAxisRange / 10f
        }

        lineChart.invalidate()
    }

    private fun styledDataSet(dataSet: LineDataSet): LineDataSet = dataSet.apply {
        color = ContextCompat.getColor(context, R.color.color_primary)
        valueTextColor = ContextCompat.getColor(context, R.color.color_secondary)
    }

    private fun dataEntryToEntry(dataEntry: StatisticsCellViewModel.DataEntry): Entry {
        // NOTE: Workaround for this issue: https://github.com/PhilJay/MPAndroidChart/issues/2203 (the `- xAxisOffsetMillis!!` part)
        return Entry((dataEntry.dateTime.millis - xAxisOffsetMillis!!).toFloat(), dataEntry.value.toFloat())
    }
}
