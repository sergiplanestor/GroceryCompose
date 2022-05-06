package com.splanes.grocery.ui.component.forms.fields

import androidx.annotation.StringRes
import com.splanes.grocery.ui.component.forms.Field
import com.splanes.grocery.utils.logger.utils.throwMessage

fun Field.State<*>.isValid(): Boolean = this is Field.ValidState

fun Field.State<*>.isError(): Boolean = this is Field.ErrorState

fun Field.State<*>.isHidden(): Boolean = this is Field.HiddenState

fun <T> Field.State<T>.toValid(value: T? = null): Field.ValidState<T> =
    this as? Field.ValidState ?: Field.ValidState(
            position = position,
            value = (this.value ?: value) ?: throwMessage { "Invalid field value, it must not be null!" },
            enabled = enabled,
            focused = focused
        )

fun <T> Field.State<T>.toError(@StringRes message: Int? = null): Field.ErrorState<T> =
    this as? Field.ErrorState ?: Field.ErrorState(
        position = position,
        value = value,
        enabled = enabled,
        focused = focused,
        message = message
    )