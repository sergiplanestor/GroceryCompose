package com.splanes.grocery.ui.component.forms.fields

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.splanes.grocery.ui.component.forms.Field
import com.splanes.grocery.ui.utils.anim.Animations

@Composable
fun Field.Color.value(
    valid: Boolean,
    focused: Boolean,
    enabled: Boolean,
    animSpec: AnimationSpec<Color> = tween(durationMillis = 200)
): Color = Animations.animColor(animSpec) {
    when {
        !enabled -> if (valid) this@value.disabled else this@value.errorDisabled
        focused && valid -> this@value.validAccent
        !focused && valid -> this@value.valid
        focused && !valid -> this@value.errorAccent
        else /* !focused && !valid */ -> this@value.error
    }
}