package com.flinesoft.fitnesstracker.globals

import com.flinesoft.fitnesstracker.BuildConfig

// TODO: add a warning when code with `runIfDebug` is found in the code – should only be used temporarily
fun runIfDebug(closure: () -> Unit) {
    if (BuildConfig.DEBUG) {
        closure()
    }
}
