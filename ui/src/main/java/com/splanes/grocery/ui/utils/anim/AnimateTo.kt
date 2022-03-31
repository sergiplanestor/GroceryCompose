package com.splanes.grocery.ui.utils.anim

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun <T> CoroutineScope.animate(
    anim: Animatable<T, *>,
    target: T,
    duration: Int = AnimDefaults.DurationMedium.toInt(),
    animSpec: AnimationSpec<T> = tween(durationMillis = duration),
    onUpdate: (T) -> Unit = {},
    onFinish: () -> Unit = {},
) {
    launch {
        anim.animateTo(targetValue = target, animationSpec = animSpec) {
            onUpdate(value)
        }.takeIf {
            it.finished
        }?.run {
            onFinish()
        }
    }
}