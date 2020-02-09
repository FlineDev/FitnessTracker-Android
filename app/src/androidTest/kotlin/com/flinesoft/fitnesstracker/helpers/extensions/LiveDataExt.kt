package com.flinesoft.fitnesstracker.helpers.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

// NOTE: Get LiveData working in tests (without observers), see also: https://stackoverflow.com/a/44991770/3451975
fun <T> LiveData<T>.awaitValue(): T? {
    var value: T? = null
    val latch = CountDownLatch(1)

    val observer = Observer<T> { t ->
        value = t
        latch.countDown()
    }

    observeForever(observer)

    latch.await(2, TimeUnit.SECONDS)
    return value
}