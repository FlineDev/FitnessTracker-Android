package com.flinesoft.fitnesstracker.helpers

import android.content.Context
import android.os.Build
import androidx.test.platform.app.InstrumentationRegistry
import com.flinesoft.fitnesstracker.R
import com.flinesoft.fitnesstracker.globals.AppPreferences
import com.flinesoft.fitnesstracker.model.Gender
import com.flinesoft.fitnesstracker.model.Impediment
import com.flinesoft.fitnesstracker.model.WaistCircumferenceMeasurement
import com.flinesoft.fitnesstracker.model.WeightMeasurement
import com.flinesoft.fitnesstracker.model.Workout
import com.flinesoft.fitnesstracker.persistence.FitnessTrackerDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import kotlin.system.exitProcess
import kotlin.time.ExperimentalTime

@ExperimentalTime
object TestContext {
    private lateinit var database: FitnessTrackerDatabase
    private lateinit var appContext: Context

    fun resetAll() {
        requireEmulator()
        appContext = InstrumentationRegistry.getInstrumentation().targetContext

        AppPreferences.setup(appContext)
        AppPreferences.clear()

        database = FitnessTrackerDatabase.getInstance(appContext)
        database.clearAllTables()
    }

    fun skipOnboarding() {
        requireEmulator()
        AppPreferences.onboardingCompleted = true
    }

    fun skipInitialPersonalDataModal() {
        requireEmulator()
        AppPreferences.heightInCentimeters = 170
        AppPreferences.birthYear = 1985
        AppPreferences.gender = Gender.FEMALE
    }

    fun withSampleData() {
        requireEmulator()
        skipOnboarding()
        skipInitialPersonalDataModal()

        AppPreferences.heightInCentimeters = 178
        AppPreferences.birthYear = 1985
        AppPreferences.gender = Gender.MALE

        GlobalScope.launch {
            sampleWorkouts().forEach { database.workoutDao.create(it) }
            sampleImpediments().forEach { database.impedimentDao.create(it) }
            sampleWeightMeasurements().forEach { database.weightMeasurementDao.create(it) }
            sampleWaistCircumferenceMeasurements().forEach { database.waistCircumferenceMeasurementDao.create(it) }
        }
    }

    private fun sampleWorkouts(): List<Workout> = listOf(
        Workout(
            type = Workout.Type.CARDIO,
            startDate = DateTime.now().minusDays(30).withTime(16, 20, 0, 0),
            endDate = DateTime.now().minusDays(30).withTime(16, 20, 0, 0)
        ),
        Workout(
            type = Workout.Type.MUSCLE_BUILDING,
            startDate = DateTime.now().minusDays(28).withTime(7, 15, 0, 0),
            endDate = DateTime.now().minusDays(28).withTime(8, 50, 0, 0)
        ),
        Workout(
            type = Workout.Type.MUSCLE_BUILDING,
            startDate = DateTime.now().minusDays(15).withTime(7, 10, 0, 0),
            endDate = DateTime.now().minusDays(15).withTime(8, 45, 0, 0)
        ),
        Workout(
            type = Workout.Type.CARDIO,
            startDate = DateTime.now().minusDays(4).withTime(16, 20, 0, 0),
            endDate = DateTime.now().minusDays(4).withTime(16, 20, 0, 0)
        ),
        Workout(
            type = Workout.Type.MUSCLE_BUILDING,
            startDate = DateTime.now().minusDays(1).withTime(8, 20, 0, 0),
            endDate = DateTime.now().minusDays(1).withTime(9, 30, 0, 0)
        )
    )

    private fun sampleImpediments(): List<Impediment> = listOf(
        Impediment(
            name = appContext.getString(R.string.tests_sample_data_cold),
            startDate = DateTime.now().minusDays(25).withTimeAtStartOfDay(),
            endDate = DateTime.now().minusDays(18).withTimeAtStartOfDay()
        ),
        Impediment(
            name = appContext.getString(R.string.tests_sample_data_stiffness),
            startDate = DateTime.now().minusDays(14).withTimeAtStartOfDay(),
            endDate = DateTime.now().minusDays(12).withTimeAtStartOfDay()
        )
    )

