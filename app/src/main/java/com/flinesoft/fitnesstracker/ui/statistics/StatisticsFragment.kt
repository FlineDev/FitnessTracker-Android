package com.flinesoft.fitnesstracker.ui.statistics

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.appcompat.view.menu.MenuBuilder
import com.flinesoft.fitnesstracker.R



class StatisticsFragment : Fragment() {
    private lateinit var statisticsViewModel: StatisticsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        statisticsViewModel = ViewModelProviders.of(this).get(StatisticsViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_statistics, container, false)
        val textView: TextView = root.findViewById(R.id.text_statistics)
        statisticsViewModel.text.observe(this, Observer { textView.text = it })

        setHasOptionsMenu(true)

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.statistics_overflow_menu, menu)

        if (menu is MenuBuilder) {
            menu.setOptionalIconsVisible(true)
        }
    }
}