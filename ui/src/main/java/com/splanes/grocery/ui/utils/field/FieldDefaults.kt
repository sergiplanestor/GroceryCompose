package com.splanes.grocery.ui.utils.field

import androidx.compose.animation.AnimatedVisibility
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
import com.splanes.grocery.ui.utils.resources.body
import com.splanes.grocery.ui.utils.resources.color
import com.splanes.grocery.ui.utils.resources.shape
import com.splanes.grocery.ui.utils.resources.title
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
        textColor: Color = color { onSurface },
        disabledTextColor: Color = color { onSurface }.alpha(ContentAlpha.disabled),
        cursorColor: Color = color { primary }.alpha(.8),
        errorCursorColor: Color = color { error.composite(surface, .7) },
        leadingIconColor: Color = color {
            primary.composite(
                surface,
                alpha = TextFieldDefaults.IconOpacity
            )
        },
        disabledLeadingIconColor: Color = leadingIconColor.copy(alpha = ContentAlpha.disabled),
        errorLeadingIconColor: Color = color {
            error.composite(
                surface,
                alpha = TextFieldDefaults.IconOpacity
            )
        },
        trailingIconColor: Color = color { onSurface }.alpha(alpha = TextFieldDefaults.IconOpacity),
        disabledTrailingIconColor: Color = trailingIconColor.copy(alpha = ContentAlpha.disabled),
        errorTrailingIconColor: Color = trailingIconColor,
        focusedLabelColor: Color = color { primary }.alpha(.8),
        unfocusedLabelColor: Color = color { onSurface }.alpha(ContentAlpha.medium),
        disabledLabelColor: Color = unfocusedLabelColor.copy(ContentAlpha.disabled),
        errorLabelColor: Color = MaterialTheme.colors.error,
        placeholderColor: Color = textColor.alpha(ContentAlpha.medium),
        disabledPlaceholderColor: Color = textColor.alpha(ContentAlpha.disabled)
    ): TextFieldColors = TextFieldDefaults.textFieldColors(
        textColor = textColor,
        disabledTextColor = disabledTextColor,
        cursorColor = cursorColor,
        errorCursorColor = errorCursorColor,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        errorIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
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
    fun Label(
        text: String,
        modifier: Modifier = Modifier
    ) {
        with(body { medium }) {
            Text(
                modifier = modifier,
                text = text,
                fontSize = fontSize,
                fontFamily = fontFamily,
                fontWeight = fontWeight
            )
        }
    }

    @Composable
    fun Placeholder(
        text: String,
        modifier: Modifier = Modifier
    ) {
        with(title { medium }) {
            Text(
                modifier = modifier,
                text = text,
                fontSize = fontSize,
                fontFamily = fontFamily,
                fontWeight = fontWeight
            )
        }
    }

    @Composable
    fun Icon(
        imageVector: ImageVector,
        modifier: Modifier = Modifier,
        contentDescription: String? = null,
        size: Int = 24
    ) {
        androidx.compose.material3.Icon(
            modifier = modifier.size(size = size.dp),
            imageVector = imageVector,
            contentDescription = contentDescription
        )
    }

    @Composable
    fun IconClear(
        visible: Boolean,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        imageVector: ImageVector = Icons.Rounded.Backspace,
        size: Int = 24
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(duration = AnimDefaults.DurationMedium.toInt()),
            exit = fadeOut(duration = AnimDefaults.DurationMedium.toInt()),
        ) {
            IconButton(modifier = modifier, onClick = onClick) {
                Icon(
                    size = size,
                    imageVector = imageVector,
                    contentDescription = "Clear field"
                )
            }
        }
    }
}

