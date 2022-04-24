package com.splanes.grocery.ui.feature.mainscreen.component.subcomponent.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.splanes.grocery.ui.component.button.Buttons
import com.splanes.grocery.ui.component.dialog.Dialog
import com.splanes.grocery.ui.component.icons.Icons
import com.splanes.grocery.ui.utils.resources.Strings
import com.splanes.grocery.ui.utils.resources.dp
import com.splanes.grocery.ui.utils.resources.headlineStyle
import com.splanes.grocery.ui.utils.resources.palette
import com.splanes.grocery.ui.utils.resources.shape
import com.splanes.grocery.ui.utils.resources.string
import com.splanes.grocery.ui.utils.resources.titleStyle

@Composable
fun DialogInfo(
    icon: Icons.Source? = null,
    iconColor: Color = palette { onSurface },
    title: String? = null,
    titleColor: Color = palette { onSurface },
    body: String? = null,
    bodyColor: Color = palette { onSurface },
    button: String = string { Strings.gotcha },
    dismissProps: Dialog.DismissProps = Dialog.DismissProps.Always,
    onPositiveClick: () -> Boolean = { false },
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        icon = icon?.let { { DialogIcon(icon, iconColor) } },
        title = title?.let { { DialogTitle(title, titleColor) } },
        text = body?.let { { DialogBody(body, bodyColor) } },
        shape = shape(size = 24),
        containerColor = palette { surface },
        properties = dismissProps.toDialogProperties(),
        confirmButton = { DialogPositiveButton(button) { if (!onPositiveClick()) onDismissRequest() } },
        onDismissRequest = onDismissRequest
    )
}

@Composable
fun DialogIcon(icon: Icons.Source, color: Color) {
    Box(
        modifier = Modifier
            .wrapContentSize(align = Alignment.Center)
            .padding(horizontal = dp { medium }, vertical = dp { small })
    ) {
        Icons.Icon(
            source = icon,
            size = 40.dp,
            color = color
        )
    }
}

@Composable
fun DialogTitle(title: String, color: Color) {
    Row(
        modifier = Modifier
            .wrapContentSize(align = Alignment.Center)
            .padding(all = dp { medium })
    ) {
        Text(
            text = title,
            style = headlineStyle { small },
            color = color
        )
    }
}

@Composable
fun DialogBody(body: String, color: Color) {
    Row(
        modifier = Modifier
            .wrapContentSize(align = Alignment.Center)
            .padding(horizontal = dp { medium }, vertical = dp { small })
    ) {
        Text(
            text = body,
            style = titleStyle { small },
            color = color
        )
    }
}

@Composable
fun DialogPositiveButton(button: String, onClick: () -> Unit) {
    Buttons.Fill(
        text = button,
        onClick = onClick
    )
}