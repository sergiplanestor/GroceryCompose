package com.splanes.grocery.ui.component.form.utils

import androidx.annotation.StringRes
import com.splanes.grocery.ui.component.form.Forms
import com.splanes.grocery.utils.logger.utils.throwMessage

fun <T> Forms.State<T>.isValid(): Boolean = this is Forms.Valid

fun <T> Forms.State<T>.isError(): Boolean = this is Forms.Error

fun <T> Forms.State<T>.update(
    value: T? = this.value,
    focused: Boolean = this.focused,
    enabled: Boolean = this.enabled,
): Forms.State<T> = when (this) {
    is Forms.Valid<T> -> {
        copy(
            value = value,
            focused = focused,
            enabled = enabled
        )
    }
    is Forms.Error<T> -> {
        copy(
            value = value ?: this.value,
            focused = focused,
            enabled = enabled,
            message = message
        )
    }
}

fun <T> Forms.State<T>.doOnValid(action: Forms.Valid<T>.() -> Unit) {
    (this as? Forms.Valid)?.run(action)
}

fun <T> Forms.State<T>.toError(@StringRes message: Int? = (this as? Forms.Error)?.message): Forms.Error<T> =
    Forms.Error(
        value = this.value
            ?: throwMessage { "Impossible to transform to Error if field has not any value. How do you know it is wrong?" },
        focused = this.focused,
        enabled = this.enabled,
        message = message
    )

fun <T> Forms.State<T>.toValid(): Forms.Valid<T> =
    Forms.Valid(
        value = this.value,
        focused = this.focused,
        enabled = this.enabled
    )

fun <T> Forms.State<T>.not(@StringRes message: Int? = (this as? Forms.Error)?.message): Forms.State<T> = when (this) {
    is Forms.Valid<T> -> toError(message)
    is Forms.Error<T> -> toValid()
}

fun <T> Forms.State<T>.focusStateChanged(focused: Boolean): Forms.State<T> = update(focused = focused)

fun <T> Forms.State<T>.enableStateChanged(enabled: Boolean): Forms.State<T> = update(enabled = enabled)

fun <T> Forms.State<T>.focused(): Forms.State<T> = focusStateChanged(focused = true)

fun <T> Forms.State<T>.unfocused(): Forms.State<T> = focusStateChanged(focused = false)

fun <T> Forms.State<T>.enable(): Forms.State<T> = enableStateChanged(enabled = true)

fun <T> Forms.State<T>.disable(): Forms.State<T> = enableStateChanged(enabled = false)

fun <T> Forms.State<T>.isValid(check: Forms.State<T>.() -> Boolean = { !isError() }): Boolean = check()

fun Forms.isValid(vararg fields: Forms.State<*>): Boolean = fields.all { it.isValid() }