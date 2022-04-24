package com.splanes.grocery.ui.component.dialog.utils

import androidx.compose.ui.window.DialogProperties
import com.splanes.grocery.ui.component.dialog.Dialog
import com.splanes.grocery.ui.component.dialog.Dialog.Dismiss
import com.splanes.grocery.ui.component.dialog.Dialog.Dismiss.Trigger
import com.splanes.grocery.ui.component.dialog.Dialog.Dismiss.Trigger.Companion
import com.splanes.grocery.ui.component.dialog.Dialog.Dismiss.Trigger.OnBack
import com.splanes.grocery.ui.component.dialog.Dialog.Dismiss.Trigger.OnOutside

val Companion.All: List<Trigger>
    get() = Trigger.values().toList()

val Companion.None: List<Trigger>
    get() = emptyList()

fun Dismiss.Companion.triggers(isCancellable: Boolean): List<Trigger> =
    if (isCancellable) Trigger.All else Trigger.None

fun List<Trigger>.containsOnBack(): Boolean =
    contains(OnBack)

fun List<Trigger>.containsOnOutside(): Boolean =
    contains(OnOutside)

fun DialogProperties.dismissTriggers(): List<Trigger> = buildList {
    if (dismissOnBackPress) add(OnBack)
    if (dismissOnClickOutside) add(OnOutside)
}

fun Dialog.UiModel.dialogProperties(): DialogProperties = DialogProperties(
    dismissOnBackPress = dismiss.triggers.containsOnBack(),
    dismissOnClickOutside = dismiss.triggers.containsOnOutside(),
    securePolicy = props.security
)