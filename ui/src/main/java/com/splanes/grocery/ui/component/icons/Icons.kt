package com.splanes.grocery.ui.component.icons

import androidx.annotation.DrawableRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.splanes.grocery.ui.utils.resources.painter
import com.splanes.grocery.ui.utils.resources.palette
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha
import androidx.compose.material.icons.Icons.Filled as IcFilled
import androidx.compose.material.icons.Icons.Outlined as IcOutlined
import androidx.compose.material.icons.Icons.Rounded as IcRounded
import androidx.compose.material.icons.Icons.Sharp as IcSharp
import androidx.compose.material.icons.Icons.TwoTone as IcTwoTone
import androidx.compose.material3.Icon as MaterialIcon

object Icons {

    sealed class Source { companion object }
    data class Painter(@DrawableRes val value: Int) : Source()
    data class Vector(val value: ImageVector) : Source()

    const val SizeTiny: Int = 18
    const val SizeSmall: Int = 20
    const val SizeMedium: Int = 24
    const val SizeLarge: Int = 28

    @Composable
    fun Icon(
        source: Source,
        modifier: Modifier = Modifier,
        size: Dp = SizeMedium.dp,
        color: Color = palette { onSurface }.alpha(.7),
        interactionSource: MutableInteractionSource = MutableInteractionSource(),
        onClick: (() -> Unit)? = null,
        enabled: Boolean = true,
        description: String? = null
    ) {
        val content: @Composable (Modifier) -> Unit = {
            when (source) {
                is Painter -> {
                    MaterialIcon(
                        painter = painter { source.value },
                        modifier = it.size(size),
                        tint = color,
                        contentDescription = description
                    )
                }
                is Vector -> {
                    MaterialIcon(
                        imageVector = source.value,
                        modifier = it.size(size),
                        tint = color,
                        contentDescription = description
                    )
                }
            }
        }
        if (onClick != null) {
            IconButton(
                modifier = modifier,
                enabled = enabled,
                interactionSource = interactionSource,
                onClick = { onClick() }
            ) {
                content(Modifier)
            }
        } else {
            content(modifier)
        }
    }

    @Composable
    fun WithRipple(
        color: Color,
        shape: Shape,
        content: @Composable () -> Unit
    ) {
        val ripple = object : RippleTheme {
            @Composable
            override fun defaultColor(): Color = color

            @Composable
            override fun rippleAlpha(): RippleAlpha = RippleTheme.defaultRippleAlpha(
                contentColor = color,
                lightTheme = !isSystemInDarkTheme()
            )
        }
        CompositionLocalProvider(LocalRippleTheme provides ripple) {

            content()
        }
    }
}

fun Icons.painter(res: Int): Icons.Painter = Icons.Painter(res)
fun Icons.filled(block: IcFilled.() -> ImageVector): Icons.Vector = Icons.Vector(block(IcFilled))
fun Icons.rounded(block: IcRounded.() -> ImageVector): Icons.Vector = Icons.Vector(block(IcRounded))
fun Icons.sharp(block: IcSharp.() -> ImageVector): Icons.Vector = Icons.Vector(block(IcSharp))
fun Icons.outlined(block: IcOutlined.() -> ImageVector): Icons.Vector = Icons.Vector(block(IcOutlined))
fun Icons.twoTone(block: IcTwoTone.() -> ImageVector): Icons.Vector = Icons.Vector(block(IcTwoTone))