package com.flinesoft.fitnesstracker.globals

import android.net.Uri

const val BOTTOM_NAVIGATION_VIEW_HEIGHT: Int = 52
const val DIALOG_HORIZONTAL_SPACING: Int = 50

// Source: https://en.wikipedia.org/wiki/Body_mass_index
const val BODY_MASS_INDEX_LOWER_HIGH_RISK: Double = 16.0
const val BODY_MASS_INDEX_HEALTHY_MIN: Double = 18.5
const val BODY_MASS_INDEX_HEALTHY_MAX: Double = 25.0
const val BODY_MASS_INDEX_UPPER_HIGH_RISK: Double = 30.0

// Source: https://www.mytecbits.com/tools/medical/absi-calculator
const val BODY_SHAPE_INDEX_VERY_LOW_RISK_MAX: Double = -0.868
const val BODY_SHAPE_INDEX_LOW_RISK_MAX: Double = -0.272
const val BODY_SHAPE_INDEX_AVERAGE_RISK_MAX: Double = 0.229
const val BODY_SHAPE_INDEX_HIGH_RISK_MAX: Double = 0.798

const val BETWEEN_WORKOUTS_POSITIVE_DAYS: Int = 2
const val BETWEEN_WORKOUTS_NEUTRAL_DAYS: Int = 3

const val DEFAULT_WORKOUT_DURATION_MINUTES: Int = 60
const val MAX_WORKOUT_DURATION_HOURS: Int = 10
const val WORKOUT_RECOMMENDATION_ADDITIONAL_HOURS: Int = 4

val HUMAN_WEIGHT_RANGE_IN_KILOGRAMS: ClosedRange<Double> = 0.2..600.0
val HUMAN_AGE_RANGE: ClosedRange<Double> = 0.0..130.0
val HUMAN_HEIGHT_RANGE_IN_CENTIMETERS: ClosedRange<Double> = 10.0..300.0
val HUMAN_WAIST_CIRCUMFERENCE_IN_CENTIMETERS: ClosedRange<Double> = 10.0..300.0

val APP_FEEDBACK_FORUM_URL: Uri = Uri.parse("https://community.flinesoft.com/c/fitness-tracker-app/")
