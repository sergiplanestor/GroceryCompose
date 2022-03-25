package com.splanes.grocery.ui.component.form.model

import android.util.Patterns

object Forms {

    sealed class State<out T>(open val value: T?)
    data class Error<out T>(override val value: T, val message: String?) : State<T>(value)
    data class Idle<out T>(override val value: T?) : State<T>(value)

    fun <T> State<T>.isIdle(): Boolean = this is Idle
    fun <T> State<T>.isError(): Boolean = this is Error

    sealed class Validator<T> {

        abstract fun satisfies(value: T): Boolean

        class NotNull<T> : Validator<T>() {
            override fun satisfies(value: T): Boolean = value != null
        }

        object NotBlank : Validator<String>() {
            override fun satisfies(value: String): Boolean = value.isNotBlank()
        }

        object Email : Validator<String>() {
            override fun satisfies(value: String): Boolean =
                value.matches(Patterns.EMAIL_ADDRESS.toRegex())
        }
    }
}