    private fun sampleWeightMeasurements(): List<WeightMeasurement> = listOf(
        WeightMeasurement(80.5, DateTime.now().minusDays(30).withTime(6, 30, 0, 0)),
        WeightMeasurement(80.2, DateTime.now().minusDays(29).withTime(6, 30, 0, 0)),
        WeightMeasurement(81.2, DateTime.now().minusDays(28).withTime(6, 30, 0, 0)),
        WeightMeasurement(81.4, DateTime.now().minusDays(27).withTime(6, 30, 0, 0)),
        WeightMeasurement(81.6, DateTime.now().minusDays(26).withTime(6, 30, 0, 0)),
        WeightMeasurement(80.8, DateTime.now().minusDays(25).withTime(6, 30, 0, 0)),
        WeightMeasurement(80.6, DateTime.now().minusDays(24).withTime(6, 30, 0, 0)),
        WeightMeasurement(80.0, DateTime.now().minusDays(23).withTime(6, 30, 0, 0)),
        WeightMeasurement(79.4, DateTime.now().minusDays(22).withTime(6, 30, 0, 0)),
        WeightMeasurement(79.9, DateTime.now().minusDays(21).withTime(6, 30, 0, 0)),
        WeightMeasurement(80.5, DateTime.now().minusDays(20).withTime(6, 30, 0, 0)),
        WeightMeasurement(79.8, DateTime.now().minusDays(19).withTime(6, 30, 0, 0)),
        WeightMeasurement(79.3, DateTime.now().minusDays(18).withTime(6, 30, 0, 0)),
        WeightMeasurement(79.1, DateTime.now().minusDays(17).withTime(6, 30, 0, 0)),
        WeightMeasurement(78.6, DateTime.now().minusDays(16).withTime(6, 30, 0, 0)),
        WeightMeasurement(79.5, DateTime.now().minusDays(15).withTime(6, 30, 0, 0)),
        WeightMeasurement(79.8, DateTime.now().minusDays(14).withTime(6, 30, 0, 0)),
        WeightMeasurement(80.1, DateTime.now().minusDays(13).withTime(6, 30, 0, 0)),
        WeightMeasurement(79.5, DateTime.now().minusDays(12).withTime(6, 30, 0, 0)),
        WeightMeasurement(79.4, DateTime.now().minusDays(11).withTime(6, 30, 0, 0)),
        WeightMeasurement(79.4, DateTime.now().minusDays(10).withTime(6, 30, 0, 0)),
        WeightMeasurement(79.1, DateTime.now().minusDays(9).withTime(6, 30, 0, 0)),
        WeightMeasurement(78.8, DateTime.now().minusDays(8).withTime(6, 30, 0, 0)),
        WeightMeasurement(78.4, DateTime.now().minusDays(7).withTime(6, 30, 0, 0)),
        WeightMeasurement(78.6, DateTime.now().minusDays(6).withTime(6, 30, 0, 0)),
        WeightMeasurement(78.2, DateTime.now().minusDays(5).withTime(6, 30, 0, 0)),
        WeightMeasurement(78.0, DateTime.now().minusDays(4).withTime(6, 30, 0, 0)),
        WeightMeasurement(78.2, DateTime.now().minusDays(3).withTime(6, 30, 0, 0)),
        WeightMeasurement(77.9, DateTime.now().minusDays(2).withTime(6, 30, 0, 0)),
        WeightMeasurement(77.6, DateTime.now().minusDays(1).withTime(6, 30, 0, 0)),
        WeightMeasurement(77.8, DateTime.now().minusDays(0).withTime(6, 30, 0, 0))
    )

