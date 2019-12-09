package com.flinesoft.fitnesstracker.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.flinesoft.fitnesstracker.model.Impediment
import com.flinesoft.fitnesstracker.model.WaistCircumferenceMeasurement
import com.flinesoft.fitnesstracker.model.WeightMeasurement
import com.flinesoft.fitnesstracker.model.Workout
import com.flinesoft.fitnesstracker.persistence.converters.DateTimeConverter
import com.flinesoft.fitnesstracker.persistence.converters.EnumConverters

@Database(entities = [Impediment::class, WaistCircumferenceMeasurement::class, WeightMeasurement::class, Workout::class], version = 1)
@TypeConverters(DateTimeConverter::class, EnumConverters::class)
abstract class FitnessTrackerDatabase : RoomDatabase() {
    abstract val impedimentDao: ImpedimentDao
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
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}
