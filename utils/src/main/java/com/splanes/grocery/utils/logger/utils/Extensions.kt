package com.splanes.grocery.utils.logger.utils

import timber.log.Timber

object EmptyError : Throwable(message = "Exception thrown with empty error...")

fun <T> throwError(
    message: (() -> String)? = null,
    throwable: () -> Throwable?
): T {
    val t = throwable()
    val err = when {
        message != null -> RuntimeException(message.invoke())
        t != null -> t.takeUnless { it.message.isNullOrBlank() }
        else -> null
    } ?: EmptyError

    Timber.e(err)
    throw err
}

fun <T> throwMessage(message: () -> String? = { EmptyError.message }): T =
    throwError(message = { (message() ?: EmptyError.message).orEmpty() }, throwable = { null })