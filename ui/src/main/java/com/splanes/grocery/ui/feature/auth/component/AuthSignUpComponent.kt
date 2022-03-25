package com.splanes.grocery.ui.feature.auth.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
import com.splanes.grocery.ui.component.form.model.Forms
import com.splanes.grocery.ui.component.form.model.Forms.isError
import com.splanes.grocery.ui.component.spacer.column.Space
import com.splanes.grocery.ui.utils.field.FieldDefaults
import com.splanes.grocery.ui.utils.field.FieldType
import com.splanes.grocery.ui.utils.resources.Strings
import com.splanes.grocery.ui.utils.resources.body
import com.splanes.grocery.ui.utils.resources.string
import com.splanes.grocery.ui.utils.resources.title


@Composable
fun AuthSignUpComponent(
    username: Forms.State<String>,
    email: Forms.State<String>,
    onSignUp: (String, String) -> Unit
) {

    var usernameState by remember { mutableStateOf(username) }
    var emailState by remember { mutableStateOf(username) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WelcomeText()
        Space { mediumLarge }
        UsernameTextField(
            state = usernameState,
            onChange = { usernameState = updateFieldState(it, usernameValidators) }
        )
        Space { mediumSmall }
        EmailTextField(
            state = emailState,
            onChange = { emailState = updateFieldState(it, emailValidators) }
        )
        Space { medium }
        SubmitButton()
    }
}

@Composable
fun WelcomeText(

) {
    Text(
        text = "Welcome! Seems that this is your first time here.\nIn order to set up all the config, please fill the next fields :)  ",
        style = title { medium },
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
        placeholder = string { Strings.username_placeholder },
        icon = Icons.Rounded.Badge,
        isError = state.isError(),
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
        placeholder = string { Strings.email_placeholder },
        icon = Icons.Rounded.AlternateEmail,
        isError = state.isError(),
        keyboardOptions = FieldDefaults.keyboardOption(FieldType.Text(), ImeAction.Next)
    )
}

@Composable
fun SubmitButton(

) {

}

@Composable
fun Field(
    value: String,
    onChange: (String) -> Unit,
    label: String,
    placeholder: String,
    icon: ImageVector,
    isError: Boolean,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier
) {
    var isClearIconVisible by remember { mutableStateOf(false) }
    TextField(
        modifier = modifier.onFocusEvent { focusState ->
            isClearIconVisible = focusState.isFocused && value.isNotBlank()
        },
        value = value,
        onValueChange = onChange,
        colors = FieldDefaults.colorsOutlined(),
        textStyle = body { medium },
        shape = FieldDefaults.shapeOutlined(),
        label = { FieldDefaults.Label(label) },
        placeholder = { FieldDefaults.Placeholder(placeholder) },
        singleLine = true,
        leadingIcon = { FieldDefaults.Icon(imageVector = icon) },
        trailingIcon = {
            FieldDefaults.IconClear(
                visible = isClearIconVisible,
                onClick = { onChange("") }
            )
        },
        isError = isError,
        keyboardOptions = keyboardOptions
    )
}

@Composable
private fun updateFieldState(
    value: String,
    validators: List<Forms.Validator<String>>
): Forms.State<String> {

}

private val usernameValidators: List<Forms.Validator<String>> = listOf(
    Forms.Validator.NotNull(),
    Forms.Validator.NotBlank
)

private val emailValidators: List<Forms.Validator<String>> = listOf(
    Forms.Validator.NotNull(),
    Forms.Validator.NotBlank,
    Forms.Validator.Email,
)