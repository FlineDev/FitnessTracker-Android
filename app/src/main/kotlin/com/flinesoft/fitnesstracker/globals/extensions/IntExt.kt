package com.flinesoft.fitnesstracker.globals.extensions

import android.graphics.Color
import com.flinesoft.fitnesstracker.globals.DownPopLevel

fun Int.withAlphaDownPoppedTo(level: DownPopLevel): Int {
    return Color.argb(
        (Color.alpha(this) * level.alpha()).toInt(),
        Color.red(this),
        Color.green(this),
        Color.blue(this)
    )
}
