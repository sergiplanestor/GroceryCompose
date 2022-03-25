package com.splanes.grocery.utils.time

import java.util.concurrent.TimeUnit

object Seconds {
    fun millis(seconds: Int): Long =
        TimeUnit.SECONDS.toMillis(seconds.toLong())
}