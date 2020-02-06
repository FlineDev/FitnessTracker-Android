package com.flinesoft.fitnesstracker.globals

import android.net.Uri

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

// Source: https://www.bmj.com/content/311/6998/158
const val WAIST_CIRCUMFERENCE_FEMALE_HEALTHY_MAX: Double = 80.0
const val WAIST_CIRCUMFERENCE_MALE_HEALTHY_MAX: Double = 94.0
const val WAIST_CIRCUMFERENCE_FEMALE_HIGH_RISK: Double = 88.0
const val WAIST_CIRCUMFERENCE_MALE_HIGH_RISK: Double = 102.0

const val BETWEEN_WORKOUTS_POSITIVE_DAYS: Int = 2
const val BETWEEN_WORKOUTS_POSITIVE_PLUS_WARNING_DAYS: Int = BETWEEN_WORKOUTS_POSITIVE_DAYS + 3

const val DEFAULT_WORKOUT_DURATION_MINUTES: Int = 60
const val DEFAULT_IMPEDIMENT_DAYS: Int = 3
const val MAX_WORKOUT_DURATION_HOURS: Int = 10
const val MAX_IMPEDIMENT_DURATION_IN_DAYS: Int = 180
const val PREVENT_NEXT_DAY_WHEN_WORKOUT_WITHIN_HOURS: Int = 4

val HUMAN_WEIGHT_RANGE_IN_KILOGRAMS: ClosedRange<Double> = 0.2..600.0
val HUMAN_AGE_RANGE: IntRange = 0..130
val HUMAN_HEIGHT_RANGE_IN_CENTIMETERS: IntRange = 10..300
val HUMAN_WAIST_CIRCUMFERENCE_RANGE_IN_CENTIMETERS: ClosedRange<Double> = 10.0..300.0

const val DEFAULT_INPUT_VALUE_WEIGHT_IN_KILOGRAMS: Double = 65.0
const val DEFAULT_INPUT_VALUE_WAIST_CIRCUMFERENCE_IN_CENTIMETERS: Double = 90.0

val APP_FEEDBACK_FORUM_URL: Uri = Uri.parse("https://community.flinesoft.com/c/fitness-tracker-app/")

const val DEFAULT_ON_DAY_REMINDER_HOUR: Int = 6
const val DEFAULT_REMINDERS_ON: Boolean = true
const val DEFAULT_REMINDER_DAYS_COUNT: Int = 7

const val DEFAULT_MODAL_PRESENTATION_DELAY: Long = 300
