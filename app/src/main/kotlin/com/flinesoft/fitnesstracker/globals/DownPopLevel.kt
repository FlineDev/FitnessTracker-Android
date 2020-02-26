package com.flinesoft.fitnesstracker.globals

enum class DownPopLevel {
    LEVEL1, LEVEL2, LEVEL3, LEVEL4, LEVEL5;

    fun alpha(): Float = when (this) {
        LEVEL1 -> 0.88f
        LEVEL2 -> 0.72f
        LEVEL3 -> 0.55f
        LEVEL4 -> 0.28f
        LEVEL5 -> 0.12f
    }
}
