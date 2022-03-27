package com.splanes.grocery.ui.feature.auth.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AlternateEmail
import androidx.compose.material.icons.rounded.Badge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import com.splanes.grocery.ui.component.button.Buttons
import com.splanes.grocery.ui.component.form.model.Forms
import com.splanes.grocery.ui.component.form.model.Forms.isDefault
import com.splanes.grocery.ui.component.form.model.Forms.isError
import com.splanes.grocery.ui.component.form.model.Forms.satisfies
import com.splanes.grocery.ui.component.spacer.VerticalSpace
import com.splanes.grocery.ui.component.spacer.column.Space
import com.splanes.grocery.ui.utils.anim.AnimDefaults
import com.splanes.grocery.ui.utils.anim.tween
import com.splanes.grocery.ui.utils.field.FieldDefaults
import com.splanes.grocery.ui.utils.field.FieldType
import com.splanes.grocery.ui.utils.resources.Strings
import com.splanes.grocery.ui.utils.resources.alpha
import com.splanes.grocery.ui.utils.resources.body
import com.splanes.grocery.ui.utils.resources.color
import com.splanes.grocery.ui.utils.resources.dp
import com.splanes.grocery.ui.utils.resources.headline
import com.splanes.grocery.ui.utils.resources.label
import com.splanes.grocery.ui.utils.resources.string
import com.splanes.grocery.ui.utils.resources.title
import com.splanes.toolkit.compose.ui.components.common.utils.color.composite
import com.splanes.toolkit.compose.ui.components.feature.navhost.graph.transition.fadeIn
import com.splanes.toolkit.compose.ui.components.feature.navhost.graph.transition.fadeOut


@Composable
fun AuthSignUpComponent(
    username: Forms.State<String>,
    email: Forms.State<String>,
    onSignUp: (String, String) -> Unit
) {
    var usernameState by remember { mutableStateOf(username) }
    var emailState by remember { mutableStateOf(email) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WelcomeText()
        Space { large }
        SignUpFormText()
        Space { huge }
        UsernameTextField(
            state = usernameState,
            onChange = { usernameState = updateFieldState(usernameState, it, usernameValidators) }
        )
        Space { mediumSmall }
        EmailTextField(
            state = emailState,
            onChange = { emailState = updateFieldState(emailState, it, emailValidators) }
        )
        Space { medium }
        SubmitButton(
            enabled = usernameState.isDefault() && emailState.isDefault(),
            onClick = {
                onSignUp(usernameState.value.orEmpty(), emailState.value.orEmpty())
            }
        )
    }
}

@Composable
fun WelcomeText() {
    Text(
        text = string { Strings.user_welcome_to_app },
        style = headline { medium },
        color = color { onPrimary },
        textAlign = TextAlign.Center
    )
    VerticalSpace { mediumSmall }
    Text(
        text = string { Strings.user_nice_to_meet_you },
        style = headline { small },
        color = color { onPrimary },
        textAlign = TextAlign.Center
    )
}

@Composable
fun SignUpFormText() {
    Text(
        text = string { Strings.user_sign_up_form },
        style = title { medium },
        color = color { onPrimary },
        textAlign = TextAlign.Center
    )
}

@Composable
fun UsernameTextField(
    state: Forms.State<String>,
    onChange: (String) -> Unit
) {
    Field(
        value = state.value.orEmpty(),
        onChange = onChange,
        label = string { Strings.username },
        icon = Icons.Rounded.Badge,
        isError = state.isError(),
        errorMessage = (state as? Forms.Error<String>)?.message,
        keyboardOptions = FieldDefaults.keyboardOption(FieldType.Text(), ImeAction.Next)
    )
}

@Composable
fun EmailTextField(
    state: Forms.State<String>,
    onChange: (String) -> Unit
) {
    Field(
        value = state.value.orEmpty(),
        onChange = onChange,
        label = string { Strings.email },
        icon = Icons.Rounded.AlternateEmail,
        isError = state.isError(),
        errorMessage = (state as? Forms.Error<String>)?.message,
        keyboardOptions = FieldDefaults.keyboardOption(FieldType.Text(), ImeAction.Next)
    )
}

