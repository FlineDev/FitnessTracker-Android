package com.flinesoft.fitnesstracker.globals

enum class DownPopLevel {
    LEVEL1, LEVEL2, LEVEL3, LEVEL4, LEVEL5;

    fun alpha(): Float = when (this) {
        LEVEL1 -> 0.85f
        LEVEL2 -> 0.7f
        LEVEL3 -> 0.5f
        LEVEL4 -> 0.3f
        LEVEL5 -> 0.15f
    }
}
