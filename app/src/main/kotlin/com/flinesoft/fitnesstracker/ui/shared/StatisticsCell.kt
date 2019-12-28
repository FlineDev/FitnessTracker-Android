package com.flinesoft.fitnesstracker.ui.shared

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
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
import java.lang.Float.max
import java.lang.Float.min
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
        lineChart.axisLeft.axisMinimum = viewModel.alwaysShowValueRange.start.toFloat()
        lineChart.axisLeft.axisMaximum = viewModel.alwaysShowValueRange.endInclusive.toFloat()
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

        lineChart.axisLeft.axisMinimum = min(dataEntries.map { it.value.toFloat() }.min() ?: lineChart.axisLeft.axisMinimum, lineChart.axisLeft.axisMinimum)
        lineChart.axisLeft.axisMaximum = max(dataEntries.map { it.value.toFloat() }.max() ?: lineChart.axisLeft.axisMaximum, lineChart.axisLeft.axisMaximum)

        lineChart.invalidate()
    }

    private fun styledDataSet(dataSet: LineDataSet): LineDataSet = dataSet.apply {
        color = R.color.color_on_surface
        valueTextColor = R.color.color_on_surface
    }

    private fun dataEntryToEntry(dataEntry: StatisticsCellViewModel.DataEntry): Entry {
        // NOTE: Workaround for this issue: https://github.com/PhilJay/MPAndroidChart/issues/2203 (the `- xAxisOffsetMillis!!` part)
        return Entry((dataEntry.dateTime.millis - xAxisOffsetMillis!!).toFloat(), dataEntry.value.toFloat())
    }
}
