package com.flinesoft.fitnesstracker.ui.workouts.editReminders

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.flinesoft.fitnesstracker.globals.AppPreferences
import org.joda.time.DateTime
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.hours
import kotlin.time.minutes

@ExperimentalTime
class EditRemindersViewModel(application: Application) : AndroidViewModel(application) {
    var remindersOn = MutableLiveData<Boolean>(AppPreferences.onDayReminderOn)
    var remindersDayDelay = MutableLiveData<Duration>(AppPreferences.onDayReminderDelay)

    fun dateWithDayDelay(delay: Duration): DateTime = DateTime.now().withTimeAtStartOfDay().plus(delay.toLongMilliseconds())
    fun dateWithRemindersDayDelay(): DateTime = dateWithDayDelay(remindersDayDelay.value!!)

    fun updateDayDelay(hour: Int, minute: Int) = kotlin.run { remindersDayDelay.value = hour.hours + minute.minutes }

    fun save() {
        AppPreferences.onDayReminderOn = remindersOn.value!!
        AppPreferences.onDayReminderDelay = remindersDayDelay.value!!
    }
}
