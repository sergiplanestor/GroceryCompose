package com.splanes.grocery.ui.component.dialog.utils

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.splanes.grocery.ui.component.dialog.Dialog
import com.splanes.grocery.ui.component.icons.Icons
import com.splanes.grocery.ui.component.icons.painter
import com.splanes.grocery.ui.utils.resources.Drawables
import com.splanes.grocery.ui.utils.resources.displayStyle
import com.splanes.grocery.ui.utils.resources.palette
import com.splanes.grocery.ui.utils.resources.titleStyle
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha

val Dialog.Icon.Companion.Size: Int
    get() = 32

val Dialog.Text.Companion.TitleStyle: TextStyle
    @Composable
    get() = displayStyle { small }

val Dialog.Text.Companion.BodyStyle: TextStyle
    @Composable
    get() = titleStyle()

@SuppressLint("ComposableNaming")
@Composable
fun Dialog.Text.Title(
    text: String,
    style: TextStyle = Dialog.Text.TitleStyle,
    textAlign: TextAlign = TextAlign.Center,
    align: Alignment = Alignment.Center,
    color: Color,
    size: Int = style.fontSize.value.toInt(),
    padding: PaddingValues
): Dialog.Text = Dialog.Text(
    text = text,
    style = style,
    textAlign = textAlign,
    align = align,
    color = color,
    size = size,
    padding = padding,
)

@SuppressLint("ComposableNaming")
@Composable
fun Dialog.Text.Body(
    text: String,
    style: TextStyle = Dialog.Text.BodyStyle,
    textAlign: TextAlign = TextAlign.Justify,
    align: Alignment = Alignment.CenterStart,
    color: Color,
    size: Int = style.fontSize.value.toInt(),
    padding: PaddingValues
): Dialog.Text = Dialog.Text(
    text = text,
    style = style,
    textAlign = textAlign,
    align = align,
    color = color,
    size = size,
    padding = padding,
)

@SuppressLint("ComposableNaming")
@Composable
fun Dialog.Icon.Companion.Warning(
    source: Icons.Source = Icons.painter(Drawables.ic_error),
    align: Alignment = Alignment.Center,
    color: Color = palette { error.alpha(.75) },
    size: Int = Dialog.Icon.Size,
    padding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 24.dp)
): Dialog.Icon = Dialog.Icon(
    source = source,
    align = align,
    color = color,
    size = size,
    padding = padding
)

@SuppressLint("ComposableNaming")
@Composable
fun Dialog.Icon.Companion.Info(
    source: Icons.Source = Icons.painter(Drawables.ic_info),
    align: Alignment = Alignment.Center,
    color: Color = palette { info.alpha(.75) },
    size: Int = Dialog.Icon.Size,
    padding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 24.dp)
): Dialog.Icon = Dialog.Icon(
    source = source,
    align = align,
    color = color,
    size = size,
    padding = padding
)