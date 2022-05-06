package com.splanes.grocery.ui.component.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.splanes.grocery.ui.component.dialog.utils.border
import com.splanes.grocery.ui.component.dialog.utils.elevation
import com.splanes.grocery.ui.component.icons.Icons
import com.splanes.grocery.ui.component.spacer.row.Space
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
fun DialogButton(button: Dialog.Button, onClick: () -> Unit) = with(button) {
    val buttonColors = colors()
    Button(
        enabled = enabled,
        onClick = onClick,
        shape = shape(size = 12),
        colors = buttonColors,
        elevation = style.elevation(),
        border = style.border(color = buttonColors.border()),
    ) {
        if (leadingIconModel != null) {
            Space { tiny }
            Icons.Icon(
                source = leadingIconModel.source,
                size = leadingIconModel.size.dp,
                color = buttonColors.icon(),
            )
            Space { small }
        }
        Text(
            text = textModel.text,
            style = textModel.style.copy(fontSize = textModel.size.sp),
            color = buttonColors.contentColor(enabled = enabled).value
        )
        if (trailingIconModel != null) {
            Space { small }
            Icons.Icon(
                source = trailingIconModel.source,
                size = trailingIconModel.size.dp,
                color = buttonColors.icon(),
            )
            Space { tiny }
        }
    }
}

