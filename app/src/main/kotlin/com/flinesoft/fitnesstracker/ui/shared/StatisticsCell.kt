package com.flinesoft.fitnesstracker.ui.shared

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.flinesoft.fitnesstracker.R
import kotlinx.android.synthetic.main.cell_statistics.view.*

class StatisticsCell(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    init {
        View.inflate(context, R.layout.cell_statistics, this)

        context.theme.obtainStyledAttributes(attrs, R.styleable.StatisticsCell, 0, 0).apply {
            try {
                titleTextView.text = getString(R.styleable.StatisticsCell_title)
            } finally {
                recycle()
            }
        }
    }
}
