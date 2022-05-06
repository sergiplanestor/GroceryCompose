package com.splanes.grocery.ui.component.dialog.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.splanes.grocery.ui.component.dialog.Dialog
import com.splanes.grocery.ui.utils.resources.Zero
import com.splanes.grocery.ui.utils.resources.palette
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha

fun Dialog.Button.Companion.styleOfType(type: Dialog.Button.Type): Dialog.Button.Style =
    when (type) {
        Dialog.Button.Type.Positive -> Dialog.Button.Style.Filled
        Dialog.Button.Type.Negative -> Dialog.Button.Style.Flat
        Dialog.Button.Type.Neutral -> Dialog.Button.Style.Flat
    }

@Composable
fun Dialog.Button.Companion.colorsOfStyle(style: Dialog.Button.Style): ButtonColors =
    when (style) {
        Dialog.Button.Style.Filled -> palette { primary } to palette { onPrimary }
        Dialog.Button.Style.Outlined -> Color.Transparent to palette { primary }
        Dialog.Button.Style.Flat -> palette { primaryContainer }.alpha(.12) to palette { onPrimaryContainer }
    }.let { (container, content) ->
        ButtonDefaults.buttonColors(
            containerColor = container,
            contentColor = content,
            disabledContainerColor = container.alpha(.12),
            disabledContentColor = content.alpha(.38)
        )
    }

@Composable
internal fun Dialog.Button.Style.elevation(): ButtonElevation =
    when(this) {
        Dialog.Button.Style.Filled -> ButtonDefaults.filledTonalButtonElevation()
        Dialog.Button.Style.Outlined -> ButtonDefaults.elevatedButtonElevation()
        Dialog.Button.Style.Flat -> ButtonDefaults.buttonElevation(Dp.Zero, Dp.Zero, Dp.Zero, Dp.Zero, Dp.Zero)
    }

@Composable
internal fun Dialog.Button.Style.border(color: Color): BorderStroke? =
    takeIf { this == Dialog.Button.Style.Outlined }?.let {
        BorderStroke(width = 1.dp, color = color)
    }

fun List<Dialog.Button>.isCustomContainer(): Boolean =
    size > 2 || filterNot { button -> button.isPositive() || button.isNegative() }.isNotEmpty()

fun Dialog.Button.isPositive(): Boolean =
    this.type == Dialog.Button.Type.Positive

fun Dialog.Button.isNegative(): Boolean =
    this.type == Dialog.Button.Type.Negative

fun Dialog.Button.isNeutral(): Boolean =
    this.type == Dialog.Button.Type.Neutral

fun Dialog.UiModel.doThenDismiss(block: () -> Unit) {
    block().also { onDismissRequest() }
}
