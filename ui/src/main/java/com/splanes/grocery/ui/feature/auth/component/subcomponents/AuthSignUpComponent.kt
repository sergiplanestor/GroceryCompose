package com.splanes.grocery.ui.feature.auth.component.subcomponents

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons.Rounded
import androidx.compose.material.icons.rounded.AlternateEmail
import androidx.compose.material.icons.rounded.Badge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.splanes.grocery.ui.component.anim.AnimationSideEffect
import com.splanes.grocery.ui.component.button.Buttons
import com.splanes.grocery.ui.component.form.Forms
import com.splanes.grocery.ui.component.form.utils.Email
import com.splanes.grocery.ui.component.form.utils.NotNullOrBlank
import com.splanes.grocery.ui.component.form.utils.isError
import com.splanes.grocery.ui.component.form.utils.isValid
import com.splanes.grocery.ui.component.form.utils.resultOf
import com.splanes.grocery.ui.component.icons.rounded
import com.splanes.grocery.ui.component.spacer.VerticalSpace
import com.splanes.grocery.ui.component.spacer.column.Space
import com.splanes.grocery.ui.utils.anim.AnimDefaults
import com.splanes.grocery.ui.utils.anim.scrollTo
import com.splanes.grocery.ui.utils.anim.tween
import com.splanes.grocery.ui.utils.field.FieldDefaults
import com.splanes.grocery.ui.utils.field.FieldType
import com.splanes.grocery.ui.utils.field.FieldType.Email
import com.splanes.grocery.ui.utils.resources.Strings
import com.splanes.grocery.ui.utils.resources.alpha
import com.splanes.grocery.ui.utils.resources.bodyStyle
import com.splanes.grocery.ui.utils.resources.dp
import com.splanes.grocery.ui.utils.resources.dpValue
import com.splanes.grocery.ui.utils.resources.headlineStyle
import com.splanes.grocery.ui.utils.resources.labelStyle
import com.splanes.grocery.ui.utils.resources.palette
import com.splanes.grocery.ui.utils.resources.shape
import com.splanes.grocery.ui.utils.resources.string
import com.splanes.grocery.ui.utils.resources.titleStyle
import com.splanes.toolkit.compose.ui.components.common.utils.color.composite
import com.splanes.toolkit.compose.ui.components.feature.navhost.graph.transition.fadeIn
import com.splanes.toolkit.compose.ui.components.feature.navhost.graph.transition.fadeOut
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun AuthSignUpComponent(
    username: Forms.State<String>,
    email: Forms.State<String>,
    scrollState: ScrollState,
    onSignUp: (String, String) -> Unit
) {
    var alpha by remember { mutableStateOf(0f) }
    var usernameState by remember { mutableStateOf(username) }
    var emailState by remember { mutableStateOf(email) }
    val coroutineScope = rememberCoroutineScope()
    val initFormCardOffset = (LocalConfiguration.current.screenHeightDp / 2).toFloat()
    val formCardOffset = remember { Animatable(initialValue = initFormCardOffset) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WelcomeText()
        Space { mediumLarge }
        Space(formCardOffset.value.dp)
        SignUpFormCard(alpha) {
            UsernameTextField(
                state = usernameState,
                onFocused = { scrollTo(coroutineScope, scrollState, false) },
                onChange = {
                    usernameState = updateFieldState(usernameState, it, usernameValidators)
                }
            )
            Space { mediumSmall }
            EmailTextField(
                state = emailState,
                onFocused = { scrollTo(coroutineScope, scrollState, true) },
                onChange = { emailState = updateFieldState(emailState, it, emailValidators) }
            )
            Space { medium }
            SubmitButton(
                enabled = Forms.isValid(usernameState, emailState),
                onClick = {
                    onSignUp(usernameState.value.orEmpty(), emailState.value.orEmpty())
                }
            )
        }

        Animations(offset = formCardOffset) { alpha = it }
    }
}

@Composable
fun WelcomeText() {
    Text(
        text = string { Strings.user_welcome_to_app },
        style = headlineStyle { medium },
        color = palette { onPrimary },
        textAlign = TextAlign.Center
    )
    VerticalSpace { mediumSmall }
    Text(
        text = string { Strings.user_nice_to_meet_you },
        style = headlineStyle { small },
        color = palette { onPrimary },
        textAlign = TextAlign.Center
    )
}

