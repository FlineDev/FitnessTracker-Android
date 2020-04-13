package com.flinesoft.fitnesstracker.globals

import com.flinesoft.fitnesstracker.BuildConfig

// TODO: [2020-02-23] code using this methods should not be committed – a lint rule should check that
/** Use for trying out some code temporarily that you don't want to accidentally commit or ship. Will be automatically excluded from release builds. */
fun runIfDebugTemporarily(closure: () -> Unit) = runIfDebug(closure)

/** Use for code that is only needed for the automated tests to work. Will be automatically excluded from release builds. */
fun runIfDebugForTesting(closure: () -> Unit) = runIfDebug(closure)

private fun runIfDebug(closure: () -> Unit) { if (BuildConfig.DEBUG) { closure() } }
