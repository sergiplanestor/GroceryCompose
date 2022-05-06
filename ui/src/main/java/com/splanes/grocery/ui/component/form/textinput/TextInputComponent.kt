package com.splanes.grocery.ui.component.form.textinput

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import com.splanes.grocery.ui.component.form.Forms
import com.splanes.grocery.ui.component.form.Forms.IconPosition
import com.splanes.grocery.ui.component.form.textinput.utils.icon
import com.splanes.grocery.ui.component.form.textinput.utils.label
import com.splanes.grocery.ui.component.form.textinput.utils.placeholder
import com.splanes.grocery.ui.component.form.textinput.utils.shape
import com.splanes.grocery.ui.component.form.textinput.utils.toKeyboardOptions
import com.splanes.grocery.ui.component.form.utils.isError
import com.splanes.grocery.ui.component.form.utils.resultOf
import com.splanes.grocery.ui.component.form.utils.update
import com.splanes.grocery.ui.component.form.utils.updateUiState
import com.splanes.grocery.ui.component.icons.Icons
import com.splanes.grocery.ui.component.icons.rounded
import com.splanes.grocery.ui.utils.anim.Animations
import com.splanes.grocery.ui.utils.anim.Animations.tween
import com.splanes.grocery.ui.utils.resources.alpha
import com.splanes.grocery.ui.utils.resources.bodyStyle
import com.splanes.grocery.ui.utils.resources.disabled
import com.splanes.grocery.ui.utils.resources.dp
import com.splanes.grocery.ui.utils.resources.labelStyle
import com.splanes.grocery.ui.utils.resources.over
import com.splanes.grocery.ui.utils.resources.palette
import com.splanes.grocery.ui.utils.resources.string
import com.splanes.grocery.ui.utils.resources.titleStyle

@Composable
fun TextInput(
    uiModel: Forms.TextInputUiModel,
    label: String,
    onValueChange: (Forms.State<String?>) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    leadingIcon: Icons.Source? = null,
    trailingIcon: Icons.Source? = null,
    onFocusChange: (focused: Boolean) -> Unit = {}
) {
    val uiState = uiModel.uiState
    val enabled = uiState.enabled
    val error = uiState.isError()
    val focused = uiState.focused
    val onInputChanged: (String) -> Unit = { input ->
        val result = Forms.Validator.resultOf(uiModel.validators, input)
        onValueChange(result.updateUiState(uiState).update(value = input))
    }
    val focusRequester by remember { mutableStateOf(FocusRequester()) }

    with(uiModel) {
        val trailingIconSource = trailingIcon ?: Icons.rounded { Cancel }.takeIf { trailingClearText }
        Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .focusRequester(focusRequester)
                    .onFocusEvent { focusState -> onFocusChange(focusState.hasFocus) },
                value = uiState.value.orEmpty(),
                onValueChange = onInputChanged,
                colors = colors,
                textStyle = bodyStyle { medium },
                shape = inputStyle.shape(),
                label = {
                    TextInputLabel(
                        label = label,
                        color = colors.label(enabled = enabled, error = error, focused = focused),
                        error = error,
                        focused = focused,
                        enabled = enabled
                    )
                },
                singleLine = singleLine,
                maxLines = maxLines,
                leadingIcon = leadingIcon?.let {
                    {
                        TextInputIcon(
                            position = IconPosition.Leading,
                            source = leadingIcon,
                            focused = focused,
                            color = colors.icon(position = IconPosition.Leading, enabled = enabled, error = error)
                        )
                    }
                },
                trailingIcon = trailingIconSource?.let {
                    {
                        TextInputIcon(
                            position = IconPosition.Trailing,
                            source = trailingIconSource,
                            focused = focused,
                            color = colors.icon(position = IconPosition.Trailing, enabled = enabled, error = error),
                            visible = !trailingClearText || uiState.isTrailingClearTextVisible(),
                            onClick = { onInputChanged("") }.takeIf { trailingClearText }
                        )
                    }
                },
                isError = uiState.isError(),
                keyboardOptions = keyboard.toKeyboardOptions(),
                keyboardActions = keyboardActions,
            )
            AnimatedVisibility(
                visible = uiState.isErrorMessageVisible(),
                enter = fadeIn(animationSpec = tween { mediumSmall }) +
                        expandVertically(animationSpec = tween { small }),
                exit = fadeOut(animationSpec = tween { mediumSmall }) +
                        shrinkVertically(animationSpec = tween { small }),
            ) {
                (uiState as? Forms.Error)?.message?.let { text ->
                    Text(
                        modifier = Modifier.padding(vertical = dp { small }),
                        text = string { text },
                        style = labelStyle { medium },
                        color = colors.textColor(enabled = true).value.alpha { medium }
                    )
                }
            }
        }
    }

    if (!focused) {
        focusRequester.freeFocus()
    }
}

