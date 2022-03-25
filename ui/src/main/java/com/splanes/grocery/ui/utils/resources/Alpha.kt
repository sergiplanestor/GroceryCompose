package com.splanes.grocery.ui.utils.resources

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ContentAlpha
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha
import com.splanes.toolkit.compose.ui.components.common.utils.color.isDarkerColor

enum class AlphaType {
    High,
    Medium,
    Low,
    Disabled
}

@Composable
fun Color.alpha(type: AlphaType): Color =
    this.alpha(
        when (type) {
            AlphaType.High -> ContentAlpha.high
            AlphaType.Medium -> ContentAlpha.medium
            AlphaType.Low -> {
                val isDarkTheme = isSystemInDarkTheme()
                val isDarkColor = isDarkerColor()
                if (isDarkTheme && isDarkColor || !isDarkTheme && !isDarkColor) {
                     .5f
                } else {
                    .3f
                }
            }
            AlphaType.Disabled -> ContentAlpha.disabled
        }
    )