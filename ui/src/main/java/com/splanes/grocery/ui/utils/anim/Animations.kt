package com.splanes.grocery.ui.utils.anim

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.splanes.grocery.ui.utils.resources.palette
import com.splanes.toolkit.compose.ui.theme.feature.colors.ThemeColorScheme
import com.splanes.toolkit.compose.ui.theme.utils.size.UiSize

object Animations {

    val duration: Duration by lazy {
        Duration(
            tiny = 100,
            smallTiny = 175,
            small = 200,
            mediumSmall = 275,
            medium = 350,
            mediumLarge = 500,
            large = 750,
            largeHuge = 1000,
            huge = 1250,
            hugeExtra = 1500,
            extra = 2000,
        )
    }

    data class Duration(
        override val tiny: Int,
        override val smallTiny: Int,
        override val small: Int,
        override val mediumSmall: Int,
        override val medium: Int,
        override val mediumLarge: Int,
        override val large: Int,
        override val largeHuge: Int,
        override val huge: Int,
        override val hugeExtra: Int,
        override val extra: Int,
    ) : UiSize.Extended<Int>

    fun <T> tween(
        delay: (Duration.() -> Int)? = null,
        easing: Easing = FastOutSlowInEasing,
        millis: Duration.() -> Int
    ) =
        androidx.compose.animation.core.tween<T>(
            durationMillis = duration.millis(),
            delayMillis = delay?.invoke(duration) ?: 0,
            easing = easing
        )

    @Composable
    fun floatAsState(
        condition: Boolean,
        onTrue: Double,
        onFalse: Double,
        animSpec: AnimationSpec<Float> = spring(),
        visibilityThreshold: Float = 0.01f,
        onEnd: ((Float) -> Unit)? = null
    ): State<Float> =
        animateFloatAsState(
            targetValue = (if (condition) onTrue else onFalse).toFloat(),
            animationSpec = animSpec,
            visibilityThreshold = visibilityThreshold,
            finishedListener = onEnd
        )

    @Composable
    fun animColor(
        animSpec: AnimationSpec<Color> = tween { medium },
        color: @Composable ThemeColorScheme.() -> Color
    ): Color {
        val c by animateColorAsState(targetValue = palette(color), animationSpec = animSpec)
        return c
    }

    @Composable
    fun animColor(
        condition: Boolean,
        onTrue: Color,
        onFalse: Color,
        animSpec: AnimationSpec<Color> = tween { medium },
        onEnd: ((Color) -> Unit)? = null
    ): Color {
        val color by colorAsState(
            condition = condition,
            colorOnTrue = onTrue,
            colorOnFalse = onFalse,
            animSpec = animSpec,
            onEnd = onEnd
        )
        return color
    }

    @Composable
    fun colorAsState(
        condition: Boolean,
        colorOnTrue: Color,
        colorOnFalse: Color,
        animSpec: AnimationSpec<Color> = tween { medium },
        onEnd: ((Color) -> Unit)? = null
    ): State<Color> =
        animateColorAsState(
            targetValue = if (condition) colorOnTrue else colorOnFalse,
            animationSpec = animSpec,
            finishedListener = onEnd
        )

    @Composable
    fun dpAsState(
        condition: Boolean,
        onTrue: Dp,
        onFalse: Dp,
        animSpec: AnimationSpec<Dp> = spring(visibilityThreshold = Dp.VisibilityThreshold),
        onEnd: ((Dp) -> Unit)? = null
    ): State<Dp> =
        animateDpAsState(
            targetValue = if (condition) onTrue else onFalse,
            animationSpec = animSpec,
            finishedListener = onEnd
        )
}