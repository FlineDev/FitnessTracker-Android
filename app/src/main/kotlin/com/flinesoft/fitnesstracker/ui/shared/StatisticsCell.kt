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
import kotlin.time.ExperimentalTime
import kotlin.time.days

@ExperimentalTime
class StatisticsCell(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    @SuppressLint("ResourceType")
    // TODO: use the localized string
    private val dataEntriesSet = LineDataSet(emptyList(), /*context.getString(R.styleable.StatisticsCell_legend)*/ "TODO")

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
        lineChart.xAxis.granularity = 1.days.inMilliseconds.toFloat()
        lineChart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return DateTimeFormat.shortDateTime().print(value.toLong())
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
        dataEntriesSet.values = dataEntries.map { dataEntryToEntry(it) }
        lineChart.invalidate()
    }

    private fun styledDataSet(dataSet: LineDataSet): LineDataSet = dataSet.apply {
        color = R.color.color_on_surface
        valueTextColor = R.color.color_on_surface
    }

    private fun dataEntryToEntry(dataEntry: StatisticsCellViewModel.DataEntry): Entry {
        return Entry(dataEntry.dateTime.millis.toFloat(), dataEntry.value.toFloat())
    }
}
