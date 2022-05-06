package com.splanes.grocery.ui.component.form.utils

import android.util.Patterns
import androidx.annotation.StringRes
import com.splanes.grocery.ui.component.form.Forms

fun <T> Forms.Validator.Result.updateUiState(from: Forms.State<T>): Forms.State<T> = when (this) {
    is Forms.Validator.Error -> from.toError(error)
    Forms.Validator.Valid -> from.toValid()
}

val Forms.Validator.Companion.NotNullOrBlank: (String?) -> Boolean
    get() = { text -> !text.isNullOrBlank() }

val Forms.Validator.Companion.Email: (String?) -> Boolean
    get() = { text -> text?.run { matches(Patterns.PHONE.toRegex()) } == true }

val Forms.Validator.Companion.Phone: (String?) -> Boolean
    get() = { text -> text?.run { matches(Patterns.PHONE.toRegex()) } == true }

fun <T : Any?> Forms.Validator.Companion.notNull(@StringRes error: Int): Forms.Validator<T> =
    Forms.Validator(error = error, isValueValid = { value -> value != null })

fun <T> Forms.Validator<T>.isValueNotValid(value: T?): Boolean =
    !isValueValid(value)

infix fun <T> Forms.Validator<T>.resultOf(value: T?): Forms.Validator.Result =
    if (isValueValid(value)) Forms.Validator.Valid else Forms.Validator.Error(error)

fun <T> Forms.Validator.Companion.resultOf(validators: List<Forms.Validator<T>>, value: T?): Forms.Validator.Result =
    validators
        .find { validator -> validator.isValueNotValid(value) }
        ?.let { validator -> Forms.Validator.Error(validator.error) }
        ?: Forms.Validator.Valid