package com.splanes.grocery.ui.base

import com.splanes.grocery.utils.logger.utils.throwError

private fun <T> Ui.State.Companion.throwNullModelAccessError(): T =
    throwError { IllegalAccessException("Attempt to access to a null model.") }

private fun <T> Ui.State.Companion.throwNullModelCopyError(): T =
    throwError { IllegalArgumentException("Attempt to copy State.Stable with a null model.") }

fun <E, M> Ui.State<E, M>.errorOrElse(fallback: Ui.State<E, M>.() -> Unit = {}): E? =
    (this as? Ui.Error<E>).also { if (it == null) fallback() }?.error

fun <E, M> Ui.State<E, M>.modelOrNull(): M? = (this as? Ui.Stable<M>)?.model

fun <E, M> Ui.State<E, M>.modelOrElse(fallback: Ui.State<E, M>.() -> M): M =
    modelOrNull() ?: fallback()

fun <E, M> Ui.State<E, M>.modelOrThrow(): M = modelOrElse { Ui.State.throwNullModelAccessError() }

fun <E, M> Ui.State<E, M>.copy(model: M? = null, error: E? = null): Ui.State<E, M> =
    when (this) {
        is Ui.Stable -> model?.let { copy(model = model) } ?: Ui.State.throwNullModelCopyError()
        is Ui.Error -> copy(error = error)
    }