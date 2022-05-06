package com.splanes.grocery.ui.component.form.textinput.utils

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.splanes.grocery.ui.component.form.Forms
import com.splanes.grocery.ui.utils.resources.unfocused
import com.splanes.grocery.utils.scope.apply

@Composable
fun TextFieldColors.label(enabled: Boolean, error: Boolean, focused: Boolean): Color = labelColor(
    enabled = enabled,
    error = error,
    interactionSource = MutableInteractionSource()
).value.apply(!focused) { unfocused() }

@Composable
fun TextFieldColors.placeholder(enabled: Boolean): Color = placeholderColor(enabled = enabled).value

@Composable
fun TextFieldColors.icon(position: Forms.IconPosition, enabled: Boolean, error: Boolean): Color =
    when (position) {
        Forms.IconPosition.Leading -> leadingIconColor(enabled = enabled, isError = error)
        Forms.IconPosition.Trailing -> trailingIconColor(enabled = enabled, isError = error)
    }.value