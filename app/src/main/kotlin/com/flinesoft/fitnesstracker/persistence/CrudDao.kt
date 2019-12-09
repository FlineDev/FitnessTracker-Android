package com.flinesoft.fitnesstracker.persistence

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

abstract class CrudDao<T> {
    @Insert
    protected abstract fun insert(obj: T): Long

    @Update
    abstract fun update(obj: T)

    @Delete
    abstract fun delete(obj: T)
}
