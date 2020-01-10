package com.flinesoft.fitnesstracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.flinesoft.fitnesstracker.globals.AppPreferences
import com.flinesoft.fitnesstracker.globals.Logger
import com.flinesoft.fitnesstracker.globals.NotificationHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.time.ExperimentalTime
import kotlin.time.hours

@ExperimentalTime
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Logger.setup()
        AppPreferences.setup(applicationContext)
        NotificationHelper.setup(applicationContext)

        // TODO: show onboarding asking for height, gender and birth year first – app will crash otherwise

        // TODO: make configurable through user interface
        AppPreferences.onDayReminderDelay = 6.hours

        setContentView(R.layout.main_activity)
        val navView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        val navController = findNavController(R.id.navigationHostFragment)
        // Passing each menu ID as a set of Ids because each menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.workouts, R.id.statistics)
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}
