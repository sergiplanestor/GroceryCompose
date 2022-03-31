package com.splanes.grocery.ui.utils.anim

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun CoroutineScope.scrollTo(
    scrollState: ScrollState,
    target: Int,
    spec: AnimationSpec<Float> = tween()
) {
    launch { scrollState.animateScrollTo(target, spec) }
}