package com.flinesoft.fitnesstracker.globals

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit
import com.flinesoft.fitnesstracker.model.Gender
import org.joda.time.DateTime
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.hours
import kotlin.time.milliseconds

@ExperimentalTime
object AppPreferences {
    private var sharedPreferences: SharedPreferences? = null

    fun setup(context: Context) {
        sharedPreferences = context.getSharedPreferences("FitnessTracker.sharedprefs", MODE_PRIVATE)
    }

    fun clear() = sharedPreferences?.edit { clear() }

    var onboardingCompleted: Boolean
        get() = Key.ONBOARDING_COMPLETED.getBoolean() ?: false
        set(value) = Key.ONBOARDING_COMPLETED.setBoolean(value)

    var lastStartedVersionCode: Int?
        get() = Key.LAST_SARTED_VERSION_CODE.getInt()
        set(value) = Key.LAST_SARTED_VERSION_CODE.setInt(value)

    var heightInCentimeters: Int?
        get() = Key.HEIGHT.getInt()
        set(value) = Key.HEIGHT.setInt(value)

    var birthYear: Int?
        get() = Key.BIRTH_YEAR.getInt()
        set(value) = Key.BIRTH_YEAR.setInt(value)

    var gender: Gender?
        get() = Key.GENDER_FEMALE.getBoolean()?.let { if (it) Gender.FEMALE else Gender.MALE }
        set(value) = value?.let { Key.GENDER_FEMALE.setBoolean(it == Gender.FEMALE) } ?: Key.GENDER_FEMALE.remove()

    var onDayReminderOn: Boolean
        get() = Key.REMINDER_ON.getBoolean() ?: DEFAULT_REMINDERS_ON
        set(value) = Key.REMINDER_ON.setBoolean(value)

    var onDayReminderDelay: Duration
        get() = Key.REMINDER_DAY_TIME.getLong()?.milliseconds ?: DEFAULT_ON_DAY_REMINDER_HOUR.hours
        set(value) = Key.REMINDER_DAY_TIME.setLong(value.toLongMilliseconds())

    var lastScheduledReminderDate: DateTime?
        get() = Key.REMINDER_DATE.getLong()?.let { DateTime(it) }
        set(value) = value?.let { Key.REMINDER_DATE.setLong(it.millis) } ?: Key.REMINDER_DATE.remove()

    private enum class Key {
        ONBOARDING_COMPLETED, LAST_SARTED_VERSION_CODE, HEIGHT, BIRTH_YEAR, GENDER_FEMALE, REMINDER_DAY_TIME, REMINDER_DATE, REMINDER_ON;

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

        fun exists(): Boolean = sharedPreferences!!.contains(name)
        fun remove() = sharedPreferences!!.edit { remove(name) }
    }
}
