package com.flinesoft.fitnesstracker.globals

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit
import com.flinesoft.fitnesstracker.model.Gender
import org.joda.time.DateTime
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds

@ExperimentalTime
object AppPreferences {
    private var sharedPreferences: SharedPreferences? = null

    fun setup(context: Context) {
        sharedPreferences = context.getSharedPreferences("FitnessTracker.sharedprefs", MODE_PRIVATE)
    }

    var heightInCentimeters: Int?
        get() = Key.HEIGHT.getInt()
        set(value) = Key.HEIGHT.setInt(value)

    var birthYear: Int?
        get() = Key.BIRTH_YEAR.getInt()
        set(value) = Key.BIRTH_YEAR.setInt(value)

    var gender: Gender?
        get() = Key.GENDER_FEMALE.getBoolean()?.let { if (it) Gender.FEMALE else Gender.MALE }
        set(value) = value?.let { Key.GENDER_FEMALE.setBoolean(it == Gender.FEMALE) } ?: Key.GENDER_FEMALE.remove()

    var onDayReminderDelay: Duration?
        get() = Key.REMINDER_DAY_TIME.getLong()?.milliseconds
        set(value) = value?.let { Key.REMINDER_DAY_TIME.setLong(it.toLongMilliseconds()) } ?: Key.REMINDER_DAY_TIME.remove()

    var lastScheduledReminderDate: DateTime?
        get() = Key.REMINDER_DATE.getLong()?.let { DateTime(it) }
        set(value) = value?.let { Key.REMINDER_DATE.setLong(it.millis) } ?: Key.REMINDER_DATE.remove()

    private enum class Key {
        HEIGHT, BIRTH_YEAR, GENDER_FEMALE, REMINDER_DAY_TIME, REMINDER_DATE;

        fun getBoolean(): Boolean? = if (sharedPreferences!!.contains(name)) sharedPreferences!!.getBoolean(name, false) else null
        fun getFloat(): Float? = if (sharedPreferences!!.contains(name)) sharedPreferences!!.getFloat(name, 0f) else null
        fun getInt(): Int? = if (sharedPreferences!!.contains(name)) sharedPreferences!!.getInt(name, 0) else null
        fun getLong(): Long? = if (sharedPreferences!!.contains(name)) sharedPreferences!!.getLong(name, 0) else null
        fun getString(): String? = if (sharedPreferences!!.contains(name)) sharedPreferences!!.getString(name, "") else null

        fun setBoolean(value: Boolean?) = value?.let { sharedPreferences!!.edit { putBoolean(name, value) } } ?: remove()
        fun setFloat(value: Float?) = value?.let { sharedPreferences!!.edit { putFloat(name, value) } } ?: remove()
        fun setInt(value: Int?) = value?.let { sharedPreferences!!.edit { putInt(name, value) } } ?: remove()
        fun setLong(value: Long?) = value?.let { sharedPreferences!!.edit { putLong(name, value) } } ?: remove()
        fun setString(value: String?) = value?.let { sharedPreferences!!.edit { putString(name, value) } } ?: remove()

        fun remove() = sharedPreferences!!.edit { remove(name) }
    }
}