@Composable
fun SubmitButton(
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Buttons.Fill(
        modifier = modifier,
        text = string { Strings.lets_go },
        enabled = enabled,
        textColor = color { primary },
        backgroundColor = color { onPrimary.composite(primary, .7) },
        onClick = onClick
    )
}

@Composable
fun Field(
    value: String,
    onChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    isError: Boolean,
    errorMessage: Int?,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier
) {
    var hasFocus by remember { mutableStateOf(false) }
    var isClearIconVisible by remember { mutableStateOf(false) }
    var isErrorMessageVisible by remember { mutableStateOf(isError && errorMessage != null) }
    val colors = FieldDefaults.colorsOutlined(
        textColor = color { onPrimary },
        errorColor = color { onPrimary.composite(error, .5) },
    )

    Column(modifier = modifier) {
        OutlinedTextField(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .onFocusEvent { focusState ->
                    hasFocus = focusState.hasFocus
                    isClearIconVisible = hasFocus && value.isNotBlank()
                },
            value = value,
            onValueChange = { input ->
                isErrorMessageVisible = (isError && input.isBlank()).not()
                onChange(input)
            },
            colors = colors,
            textStyle = body { medium },
            shape = FieldDefaults.shapeOutlined(),
            label = { FieldDefaults.Label(label, colors, error = isError, focused = hasFocus) },
            singleLine = true,
            leadingIcon = {
                FieldDefaults.Icon(
                    imageVector = icon,
                    iconType = FieldDefaults.FieldIcon.Leading,
                    focused = hasFocus,
                    colors = colors
                )
            },
            trailingIcon = {
                FieldDefaults.IconClear(
                    visible = isClearIconVisible,
                    onClick = { onChange("") },
                    size = 18,
                    colors = colors
                )
            },
            isError = isError,
            keyboardOptions = keyboardOptions
        )
        AnimatedVisibility(
            visible = isErrorMessageVisible,
            enter = fadeIn(duration = AnimDefaults.DurationShort.toInt()) +
                    expandVertically(animationSpec = tween(duration = AnimDefaults.DurationShort)),
            exit = fadeOut(duration = AnimDefaults.DurationShort.toInt()) +
                    shrinkVertically(animationSpec = tween(duration = AnimDefaults.DurationShort)),
        ) {
            errorMessage?.let { text ->
                Text(
                    modifier = Modifier.padding(vertical = dp { small }),
                    text = string { text },
                    style = label { medium },
                    color = color { error }.alpha { if (hasFocus) high else medium }
                )
            }
        }
    }
}

private fun updateFieldState(
    currentState: Forms.State<String>,
    value: String,
    validators: List<Forms.Validator<String>>
): Forms.State<String> {
    val validatorResult = value.satisfies(validators)
    return when (currentState) {
        is Forms.Default -> {
            when (validatorResult) {
                is Forms.Validator.Error ->
                    Forms.Error(value, validatorResult.error)
                Forms.Validator.Valid ->
                    currentState.copy(value)
            }
        }
        is Forms.Error -> {
            when (validatorResult) {
                is Forms.Validator.Error ->
                    currentState.copy(value = value, message = validatorResult.error)
                Forms.Validator.Valid ->
                    Forms.Default(value)
            }
        }
    }
}

private val usernameValidators: List<Forms.Validator<String>> = listOf(
    Forms.Validator.NotNull(error = Strings.field_error_mandatory),
    Forms.Validator.NotBlank(error = Strings.field_error_mandatory),
)

private val emailValidators: List<Forms.Validator<String>> = listOf(
    Forms.Validator.NotNull(error = Strings.field_error_mandatory),
    Forms.Validator.NotBlank(error = Strings.field_error_mandatory),
    Forms.Validator.Email(error = Strings.field_error_invalid_email),
)