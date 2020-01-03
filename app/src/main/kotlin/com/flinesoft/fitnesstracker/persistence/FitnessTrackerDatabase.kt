package com.flinesoft.fitnesstracker.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.flinesoft.fitnesstracker.model.WaistCircumferenceMeasurement
import com.flinesoft.fitnesstracker.model.WeightMeasurement
import com.flinesoft.fitnesstracker.model.Workout
import com.flinesoft.fitnesstracker.persistence.converters.DateTimeConverter
import com.flinesoft.fitnesstracker.persistence.converters.EnumConverters
import kotlin.time.ExperimentalTime

@ExperimentalTime
@Database(entities = [WaistCircumferenceMeasurement::class, WeightMeasurement::class, Workout::class], version = 2)
@TypeConverters(DateTimeConverter::class, EnumConverters::class)
abstract class FitnessTrackerDatabase : RoomDatabase() {
    abstract val waistCircumferenceMeasurementDao: WaistCircumferenceMeasurementDao
    abstract val weightMeasurementDao: WeightMeasurementDao
    abstract val workoutDao: WorkoutDao

    companion object {
        @Volatile
        private var INSTANCE: FitnessTrackerDatabase? = null

        fun getInstance(context: Context): FitnessTrackerDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FitnessTrackerDatabase::class.java,
                        "FitnessTrackerDatabase"
                    )
                        .addMigrations(migrationFrom1To2)
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }

        private val migrationFrom1To2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("DROP TABLE Impediments")
            }
        }
    }
}
