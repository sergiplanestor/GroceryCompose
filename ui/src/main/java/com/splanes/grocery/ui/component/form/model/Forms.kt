package com.splanes.grocery.ui.component.form.model

import android.util.Patterns
import androidx.annotation.StringRes

object Forms {

    sealed class State<out T>(open val value: T?)
    data class Error<out T>(override val value: T, @StringRes val message: Int?) : State<T>(value)
    data class Default<out T>(override val value: T?) : State<T>(value)

    fun <T> State<T>.isDefault(): Boolean = this is Default
    fun <T> State<T>.isError(): Boolean = this is Error

    sealed class Validator<T>(@StringRes open val error: Int) {

        abstract fun isValid(value: T): Boolean

        open fun satisfies(value: T): Result =
            if (isValid(value)) Valid else Error(error)

        data class NotNull<T>(@StringRes override val error: Int) : Validator<T>(error) {
            override fun isValid(value: T): Boolean = value != null
        }

        data class NotBlank(@StringRes override val error: Int) : Validator<String>(error) {
            override fun isValid(value: String): Boolean = value.isNotBlank()
        }

        data class Email(@StringRes override val error: Int) : Validator<String>(error) {
            override fun isValid(value: String): Boolean =
                value.matches(Patterns.EMAIL_ADDRESS.toRegex())
        }

        sealed class Result
        object Valid : Result()
        data class Error(@StringRes val error: Int?) : Result()
    }

    fun <T> T.satisfies(validators: List<Validator<T>>): Validator.Result = let { value ->
        validators
            .map { validator -> validator.satisfies(value) }
            .firstOrNull { result -> result is Validator.Error }
            ?: Validator.Valid
    }
}