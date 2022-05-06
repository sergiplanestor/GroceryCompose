package com.splanes.grocery.ui.component.forms

import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import com.splanes.grocery.ui.component.forms.fields.isValid
import com.splanes.grocery.ui.utils.resources.Zero
import com.splanes.grocery.ui.utils.resources.shape

object Form {

    interface Colors {
        val surfaceOnValid: Color
        val surfaceOnError: Color
    }

    data class State(
        val fields: Map<Int, Field.State<*>>,
        val onSubmitRequest: suspend () -> Boolean = { true }
    ) {
        val isValid: Boolean get() = fields.values.all { fieldState -> fieldState.isValid() }
    }
}

object Field {

    interface Color {
        val valid: androidx.compose.ui.graphics.Color
        val validAccent: androidx.compose.ui.graphics.Color
        val error: androidx.compose.ui.graphics.Color
        val errorAccent: androidx.compose.ui.graphics.Color
        val disabled: androidx.compose.ui.graphics.Color get() = valid.copy(alpha = .38f)
        val errorDisabled: androidx.compose.ui.graphics.Color get() = error.copy(alpha = .38f)
    }

    interface Colors

    sealed class State<out T>(
        open val value: T?,
        open val enabled: Boolean,
        open val focused: Boolean,
        open val position: Int
    )

    data class ValidState<out T>(
        override val position: Int,
        override val value: T,
        override val enabled: Boolean = true,
        override val focused: Boolean = false,
    ) : State<T>(value, enabled, focused, position)

    data class ErrorState<out T>(
        override val position: Int,
        @StringRes val message: Int?,
        override val value: T?,
        override val enabled: Boolean = true,
        override val focused: Boolean = false,
    ) : State<T>(value, enabled, focused, position)

    data class HiddenState<out T>(override val value: T) :
            State<T>(value, enabled = true, focused = false, position = -1)

    interface Validator<T> {
        val errorMessage: Int
        fun isValueValid(value: T?): Boolean
    }

    sealed class ValidationResult
    object ValidationSuccess : ValidationResult()
    data class ValidationFailure(@StringRes val message: Int?) : ValidationResult()
}

@Composable
fun rememberFormState(): State<Form.State> =
    remember { mutableStateOf(Form.State()) }

@Composable
fun <T> rememberFormFieldState(
    initialValue: T,
    enabled: Boolean = true,
    focused: Boolean = false,
): State<Field.State<T>> =
    remember { mutableStateOf(Field.ValidState(initialValue, enabled = enabled, focused = focused)) }

@Composable
fun Form(
    state: Form.State,
    colors: Form.Colors,
    modifier: Modifier = Modifier,
    shape: Shape = shape { small },
    border: BorderStroke? = null,
    elevation: Dp = Dp.Zero,
    contentPaddings: PaddingValues = PaddingValues(Dp.Zero),
    contentAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    content: @Composable LazyListScope.() -> Unit
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = colors.surface(valid = state.valid),
        border = border,
        shadowElevation = elevation
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPaddings),
            horizontalAlignment = contentAlignment
        ) {

        }
    }
}

@Composable
fun <T> Field(
    state: Field.State<T>,
    colors: Field.Colors,
    modifier: Modifier = Modifier,
    validators: List<Field.Validator<T>> = emptyList(),
    shape: Shape = shape { small },
    border: BorderStroke? = null,
    elevation: Dp = Dp.Zero,
    contentPaddings: PaddingValues = PaddingValues(Dp.Zero),
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = colors.surface,
        border = border,
        shadowElevation = elevation
    ) {

    }
    Crossfade(targetState = state) { fieldState ->
        when (fieldState) {
            is Field.ErrorState -> {

            }
            is Field.ValidState -> {

            }
        }
    }
}