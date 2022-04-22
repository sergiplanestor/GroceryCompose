package com.splanes.grocery.ui.component.checkbox

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.rounded.LocalGroceryStore
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.splanes.grocery.ui.component.icons.Icons
import com.splanes.grocery.ui.component.icons.rounded
import com.splanes.grocery.ui.component.spacer.row.Space
import com.splanes.grocery.ui.utils.anim.Animations
import com.splanes.grocery.ui.utils.resources.bodyStyle
import com.splanes.grocery.ui.utils.resources.dp
import com.splanes.grocery.ui.utils.resources.palette
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha
import com.splanes.toolkit.compose.ui.components.common.utils.color.composite

@Composable
fun GroceryCheckbox(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = bodyStyle { large },
    textColor: Color = palette { onSurface },
    paddingBetween: Dp = dp { tiny },
    color: Color = palette { primary },
    colors: CheckboxColors = groceryCheckboxColors(color),
    onClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = modifier.heightIn(min = BoxSizeMarked),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GroceryCheckboxIndicator(
            selected = selected,
            colors = colors,
            interactionSource = interactionSource,
            onClick = onClick
        )
        Space(dp = paddingBetween)
        Text(
            modifier = modifier.clickable(
                indication = null,
                interactionSource = interactionSource,
                onClick = onClick
            ),
            text = text,
            style = textStyle,
            color = textColor
        )
    }
}

@Composable
fun GroceryCheckboxIndicator(
    selected: Boolean,
    colors: CheckboxColors,
    interactionSource: MutableInteractionSource,
    onClick: () -> Unit
) {
    val checkboxIndicatorSize by Animations.dpAsState(
        condition = selected,
        onTrue = BoxSizeMarked,
        onFalse = BoxSizeUnmarked,
        animSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )
    val shapeBorderSize by Animations.dpAsState(
        condition = selected,
        onTrue = BoxShapeCornerMarked,
        onFalse = BoxShapeCornerUnmarked
    )
    val backgroundColor by Animations.colorAsState(
        condition = selected,
        onTrue = colors.border(selected = true).alpha(.25),
        onFalse = Color.Transparent
    )
    val indication = rememberRipple(
        bounded = false,
        radius = checkboxIndicatorSize + 4.dp,
        color = colors.box(selected = !selected)
    )

    Surface(
        modifier = Modifier.size(checkboxIndicatorSize),
        shape = RoundedCornerShape(shapeBorderSize),
        border = if (!selected) BorderStroke(width = 1.dp, color = colors.border(selected = selected)) else null,
        color = backgroundColor,
        interactionSource = interactionSource,
        indication = indication,
        enabled = true,
        role = Role.Checkbox,
        onClick = onClick
    ) {
        Box(modifier = Modifier.padding(all = dp { smallTiny }), contentAlignment = Alignment.Center) {
            AnimatedVisibility(
                visible = selected,
                enter = expandIn(
                    expandFrom = Alignment.Center,
                    animationSpec = spring(dampingRatio = Spring.DampingRatioHighBouncy)
                ) + fadeIn(animationSpec = Animations.tween { small }),
                exit = fadeOut(animationSpec = Animations.tween { small })
            ) {
                Icons.Icon(
                    source = Icons.rounded { LocalGroceryStore },
                    size = IconSize,
                    color = colors.mark(selected = true)
                )
            }
        }
    }
}

@Composable
fun groceryCheckboxColors(color: Color): CheckboxColors = CheckboxDefaults.colors(
    checkedColor = color,
    uncheckedColor = palette { onSurface.composite(surface, .8) },
    checkmarkColor = color,
    disabledCheckedColor = palette { onSurface }.composite(color, .25),
    disabledUncheckedColor = palette { onSurface.composite(surface, .25) },
    disabledIndeterminateColor = palette { onSurface.composite(surface, .25) }
)

@Composable
fun CheckboxColors.border(enabled: Boolean = true, selected: Boolean) = borderColor(
    enabled = enabled,
    state = ToggleableState(selected)
).value

@Composable
fun CheckboxColors.box(enabled: Boolean = true, selected: Boolean) = boxColor(
    enabled = enabled,
    state = ToggleableState(selected)
).value

@Composable
fun CheckboxColors.mark(selected: Boolean) = checkmarkColor(state = ToggleableState(selected)).value

private val BoxSizeMarked @Composable get() = IconSize + dp { smallTiny }
private val BoxSizeUnmarked by lazy { 22.dp }
private val BoxShapeCornerMarked by lazy { 12.dp }
private val BoxShapeCornerUnmarked by lazy { 5.dp }
private val IconSize by lazy { 30.dp }