package com.splanes.grocery.ui.component.button

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.splanes.grocery.ui.component.spacer.row.Space
import com.splanes.grocery.ui.utils.resources.alpha
import com.splanes.grocery.ui.utils.resources.palette
import com.splanes.grocery.ui.utils.resources.dp
import com.splanes.grocery.ui.utils.resources.shape
import com.splanes.grocery.ui.utils.resources.titleStyle

object Buttons {

    @Composable
    fun Fill(
        text: String,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        textStyle: TextStyle = titleStyle { small },
        textColor: Color = palette { onPrimary },
        backgroundColor: Color = palette { primary },
        colors: ButtonColors = ButtonDefaults.textButtonColors(
            contentColor = textColor,
            containerColor = backgroundColor,
            disabledContentColor = textColor.alpha { disabled },
            disabledContainerColor = backgroundColor.alpha { disabled },
        ),
        cornerSize: Int = 10,
        leadingIconContent: (@Composable RowScope.() -> Unit)? = null,
        trailingIconContent: (@Composable RowScope.() -> Unit)? = null,
        textContent: @Composable RowScope.(
            String,
            TextStyle,
            Boolean,
            ButtonColors
        ) -> Unit = { textContentText, textContentTextStyle, textContentEnabled, textContentColors ->
            TextContent(
                textContentText,
                textContentTextStyle,
                textContentEnabled,
                textContentColors
            )
        }
    ) {
        TextButton(
            modifier = modifier.padding(vertical = dp { tiny }),
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
    fun TextContent(
        text: String,
        textStyle: TextStyle,
        enabled: Boolean,
        colors: ButtonColors
    ) {
        Text(
            text = text,
            color = colors.contentColor(enabled = enabled).value,
            style = textStyle
        )
    }

}