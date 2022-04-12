package com.splanes.grocery.ui.utils.ripple

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import com.splanes.toolkit.compose.ui.components.common.utils.color.isLighterColor
import java.lang.Float.max

object Ripple {

    data class Alpha(
        val pressed: Float,
        val focused: Float = pressed,
        val dragged: Float = with(max(pressed, focused)) { this - (this / 3) },
        val hovered: Float = max(pressed, focused) / 3,
    ) {
        fun toRippleAlpha(): RippleAlpha = RippleAlpha(
            pressedAlpha = pressed,
            focusedAlpha = focused,
            draggedAlpha = dragged,
            hoveredAlpha = hovered
        )

        companion object {

            private val LightHighContrast: Alpha = Alpha(pressed = 0.24f)

            private val LightLowContrast: Alpha = Alpha(pressed = 0.12f)

            private val Dark: Alpha = Alpha(pressed = 0.10f, focused = 0.12f)

            @Composable
            fun default(color: Color): Alpha = when {
                !isSystemInDarkTheme() -> if (color.isLighterColor()) LightHighContrast else LightLowContrast
                else -> Dark
            }
        }
    }

    @Composable
    fun theme(
        color: Color,
        alphaPressed: Float? = null,
        alphaFocused: Float? = null,
        alphaDragged: Float? = null,
        alphaHovered: Float? = null,
        alpha: Alpha = Alpha.default(color = color).run {
            copy(
                pressed = alphaPressed ?: pressed,
                focused = alphaFocused ?: focused,
                dragged = alphaDragged ?: dragged,
                hovered = alphaHovered ?: hovered
            )
        }
    ): RippleTheme = object : RippleTheme {
        @Composable
        override fun defaultColor(): Color = color

        @Composable
        override fun rippleAlpha(): RippleAlpha = alpha.toRippleAlpha()
    }
}

@Composable
fun RippleStyle(color: Color, theme: RippleTheme = Ripple.theme(color = color), block: @Composable () -> Unit) {
    CompositionLocalProvider(LocalRippleTheme provides theme) {
        block()
    }
}