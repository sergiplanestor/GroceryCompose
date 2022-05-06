package com.splanes.grocery.ui.component.form.fields

import androidx.annotation.StringRes
import com.splanes.grocery.ui.component.form.Forms

object Fields {
    sealed class UiState<out T>(
        open val value: T?,
        open val enabled: Boolean,
        open val focused: Boolean
    )
    data class ErrorUiState<out T>(
        override val value: T,
        @StringRes val message: Int?,
        override val enabled: Boolean = true,
        override val focused: Boolean = false
    ) : UiState<T>(
        value = value,
        enabled = enabled,
        focused = focused
    ), Forms.InvalidUiState<T>

    data class ValidUiState<out T>(
        override val value: T?,
        override val enabled: Boolean = true,
        override val focused: Boolean = false,
    ) : UiState<T>(
        value = value,
        enabled = enabled,
        focused = focused
    ), Forms.ValidUiState<T>
}