    private fun sampleWaistCircumferenceMeasurements(): List<WaistCircumferenceMeasurement> = listOf(
        WaistCircumferenceMeasurement(98.0, DateTime.now().minusDays(30).withTime(6, 30, 0, 0)),
        WaistCircumferenceMeasurement(98.5, DateTime.now().minusDays(29).withTime(6, 30, 0, 0)),
        WaistCircumferenceMeasurement(99.0, DateTime.now().minusDays(28).withTime(6, 30, 0, 0)),
        WaistCircumferenceMeasurement(99.0, DateTime.now().minusDays(27).withTime(6, 30, 0, 0)),
        WaistCircumferenceMeasurement(99.5, DateTime.now().minusDays(26).withTime(6, 30, 0, 0)),
        WaistCircumferenceMeasurement(98.0, DateTime.now().minusDays(25).withTime(6, 30, 0, 0)),
        WaistCircumferenceMeasurement(97.0, DateTime.now().minusDays(24).withTime(6, 30, 0, 0)),
        WaistCircumferenceMeasurement(98.0, DateTime.now().minusDays(23).withTime(6, 30, 0, 0)),
        WaistCircumferenceMeasurement(97.5, DateTime.now().minusDays(22).withTime(6, 30, 0, 0)),
        WaistCircumferenceMeasurement(96.0, DateTime.now().minusDays(21).withTime(6, 30, 0, 0)),
        WaistCircumferenceMeasurement(94.0, DateTime.now().minusDays(20).withTime(6, 30, 0, 0)),
        WaistCircumferenceMeasurement(95.5, DateTime.now().minusDays(19).withTime(6, 30, 0, 0)),
        WaistCircumferenceMeasurement(96.0, DateTime.now().minusDays(18).withTime(6, 30, 0, 0)),
        WaistCircumferenceMeasurement(94.0, DateTime.now().minusDays(17).withTime(6, 30, 0, 0)),
        WaistCircumferenceMeasurement(95.5, DateTime.now().minusDays(16).withTime(6, 30, 0, 0)),
        WaistCircumferenceMeasurement(94.5, DateTime.now().minusDays(15).withTime(6, 30, 0, 0)),
        WaistCircumferenceMeasurement(93.5, DateTime.now().minusDays(14).withTime(6, 30, 0, 0)),
        WaistCircumferenceMeasurement(93.0, DateTime.now().minusDays(13).withTime(6, 30, 0, 0)),
        WaistCircumferenceMeasurement(93.0, DateTime.now().minusDays(12).withTime(6, 30, 0, 0)),
        WaistCircumferenceMeasurement(94.5, DateTime.now().minusDays(11).withTime(6, 30, 0, 0)),
        WaistCircumferenceMeasurement(92.0, DateTime.now().minusDays(10).withTime(6, 30, 0, 0)),
        WaistCircumferenceMeasurement(91.5, DateTime.now().minusDays(9).withTime(6, 30, 0, 0)),
        WaistCircumferenceMeasurement(92.0, DateTime.now().minusDays(8).withTime(6, 30, 0, 0)),
        WaistCircumferenceMeasurement(93.0, DateTime.now().minusDays(7).withTime(6, 30, 0, 0)),
        WaistCircumferenceMeasurement(91.5, DateTime.now().minusDays(6).withTime(6, 30, 0, 0)),
        WaistCircumferenceMeasurement(90.0, DateTime.now().minusDays(5).withTime(6, 30, 0, 0)),
        WaistCircumferenceMeasurement(90.5, DateTime.now().minusDays(4).withTime(6, 30, 0, 0)),
        WaistCircumferenceMeasurement(90.0, DateTime.now().minusDays(3).withTime(6, 30, 0, 0)),
        WaistCircumferenceMeasurement(91.0, DateTime.now().minusDays(2).withTime(6, 30, 0, 0)),
        WaistCircumferenceMeasurement(92.0, DateTime.now().minusDays(1).withTime(6, 30, 0, 0)),
        WaistCircumferenceMeasurement(90.5, DateTime.now().minusDays(0).withTime(6, 30, 0, 0))
    )

    private fun requireEmulator() {
        if (!runningOnEmulator()) {
            throw RuntimeException("Running UI tests on a real device will reset all data and is not allowed to prevent data loss.")
            exitProcess(1)
        }
    }

    private fun runningOnEmulator(): Boolean = Build.FINGERPRINT.contains("generic_")
            || Build.MODEL.contains("Android SDK")
            || Build.MODEL.contains("Emulator")
            || Build.DEVICE.contains("generic_")
            || Build.BOARD.contains("goldfish_")
            || Build.PRODUCT.contains("sdk_")
}
