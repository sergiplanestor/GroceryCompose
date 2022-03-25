package com.splanes.grocery.ui.utils.anim

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing

fun <T> tween(duration: Long, delay: Long = 0, easing: Easing = FastOutSlowInEasing) =
    androidx.compose.animation.core.tween<T>(
        durationMillis = duration.toInt(),
        delayMillis = delay.toInt(),
        easing = easing
    )