@Composable
fun SignUpFormCard(
    alpha: Float,
    form: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = Modifier
            .padding(start = dp { small }, end = dp { small })
            .alpha(alpha),
        color = palette { surface },
        shape = shape(12),
        elevation = 8.dp,
        content = {
            Column(
                modifier = Modifier.padding(
                    start = dp { medium },
                    end = dp { medium },
                    bottom = dp { medium },
                    top = dp { large }
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = dp { mediumSmall }),
                    text = string { Strings.user_sign_up },
                    style = headlineStyle { medium },
                    color = palette { onSurface },
                )
                Space { small }
                Text(
                    modifier = Modifier.padding(horizontal = dp { mediumSmall }),
                    text = string { Strings.user_sign_up_form },
                    style = titleStyle { medium },
                    color = palette { onSurface }
                )
                Space { mediumLarge }
                form()
            }
        }
    )
}

@Composable
fun UsernameTextField(
    state: Forms.State<String>,
    onFocused: () -> Unit,
    onChange: (String) -> Unit
) {
    Field(
        value = state.value.orEmpty(),
        onChange = onChange,
        onFocused = onFocused,
        label = string { Strings.username },
        icon = Rounded.Badge,
        isError = state.isError(),
        errorMessage = (state as? Forms.Error<String>)?.message,
        keyboardOptions = FieldDefaults.keyboardOption(FieldType.Text(), ImeAction.Next)
    )
}

@Composable
fun EmailTextField(
    state: Forms.State<String>,
    onFocused: () -> Unit,
    onChange: (String) -> Unit
) {
    Field(
        value = state.value.orEmpty(),
        onChange = onChange,
        onFocused = onFocused,
        label = string { Strings.email },
        icon = com.splanes.grocery.ui.component.icons.Icons.rounded { AlternateEmail }.value,
        isError = state.isError(),
        errorMessage = (state as? Forms.Error<String>)?.message,
        keyboardOptions = FieldDefaults.keyboardOption(Email, ImeAction.Done)
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
        textColor = palette { onPrimary },
        backgroundColor = palette { primary.composite(surface, .8) },
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
    modifier: Modifier = Modifier,
    onFocused: () -> Unit = {}
) {
    var hasFocus by remember { mutableStateOf(false) }
    var isClearIconVisible by remember { mutableStateOf(false) }
    var isErrorMessageVisible by remember { mutableStateOf(isError && errorMessage != null) }
    val colors = FieldDefaults.colorsOutlined(
        textColor = palette { onSurface },
        errorColor = palette { error.composite(onSurface, .75) },
    )

    Column(modifier = modifier) {
        OutlinedTextField(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .onFocusEvent { focusState ->
                    hasFocus = focusState.hasFocus
                    isClearIconVisible = hasFocus && value.isNotBlank()
                    if (hasFocus) onFocused()
                },
            value = value,
            onValueChange = { input ->
                isErrorMessageVisible = (isError && input.isBlank()).not()
                onChange(input)
            },
            colors = colors,
            textStyle = bodyStyle { medium },
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
                    style = labelStyle { medium },
                    color = palette {
                        error.composite(
                            onSurface,
                            .75
                        )
                    }.alpha { if (hasFocus) high else medium }
                )
            }
        }
    }
}

@Composable
private fun Animations(
    offset: Animatable<Float, *>,
    update: (Float) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val max =
        (LocalConfiguration.current.screenHeightDp / 2).toFloat() - dpValue<Float> { medium }
    AnimationSideEffect(
        AnimDefaults.SideEffectUi(
            anim = offset,
            target = dpValue { medium },
            duration = 1500,
            onUpdate = { value ->
                coroutineScope.launch { update(((max - value) / max)) }
            }
        )
    )
}

private fun scrollTo(scope: CoroutineScope, state: ScrollState, isTargetMax: Boolean) {
    scope.scrollTo(
        state,
        if (isTargetMax) state.maxValue else state.maxValue - 1,
        tween(delay = 150, duration = 500)
    )
}

private fun updateFieldState(
    currentState: Forms.State<String>,
    value: String,
    validators: List<Forms.Validator<String>>
): Forms.State<String> {
    val validatorResult = Forms.Validator.resultOf(validators, value)
    return when (currentState) {
        is Forms.Valid -> {
            when (validatorResult) {
                is Forms.Validator.Error ->
                    Forms.Error(value, message = validatorResult.error)
                Forms.Validator.Valid ->
                    currentState.copy(value)
            }
        }
        is Forms.Error -> {
            when (validatorResult) {
                is Forms.Validator.Error ->
                    currentState.copy(value = value, message = validatorResult.error)
                Forms.Validator.Valid ->
                    Forms.Valid(value)
            }
        }
    }
}

private val usernameValidators: List<Forms.Validator<String>> = listOf(
    Forms.Validator(error = Strings.field_error_mandatory, Forms.Validator.NotNullOrBlank),
)

private val emailValidators: List<Forms.Validator<String>> = listOf(
    Forms.Validator(error = Strings.field_error_mandatory, Forms.Validator.NotNullOrBlank),
    Forms.Validator(error = Strings.field_error_invalid_email, Forms.Validator.Email),
)