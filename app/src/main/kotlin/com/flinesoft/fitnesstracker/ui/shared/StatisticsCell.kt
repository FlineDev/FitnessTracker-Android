package com.flinesoft.fitnesstracker.ui.shared

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.flinesoft.fitnesstracker.R
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.cell_statistics.view.*
import timber.log.Timber

class StatisticsCell(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    init {
        View.inflate(context, R.layout.cell_statistics, this)

        context.theme.obtainStyledAttributes(attrs, R.styleable.StatisticsCell, 0, 0).apply {
            try {
                titleTextView.text = getString(R.styleable.StatisticsCell_title)

                // TODO: use real data here
                val dataEntries = listOf(Entry(100f, 200f), Entry(2f, 3f), Entry(3f, 4f))
                val dataSet = LineDataSet(dataEntries, getString(R.styleable.StatisticsCell_legend)!!)
                lineChart.data = LineData(styledDataSet(dataSet))
                lineChart.invalidate()
            } finally {
                recycle()
            }
        }
    }

    fun setup(viewModel: StatisticsCellViewModel, lifecycleOwner: LifecycleOwner) {
        viewModel.tresholdEntries.forEach { addTresholdEntry(it) }
        viewModel.dataEntries.observe(lifecycleOwner, Observer { updateDataEntries(it) })
        // TODO: setup one-off data and LiveData observers
    }

    private fun setupLineChart() {
//        lineChart.xAxis.valueFormatter = object : ValueFormatter() {
//            override fun getAxisLabel(value: Float, axis: AxisBase): String {
//                return // TODO: write a value formatter that can both convert DateTime to float and the other way around
//            }
//        }
    }

    private fun addTresholdEntry(tresholdEntry: StatisticsCellViewModel.TresholdEntry) {
        Timber.d("adding new treshold entry: $tresholdEntry") // TODO: not yet implemented
    }

    fun updateDataEntries(dataEntries: List<StatisticsCellViewModel.DataEntry>) {
        Timber.d("received new data entries: $dataEntries") // TODO: not yet implemented
    }

    private fun styledDataSet(dataSet: LineDataSet): LineDataSet = dataSet.apply {
        color = R.color.color_on_surface
        valueTextColor = R.color.color_on_surface
    }
}
