package com.splanes.grocery.ui.component.forms

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.splanes.grocery.ui.utils.anim.Animations

@Composable
fun Form.Colors.surface(
    valid: Boolean,
    animSpec: AnimationSpec<Color> = tween(durationMillis = 200)
): Color = Animations.animColor(animSpec) {
    when {
        !valid -> surfaceOnError
        else -> surfaceOnValid
    }
}