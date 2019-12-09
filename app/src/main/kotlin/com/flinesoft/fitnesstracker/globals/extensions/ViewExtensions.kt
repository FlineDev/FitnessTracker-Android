package com.flinesoft.fitnesstracker.globals.extensions

import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import com.flinesoft.fitnesstracker.globals.BOTTOM_NAVIGATION_VIEW_HEIGHT
import com.google.android.material.snackbar.Snackbar

fun View.snack(resId: Int, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, resId, duration).apply {
        (view.layoutParams as? MarginLayoutParams)?.apply {
            // TODO: changing bottom margin doesn't work, left & right are working though ...
            setMargins(leftMargin, topMargin, rightMargin, bottomMargin + BOTTOM_NAVIGATION_VIEW_HEIGHT)
        }
    }.show()
}
