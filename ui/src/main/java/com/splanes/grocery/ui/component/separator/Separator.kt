package com.splanes.grocery.ui.component.separator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.splanes.grocery.ui.utils.resources.palette
import com.splanes.grocery.ui.utils.resources.shape
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha
import com.splanes.toolkit.compose.ui.theme.feature.colors.ThemeColorScheme

object Separator {
    const val thickness: Double = 1.0
    const val alpha: Double = .05
    const val cornerSize: Int = 20
    enum class Orientation {
        Vertical, Horizontal
    }
}

@Composable
fun Separator(
    orientation: Separator.Orientation,
    modifier: Modifier = Modifier,
    color: Color = palette { onSurface },
    paddings: PaddingValues = PaddingValues(),
    thickness: Dp = Separator.thickness.dp,
    alpha: Double = Separator.alpha,
    cornerSize: Int = Separator.cornerSize,
) {
    Divider(
        modifier = modifier
            .orientation(orientation, thickness)
            .padding(paddings)
            .background(color = color.alpha(alpha), shape = shape(size = cornerSize)),
        color = color.alpha(alpha)
    )
}

@Composable
fun Separator(
    orientation: Separator.Orientation,
    modifier: Modifier = Modifier,
    paddings: PaddingValues = PaddingValues(),
    thickness: Dp = Separator.thickness.dp,
    alpha: Double = Separator.alpha,
    cornerSize: Int = Separator.cornerSize,
    color: @Composable ThemeColorScheme.() -> Color
) {
    Separator(
        modifier = modifier,
        orientation = orientation,
        paddings = paddings,
        color = palette(block = color),
        thickness = thickness,
        alpha = alpha,
        cornerSize = cornerSize,
    )
}

@Composable
fun ColumnScope.Separator(
    modifier: Modifier = Modifier,
    color: Color = palette { onSurface },
    paddings: PaddingValues = PaddingValues(),
    thickness: Dp = Separator.thickness.dp,
    alpha: Double = Separator.alpha,
    cornerSize: Int = Separator.cornerSize,
) {
    Separator(
        modifier = modifier,
        orientation = Separator.Orientation.Horizontal,
        paddings = paddings,
        color = color,
        thickness = thickness,
        alpha = alpha,
        cornerSize = cornerSize,
    )
}

@Composable
fun ColumnScope.Separator(
    modifier: Modifier = Modifier,
    paddings: PaddingValues = PaddingValues(),
    thickness: Dp = Separator.thickness.dp,
    alpha: Double = Separator.alpha,
    cornerSize: Int = Separator.cornerSize,
    color: @Composable ThemeColorScheme.() -> Color
) {
    Separator(
        modifier = modifier,
        orientation = Separator.Orientation.Horizontal,
        paddings = paddings,
        color = color,
        thickness = thickness,
        alpha = alpha,
        cornerSize = cornerSize,
    )
}

@Composable
fun RowScope.Separator(
    modifier: Modifier = Modifier,
    color: Color = palette { onSurface },
    paddings: PaddingValues = PaddingValues(),
    thickness: Dp = Separator.thickness.dp,
    alpha: Double = Separator.alpha,
    cornerSize: Int = Separator.cornerSize,
) {
    Separator(
        modifier = modifier,
        orientation = Separator.Orientation.Vertical,
        paddings = paddings,
        color = color,
        thickness = thickness,
        alpha = alpha,
        cornerSize = cornerSize,
    )
}

@Composable
fun RowScope.Separator(
    modifier: Modifier = Modifier,
    paddings: PaddingValues = PaddingValues(),
    thickness: Dp = Separator.thickness.dp,
    alpha: Double = Separator.alpha,
    cornerSize: Int = Separator.cornerSize,
    color: @Composable ThemeColorScheme.() -> Color
) {
    Separator(
        modifier = modifier,
        orientation = Separator.Orientation.Vertical,
        paddings = paddings,
        color = color,
        thickness = thickness,
        alpha = alpha,
        cornerSize = cornerSize,
    )
}

internal fun Modifier.orientation(
    orientation: Separator.Orientation,
    thickness: Dp
): Modifier = this.then(
    when (orientation) {
        Separator.Orientation.Vertical -> Modifier
            .fillMaxHeight()
            .width(thickness)
        Separator.Orientation.Horizontal -> Modifier
            .fillMaxWidth()
            .height(thickness)
    }
)
