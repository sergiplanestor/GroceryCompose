package com.splanes.grocery.ui.component.dialog

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.splanes.grocery.ui.component.icons.Icons
import com.splanes.grocery.ui.component.icons.painter
import com.splanes.grocery.ui.utils.resources.Drawables
import com.splanes.grocery.ui.utils.resources.displayStyle
import com.splanes.grocery.ui.utils.resources.palette
import com.splanes.grocery.ui.utils.resources.string
import com.splanes.grocery.ui.utils.resources.titleStyle
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha

object Dialogs {

    sealed class UiModel(
        open val dismiss: Dismiss,
        open val icon: Icon? = null,
        open val title: Title? = null,
        open val body: Body? = null,
        open val positiveButtons: Button,
        open val otherButtons: List<Button> = emptyList(),
    )

    data class SimpleUiModel(
        val onDismiss: () -> Unit = {},
        override val dismiss: Dismiss = Dismiss(),
        override val icon: Icon? = null,
        override val title: Title? = null,
        override val body: Body? = null,
        override val positiveButtons: Button,
        override val otherButtons: List<Button> = emptyList(),
    ) : UiModel(
        dismiss = Dismiss(action = onDismiss, triggers = Dismiss.Trigger.All),
        icon = icon,
        title = title,
        body = body,
        positiveButtons = positiveButtons,
        otherButtons = otherButtons,
    )

    data class Dismiss(val action: () -> Unit = {}, val triggers: List<Trigger> = Trigger.All) {
        enum class Trigger {
            OnBack, OnOutside;

            companion object
        }
        companion object
    }

    interface Content {
        val align: Alignment.Horizontal
        val color: Color
        val size: Int
        val padding: PaddingValues
    }

    data class Icon(
        val source: Icons.Source,
        override val align: Alignment.Horizontal = Alignment.CenterHorizontally,
        override val color: Color,
        override val size: Int,
        override val padding: PaddingValues
    ) : Content {
        companion object
    }

    data class Title(
        val text: String,
        val style: TextStyle,
        val textAlign: TextAlign = TextAlign.Center,
        override val align: Alignment.Horizontal = Alignment.CenterHorizontally,
        override val color: Color,
        override val size: Int = style.fontSize.value.toInt(),
        override val padding: PaddingValues
    ) : Content {
        companion object
    }

    data class Body(
        val text: String,
        val style: TextStyle,
        val textAlign: TextAlign = TextAlign.Justify,
        override val align: Alignment.Horizontal = Alignment.Start,
        override val color: Color,
        override val size: Int = style.fontSize.value.toInt(),
        override val padding: PaddingValues
    ) : Content {
        companion object
    }

    data class Button(
        val text: String,
        val type: Type,
        val style: Style = styleOfType(type),
        val action: () -> Unit
    ) {
        enum class Type { Positive, Negative, Neutral, Undefined }
        enum class Style {
            Filled, Outlined, Flat, Undefined;

            companion object
        }

        companion object
    }
}

val Dialogs.Title.Companion.Style: TextStyle @Composable get() = displayStyle { small }

val Dialogs.Body.Companion.Style: TextStyle @Composable get() = titleStyle()

@SuppressLint("ComposableNaming")
@Composable
fun Dialogs.Button.Companion.Primary(
    text: () -> Int,
    type: Dialogs.Button.Type = Dialogs.Button.Type.Positive,
    style: Dialogs.Button.Style = Dialogs.Button.styleOfType(type),
    onClick: () -> Unit
): Dialogs.Button =
    Dialogs.Button(
        text = string { text() },
        type = type,
        style = style,
        action = onClick
    )

val Dialogs.Icon.Companion.Size: Int get() = 32

@SuppressLint("ComposableNaming")
@Composable
fun Dialogs.Icon.Companion.Warning(
    source: Icons.Source = Icons.painter(Drawables.ic_error),
    align: Alignment.Horizontal = Alignment.CenterHorizontally,
    color: Color = palette { error.alpha(.75) },
    size: Int = Dialogs.Icon.Size,
    padding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 24.dp)
): Dialogs.Icon = Dialogs.Icon(
    source = source,
    align = align,
    color = color,
    size = size,
    padding = padding
)

@SuppressLint("ComposableNaming")
@Composable
fun Dialogs.Icon.Companion.Info(
    source: Icons.Source = Icons.painter(Drawables.ic_info),
    align: Alignment.Horizontal = Alignment.CenterHorizontally,
    color: Color = palette { info.alpha(.75) },
    size: Int = Dialogs.Icon.Size,
    padding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 24.dp)
): Dialogs.Icon = Dialogs.Icon(
    source = source,
    align = align,
    color = color,
    size = size,
    padding = padding
)

fun Dialogs.Dismiss.Companion.Cancellable(action: () -> Unit): Dialogs.Dismiss = Dialogs.Dismiss(
    action = action,
    triggers = Dialogs.Dismiss.Trigger.All
)

val Dialogs.Dismiss.Trigger.Companion.All: List<Dialogs.Dismiss.Trigger>
    get() =
        Dialogs.Dismiss.Trigger.values().toList()

val Dialogs.Dismiss.Trigger.Companion.None: List<Dialogs.Dismiss.Trigger>
    get() =
        emptyList()

fun List<Dialogs.Dismiss.Trigger>.mapToProperties(): DialogProperties =
    DialogProperties(
        dismissOnBackPress = contains(Dialogs.Dismiss.Trigger.OnBack),
        dismissOnClickOutside = contains(Dialogs.Dismiss.Trigger.OnOutside)
    )

fun DialogProperties.dismissTriggers(): List<Dialogs.Dismiss.Trigger> = buildList {
    if (dismissOnBackPress) add(Dialogs.Dismiss.Trigger.OnBack)
    if (dismissOnClickOutside) add(Dialogs.Dismiss.Trigger.OnOutside)
}

fun Dialogs.Button.Companion.styleOfType(type: Dialogs.Button.Type): Dialogs.Button.Style =
    when (type) {
        Dialogs.Button.Type.Positive -> Dialogs.Button.Style.Filled
        Dialogs.Button.Type.Negative -> Dialogs.Button.Style.Flat
        Dialogs.Button.Type.Neutral -> Dialogs.Button.Style.Flat
        Dialogs.Button.Type.Undefined -> Dialogs.Button.Style.Undefined
    }