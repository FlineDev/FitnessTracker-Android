package com.flinesoft.fitnesstracker.globals.extensions

import android.graphics.Color

fun Int.withDownPoppedAlpha(): Int {
    return Color.argb(
        (Color.alpha(this) * 0.7f).toInt(),
        Color.red(this),
        Color.green(this),
        Color.blue(this)
    )
}
