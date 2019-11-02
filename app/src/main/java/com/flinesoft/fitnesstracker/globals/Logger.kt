package com.flinesoft.fitnesstracker.globals

import com.flinesoft.fitnesstracker.BuildConfig
import timber.log.Timber

object Logger {
    fun setup() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}