package com.splanes.grocery.ui.utils.anim

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween

object AnimDefaults {

    val DurationShort: Long by lazy { 300 }
    val DurationMedium: Long by lazy { 500 }
    val DurationLarge: Long by lazy { 750 }

    val DelayShort: Long by lazy { 100 }
    val DelayMedium: Long by lazy { 200 }
    val DelayLarge: Long by lazy { 300 }

    data class SideEffectUi<T>(
        val anim: Animatable<T, *>,
        val target: T,
        val duration: Int = DurationMedium.toInt(),
        val animSpec: AnimationSpec<T> = tween(durationMillis = duration),
        val onUpdate: (T) -> Unit = {},
        val onFinish: () -> Unit = {},
    )
}