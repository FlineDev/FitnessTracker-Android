package com.flinesoft.fitnesstracker.persistence

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

@Suppress("UnnecessaryAbstractClass")
abstract class CrudDao<T> {
    @Insert
    protected abstract suspend fun insert(obj: T): Long

    @Update
    abstract suspend fun update(obj: T)

    @Delete
    abstract suspend fun delete(obj: T)
}
