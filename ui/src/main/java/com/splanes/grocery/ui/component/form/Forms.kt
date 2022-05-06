package com.splanes.grocery.ui.component.form

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.TextFieldColors
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.splanes.grocery.ui.component.form.textinput.utils.toImeAction
import com.splanes.grocery.ui.component.form.textinput.utils.toKeyboardCapitalization
import com.splanes.grocery.ui.component.form.textinput.utils.toKeyboardType

object Forms {

    sealed interface UiState<out T>
    interface UninitializedUiState<out T> : UiState<T>
    interface ValidUiState<out T> : UiState<T>
    interface InvalidUiState<out T> : UiState<T>

    sealed class State<out T>(open val value: T?, open val enabled: Boolean, open val focused: Boolean)
    data class Error<out T>(
        override val value: T,
        @StringRes val message: Int?,
        override val enabled: Boolean = true,
        override val focused: Boolean = false
    ) : State<T>(
        value = value,
        enabled = enabled,
        focused = focused
    )

    data class Valid<out T>(
        override val value: T?,
        override val enabled: Boolean = true,
        override val focused: Boolean = false,
    ) : State<T>(
        value = value,
        enabled = enabled,
        focused = focused
    )

    data class Validator<T>(@StringRes val error: Int, val isValueValid: (T?) -> Boolean) {
        sealed class Result
        object Valid : Result()
        data class Error(@StringRes val error: Int?) : Result()
        companion object
    }

    sealed class UiModel<T>(
        open val uiState: State<T>,
        open val validators: List<Validator<T>> = emptyList()
    )

    data class TextInputUiModel(
        override val uiState: State<String?>,
        val colors: TextFieldColors,
        override val validators: List<Validator<String?>> = emptyList(),
        val inputStyle: TextInputStyle = OutlinedInputStyle(all = 12),
        val keyboard: Keyboard = TextKeyboard(),
        val keyboardActions: KeyboardActions = KeyboardActions(),
        val singleLine: Boolean = true,
        val maxLines: Int = Int.MAX_VALUE,
        val trailingClearText: Boolean = true,
    ) : UiModel<String?>(uiState, validators)

    sealed class TextInputStyle
    object NoneInputStyle : TextInputStyle()
    data class OutlinedInputStyle(
        val topStart: Dp,
        val topEnd: Dp,
        val bottomEnd: Dp,
        val bottomStart: Dp
    ) : TextInputStyle() {
        constructor(horizontal: Dp, vertical: Dp) : this(
            topStart = horizontal,
            topEnd = vertical,
            bottomEnd = horizontal,
            bottomStart = vertical
        )

        constructor(all: Dp) : this(horizontal = all, vertical = all)
        constructor(all: Int) : this(all = all.dp)
    }

    data class CutCornerInputStyle(
        val topStart: Dp,
        val topEnd: Dp,
        val bottomEnd: Dp,
        val bottomStart: Dp
    ) : TextInputStyle() {
        constructor(horizontal: Dp, vertical: Dp) : this(
            topStart = horizontal,
            topEnd = vertical,
            bottomEnd = horizontal,
            bottomStart = vertical
        )

        constructor(all: Dp) : this(horizontal = all, vertical = all)
        constructor(all: Int) : this(all = all.dp)
    }

    enum class IconPosition {
        Leading, Trailing
    }

    sealed class Keyboard(
        open val capitalization: KeyboardCapitalization = KeyboardCapitalization.Sentences,
        open val autoCorrect: Boolean = true,
        open val keyboardType: KeyboardType = KeyboardType.Text,
        open val imeAction: ImeAction = ImeAction.Default
    ) {
        enum class Caps {
            All,
            Sentence,
            Word,
            None;

            companion object
        }

        enum class Input {
            Text,
            Email,
            Phone,
            Uri,
            Number,
            Decimal,
            Password,
            NumericPassword;

            companion object
        }

        enum class Ime {
            Default,
            None,
            Go,
            Search,
            Send,
            Previous,
            Next,
            Done;

            companion object
        }

        companion object
    }

    data class TextKeyboard(
        val caps: Caps = Caps.Sentence,
        val input: Input = Input.Text,
        val ime: Ime = Ime.Default,
        override val autoCorrect: Boolean = false,
    ) : Keyboard(
        capitalization = caps.toKeyboardCapitalization(),
        autoCorrect = autoCorrect,
        keyboardType = input.toKeyboardType(),
        imeAction = ime.toImeAction(),
    )
}