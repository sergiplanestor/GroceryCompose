package com.splanes.grocery.ui.component.anim

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.splanes.grocery.ui.utils.anim.AnimDefaults
import com.splanes.grocery.ui.utils.anim.animate
import kotlinx.coroutines.CoroutineScope

private fun <T> CoroutineScope.animateSequence(
    pending: MutableList<AnimDefaults.SideEffectUi<T>>
) {
    pending.takeIf { it.isNotEmpty() }?.firstOrNull()?.run {
        animate(
            anim = anim,
            target = target,
            duration = duration,
            animSpec = animSpec,
            onUpdate = onUpdate,
            onFinish = {
                onFinish()
                animateSequence(pending.apply { removeAt(0) })
            }
        )
    }
}

@Composable
fun <T> AnimationSideEffect(
    vararg animations: AnimDefaults.SideEffectUi<T>,
    sideEffectKey: String = "AnimationSideEffect"
) {
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(sideEffectKey) {
        with(coroutineScope) {
            animateSequence(animations.toMutableList())
        }
    }
}

@Composable
fun <T> AnimationSideEffect(
    anim: Animatable<T, *>,
    target: T,
    duration: Int = AnimDefaults.DurationMedium.toInt(),
    animSpec: AnimationSpec<T> = tween(durationMillis = duration),
    sideEffectKey: String = "AnimationSideEffect",
    onUpdate: (T) -> Unit = {},
    onFinish: () -> Unit = {},
) {
    AnimationSideEffect(
        AnimDefaults.SideEffectUi(
            anim,
            target,
            duration,
            animSpec,
            onUpdate,
            onFinish
        ),
        sideEffectKey = sideEffectKey,
    )
}

