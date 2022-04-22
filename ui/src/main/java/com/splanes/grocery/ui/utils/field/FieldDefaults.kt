package com.splanes.grocery.ui.utils.field

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Backspace
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.splanes.grocery.ui.utils.anim.AnimDefaults
import com.splanes.grocery.ui.utils.resources.alpha
import com.splanes.grocery.ui.utils.resources.bodyStyle
import com.splanes.grocery.ui.utils.resources.palette
import com.splanes.grocery.ui.utils.resources.shape
import com.splanes.grocery.ui.utils.resources.titleStyle
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha
import com.splanes.toolkit.compose.ui.components.common.utils.color.composite
import com.splanes.toolkit.compose.ui.components.feature.navhost.graph.transition.fadeIn
import com.splanes.toolkit.compose.ui.components.feature.navhost.graph.transition.fadeOut

object FieldDefaults {

    fun keyboardOption(
        fieldType: FieldType,
        action: ImeAction
    ): KeyboardOptions =
        KeyboardOptions(
            capitalization = fieldType.caps,
            keyboardType = fieldType.keyboardType,
            imeAction = action
        )

    @Composable
    fun shapeOutlined(): Shape = shape { medium }

    @Composable
    fun colorsOutlined(
        textColor: Color = palette { onSurface },
        unfocusedBorderColor: Color = palette { onSurface },
        focusedBorderColor: Color = palette { primary },
        disabledBorderColor: Color = unfocusedBorderColor.alpha(ContentAlpha.disabled),
        errorBorderColor: Color = palette { error },
        disabledTextColor: Color = palette { onSurface }.alpha(ContentAlpha.disabled),
        cursorColor: Color = palette { primary }.alpha(.8),
        errorCursorColor: Color = palette { error.composite(surface, .7) },
        leadingIconColor: Color = palette {
            primary.composite(
                surface,
                alpha = TextFieldDefaults.IconOpacity
            )
        },
        disabledLeadingIconColor: Color = leadingIconColor.copy(alpha = ContentAlpha.disabled),
        errorLeadingIconColor: Color = palette {
            error.composite(
                surface,
                alpha = TextFieldDefaults.IconOpacity
            )
        },
        trailingIconColor: Color = palette { onSurface }.alpha(alpha = TextFieldDefaults.IconOpacity),
        disabledTrailingIconColor: Color = trailingIconColor.copy(alpha = ContentAlpha.disabled),
        errorTrailingIconColor: Color = trailingIconColor,
        focusedLabelColor: Color = palette { primary }.alpha(.8),
        unfocusedLabelColor: Color = palette { onSurface }.alpha(ContentAlpha.medium),
        disabledLabelColor: Color = unfocusedLabelColor.copy(ContentAlpha.disabled),
        errorLabelColor: Color = MaterialTheme.colors.error,
        placeholderColor: Color = textColor.alpha(ContentAlpha.medium),
        disabledPlaceholderColor: Color = textColor.alpha(ContentAlpha.disabled)
    ): TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        textColor = textColor,
        disabledTextColor = disabledTextColor,
        cursorColor = cursorColor,
        errorCursorColor = errorCursorColor,
        focusedBorderColor = focusedBorderColor,
        unfocusedBorderColor = unfocusedBorderColor,
        errorBorderColor = errorBorderColor,
        disabledBorderColor = disabledBorderColor,
        leadingIconColor = leadingIconColor,
        disabledLeadingIconColor = disabledLeadingIconColor,
        errorLeadingIconColor = errorLeadingIconColor,
        trailingIconColor = trailingIconColor,
        disabledTrailingIconColor = disabledTrailingIconColor,
        errorTrailingIconColor = errorTrailingIconColor,
        backgroundColor = Color.Transparent,
        focusedLabelColor = focusedLabelColor,
        unfocusedLabelColor = unfocusedLabelColor,
        disabledLabelColor = disabledLabelColor,
        errorLabelColor = errorLabelColor,
        placeholderColor = placeholderColor,
        disabledPlaceholderColor = disabledPlaceholderColor
    )

    @Composable
    fun colorsOutlined(
        textColor: Color,
        errorColor: Color,
        borderFocusedColor: Color = textColor,
        borderUnfocusedColor: Color = borderFocusedColor.alpha { low },
        cursorColor: Color = textColor,
        trailingIconColor: Color = borderFocusedColor.alpha { medium },
        trailingIconErrorColor: Color = errorColor.alpha { medium },
        leadingIconColor: Color = borderFocusedColor.alpha { medium },
        leadingIconErrorColor: Color = errorColor.alpha { medium },
    ): TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        textColor = textColor,
        disabledTextColor = textColor.alpha { disabled },
        focusedBorderColor = borderFocusedColor,
        unfocusedBorderColor = borderUnfocusedColor,
        disabledBorderColor = borderUnfocusedColor.alpha { disabled },
        errorBorderColor = errorColor,
        cursorColor = cursorColor,
        errorCursorColor = errorColor,
        backgroundColor = Color.Transparent,
        leadingIconColor = leadingIconColor,
        disabledLeadingIconColor = leadingIconColor.alpha { disabled },
        errorLeadingIconColor = leadingIconErrorColor,
        trailingIconColor = trailingIconColor,
        disabledTrailingIconColor = trailingIconColor.alpha { disabled },
        errorTrailingIconColor = trailingIconErrorColor,
        focusedLabelColor = borderFocusedColor,
        unfocusedLabelColor = borderFocusedColor.alpha { medium },
        disabledLabelColor = borderUnfocusedColor.alpha { disabled },
        errorLabelColor = errorColor,
        placeholderColor = textColor,
        disabledPlaceholderColor = textColor.alpha { disabled },
    )

    @Composable
    fun Label(
        text: String,
        colors: TextFieldColors,
        error: Boolean,
        focused: Boolean,
        modifier: Modifier = Modifier,
        enabled: Boolean = true
    ) {
        Text(
            modifier = modifier,
            text = text,
            style = bodyStyle { medium },
            color = colors.labelColor(
                enabled = enabled,
                error = error,
                interactionSource = MutableInteractionSource()
            ).value.alpha { if (focused) high else medium }
        )
    }

    @Composable
    fun Placeholder(
        text: String,
        colors: TextFieldColors,
        modifier: Modifier = Modifier,
        enabled: Boolean = true
    ) {
        Text(
            modifier = modifier,
            text = text,
            style = titleStyle { medium },
            color = colors.placeholderColor(enabled = enabled).value
        )
    }

    @Composable
    fun Icon(
        iconType: FieldIcon,
        imageVector: ImageVector,
        focused: Boolean,
        colors: TextFieldColors,
        modifier: Modifier = Modifier,
        contentDescription: String? = null,
        size: Int = 20,
    ) {
        androidx.compose.material3.Icon(
            modifier = modifier.size(size = size.dp),
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = when (iconType) {
                FieldIcon.Leading -> colors.leadingIconColor(
                    enabled = true,
                    isError = false
                ).value.alpha(if (focused) .7 else .45)
                FieldIcon.Trailing -> colors.trailingIconColor(
                    enabled = true,
                    isError = false
                ).value.alpha(if (focused) .7 else .45)
            }
        )
    }

    @Composable
    fun IconClear(
        visible: Boolean,
        onClick: () -> Unit,
        colors: TextFieldColors,
        modifier: Modifier = Modifier,
        imageVector: ImageVector = Icons.Rounded.Backspace,
        size: Int = 20
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(duration = AnimDefaults.DurationMedium.toInt()),
            exit = fadeOut(duration = AnimDefaults.DurationMedium.toInt()),
        ) {
            IconButton(modifier = modifier, onClick = onClick) {
                Icon(
                    iconType = FieldIcon.Trailing,
                    focused = true,
                    colors = colors,
                    size = size,
                    imageVector = imageVector,
                    contentDescription = "Clear field"
                )
            }
        }
    }

    enum class FieldIcon {
        Leading, Trailing,
    }
}

