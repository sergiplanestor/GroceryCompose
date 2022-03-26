package com.splanes.grocery.ui.utils.resources

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ContentAlpha
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.splanes.grocery.utils.logger.utils.throwMessage
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha
import com.splanes.toolkit.compose.ui.components.common.utils.color.isDarkerColor


class AlphaType internal constructor() {
    val high = 0
    val medium = 1
    val low = 2
    val disabled = 3
}

@Composable
fun Color.alpha(block: AlphaType.() -> Int): Color =
    alpha(when (block(AlphaType())) {
        0 -> ContentAlpha.high
        1 -> ContentAlpha.medium
        2 -> {
            val isDarkTheme = isSystemInDarkTheme()
            val isDarkColor = isDarkerColor()
            if (isDarkTheme && isDarkColor || !isDarkTheme && !isDarkColor) {
                .5f
            } else {
                .3f
            }
        }
        3 -> ContentAlpha.disabled
        else -> throwMessage { "Not handled value" }
    })