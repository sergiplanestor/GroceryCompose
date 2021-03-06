package com.splanes.grocery.ui.component.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import com.splanes.grocery.ui.component.dialog.utils.dialogProperties
import com.splanes.grocery.ui.utils.resources.palette

@Composable
fun DialogSimple(uiModel: Dialog.SimpleUiModel) {
    with(uiModel) {
        AlertDialog(
            icon = icon?.let { { DialogIcon(icon) } },
            title = title?.let { { DialogText(title) } },
            text = body?.let { { DialogText(body) } },
            shape = props.shape,
            containerColor = palette { props.containerColor(this) },
            tonalElevation = props.elevation,
            properties = dialogProperties(),
            confirmButton = { DialogButton(buttonPositive, onDismissRequest = { dismiss.requestDismiss() }) },
            dismissButton = buttonNegative?.let {
                { DialogButton(buttonNegative, onDismissRequest = { dismiss.requestDismiss() }) }
            },
            onDismissRequest = { dismiss.requestDismiss() }
        )
    }
}