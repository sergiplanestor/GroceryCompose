package com.splanes.grocery.ui.component.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.splanes.grocery.ui.component.button.Buttons
import com.splanes.grocery.ui.component.icons.Icons
import com.splanes.grocery.ui.component.spacer.row.Space
import com.splanes.grocery.ui.utils.resources.dp
import com.splanes.grocery.ui.utils.resources.shape

@Composable
internal fun <T : Dialog.Content> DialogContent(
    uiModel: T,
    content: @Composable T.(BoxScope) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues = uiModel.padding),
        contentAlignment = uiModel.align,
        content = { uiModel.content(this) }
    )
}

@Composable
fun DialogIcon(icon: Dialog.Icon) = DialogContent(uiModel = icon) {
    Icons.Icon(
        source = source,
        size = size.dp,
        color = color
    )
}

@Composable
fun DialogText(title: Dialog.Text) = DialogContent(uiModel = title) {
    Text(
        text = text,
        style = style.copy(fontSize = size.sp),
        textAlign = textAlign,
        color = color
    )
}

@Composable
fun DialogButton(button: Dialog.Button, onDismissRequest: () -> Unit) = with(button) {
    when(style) {
       Dialog.Button.Style.Filled -> {
           Buttons.Fill(
               text = text,
               onClick = {
                   action()
                   onDismissRequest()
               }
           )
       }
        Dialog.Button.Style.Outlined -> {
            Buttons.Fill(
                text = text,
                onClick = {
                    action()
                    onDismissRequest()
                }
            )
        }
        else -> {
            Buttons.Fill(
                text = text,
                onClick = {
                    action()
                    onDismissRequest()
                }
            )
        }
    }

    TextButton(
        enabled = enabled,
        onClick = onClick,
        shape = shape(size = cornerSize),
        colors = colors
    ) {
        if (leadingIconContent != null) {
            Space { tiny }
            leadingIconContent()
            Space { small }
        }
        textContent(text, textStyle, enabled, colors)
        if (trailingIconContent != null) {
            Space { small }
            trailingIconContent()
            Space { tiny }
        }
    }
}

@Composable
fun DialogButtons(button: String, onClick: () -> Unit) {
    Buttons.Fill(
        text = button,
        onClick = onClick
    )
}