@Composable
fun TextInputLabel(
    label: String,
    color: Color,
    error: Boolean,
    focused: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val style = bodyStyle()
    val weight by Animations.floatAsState(
        condition = focused,
        onTrue = (style.fontWeight ?: FontWeight.Normal).weight.toDouble() + 50,
        onFalse = (style.fontWeight?.weight ?: FontWeight.Normal.weight).toDouble()
    )
    Text(
        modifier = modifier,
        text = label,
        style = bodyStyle(),
        color = color,
        fontWeight = FontWeight(weight = weight.toInt())
    )
}

@Composable
fun TextInputPlaceholder(
    text: String,
    colors: TextFieldColors,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Text(
        modifier = modifier,
        text = text,
        style = titleStyle { medium },
        color = colors.placeholder(enabled = enabled)
    )
}

@Composable
fun TextInputIcon(
    position: Forms.IconPosition,
    source: Icons.Source?,
    focused: Boolean,
    color: Color,
    modifier: Modifier = Modifier,
    visible: Boolean = true,
    size: Dp = Forms.TextInputIconSize,
    description: String? = null,
    onClick: (() -> Unit)? = null,
) {
    AnimatedVisibility(
        visible = visible && source != null,
        enter = fadeIn(animationSpec = tween { mediumSmall }),
        exit = fadeOut(animationSpec = tween { mediumSmall }),
    ) {
        source?.let {
            Icons.Icon(
                modifier = modifier,
                source = source,
                description = description,
                color = color,
                size = size,
                onClick = onClick
            )
        }
    }
}

fun Forms.State<String?>.isErrorMessageVisible(): Boolean =
    (this as? Forms.Error)?.message != null

fun Forms.State<String?>.isTrailingClearTextVisible(): Boolean =
    !focused || !value.isNullOrBlank()

fun Forms.textInputInitialState(initialValue: String? = null): Forms.State<String?> =
    Forms.Valid(value = initialValue)

@Composable
fun Forms.textInputColors(
    textColor: Color = palette { onSurface },
    disabledTextColor: Color = textColor.disabled(),
    backgroundColor: Color = palette { surface },
    cursorColor: Color = textColor,
    errorCursorColor: Color = palette { errorContainer }.alpha { high },
    focusedBorderColor: Color = palette { primary },
    unfocusedBorderColor: Color = textColor,
    disabledBorderColor: Color = unfocusedBorderColor.disabled(),
    errorBorderColor: Color = palette { error }.alpha { high },
    leadingIconColor: Color = textColor.alpha { medium },
    disabledLeadingIconColor: Color = textColor.disabled(),
    errorLeadingIconColor: Color = palette { error }.alpha { low }.over { surface },
    trailingIconColor: Color = textColor.alpha { medium },
    disabledTrailingIconColor: Color = textColor.disabled(),
    errorTrailingIconColor: Color = palette { error }.alpha { low }.over { surface },
    focusedLabelColor: Color = focusedBorderColor.alpha { high },
    unfocusedLabelColor: Color = unfocusedBorderColor.alpha { high },
    disabledLabelColor: Color = disabledBorderColor.alpha { medium },
    errorLabelColor: Color = errorBorderColor.alpha { high },
    placeholderColor: Color = textColor.alpha { medium },
    disabledPlaceholderColor: Color = disabledTextColor.alpha { medium },
): TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
    textColor = textColor,
    disabledTextColor = disabledTextColor,
    backgroundColor = backgroundColor,
    cursorColor = cursorColor,
    errorCursorColor = errorCursorColor,
    focusedBorderColor = focusedBorderColor,
    unfocusedBorderColor = unfocusedBorderColor,
    disabledBorderColor = disabledBorderColor,
    errorBorderColor = errorBorderColor,
    leadingIconColor = leadingIconColor,
    disabledLeadingIconColor = disabledLeadingIconColor,
    errorLeadingIconColor = errorLeadingIconColor,
    trailingIconColor = trailingIconColor,
    disabledTrailingIconColor = disabledTrailingIconColor,
    errorTrailingIconColor = errorTrailingIconColor,
    focusedLabelColor = focusedLabelColor,
    unfocusedLabelColor = unfocusedLabelColor,
    disabledLabelColor = disabledLabelColor,
    errorLabelColor = errorLabelColor,
    placeholderColor = placeholderColor,
    disabledPlaceholderColor = disabledPlaceholderColor,
)