package com.splanes.grocery.ui.component.dialog

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.SecureFlagPolicy
import com.splanes.grocery.ui.component.dialog.utils.All
import com.splanes.grocery.ui.component.dialog.utils.styleOfType
import com.splanes.grocery.ui.component.dialog.utils.triggers
import com.splanes.grocery.ui.component.icons.Icons
import com.splanes.toolkit.compose.ui.theme.feature.colors.ThemeColorScheme

object Dialog {

    sealed class UiModel(
        open val dismiss: Dismiss = Dismiss(),
        open val icon: Icon? = null,
        open val title: Text? = null,
        open val body: Text? = null,
        open val buttonPositive: Button? = null,
        open val buttonNegative: Button? = null,
        open val buttonNeutrals: List<Button> = emptyList(),
        open val props: Properties = Properties()
    ) {
        object Empty : UiModel()
    }

    data class SimpleUiModel(
        val onRequestDismiss: () -> Unit = {},
        override val buttonPositive: Button,
        val isCancellable: Boolean = true,
        override val buttonNegative: Button? = null,
        override val icon: Icon? = null,
        override val title: Text? = null,
        override val body: Text? = null
    ) : UiModel(
        dismiss = Dismiss(requestDismiss = onRequestDismiss, triggers = Dismiss.triggers(isCancellable)),
        icon = icon,
        title = title,
        body = body,
        buttonPositive = buttonPositive,
        buttonNegative = buttonNegative,
        props = Properties()
    )

    data class Properties(
        val shape: Shape = RoundedCornerShape(size = 16.dp),
        val containerColor: ThemeColorScheme.() -> Color = { surface },
        val security: SecureFlagPolicy = SecureFlagPolicy.Inherit,
        val elevation: Dp = 6.dp
    )

    data class Dismiss(val requestDismiss: () -> Unit = {}, val triggers: List<Trigger> = Trigger.All) {
        enum class Trigger {
            OnBack, OnOutside;

            companion object
        }

        companion object
    }

    interface Content {
        val align: Alignment
        val color: Color
        val size: Int
        val padding: PaddingValues
    }

    data class Icon(
        val source: Icons.Source,
        override val align: Alignment = Alignment.Center,
        override val color: Color,
        override val size: Int,
        override val padding: PaddingValues
    ) : Content {
        companion object
    }

    data class Text(
        val text: String,
        val style: TextStyle,
        val textAlign: TextAlign,
        override val align: Alignment,
        override val color: Color,
        override val size: Int = style.fontSize.value.toInt(),
        override val padding: PaddingValues
    ) : Content {
        companion object
    }

    data class Button(
        val text: Dialog.Text,
        val type: Type,
        val enabled: Boolean = true,
        val leadingIcon: Icon? = null,
        val trailingIcon: Icon? = null,
        val style: Style = styleOfType(type),
        val action: () -> Unit
    ) {
        enum class Type { Positive, Negative, Neutral }
        enum class Style {
            Filled, Outlined, Flat;

            companion object
        }

        companion object
    }
}

