package com.splanes.grocery.ui.component.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.splanes.grocery.ui.component.icons.Icons
import com.splanes.grocery.ui.utils.resources.palette
import com.splanes.grocery.ui.utils.resources.string

object Dialog {

    @Composable
    fun Informative(
        icon: Icons.Source? = null,
        iconColor: Color = palette { onSurface },
        title: String? = null,
        titleColor: Color = palette { onSurface },
        body: String? = null,
        bodyColor: Color = palette { onSurface },
        button: String = string { Strings.gotcha },
        dismissProps: Dialogs.DismissProps = Dialogs.DismissProps.Always,
        onPositiveClick: () -> Boolean = { false },
        onDismissRequest: () -> Unit
    )

}