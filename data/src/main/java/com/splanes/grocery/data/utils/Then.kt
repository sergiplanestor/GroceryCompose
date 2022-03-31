package com.splanes.grocery.data.utils

import com.splanes.grocery.utils.logger.utils.throwMessage

internal suspend fun <T> Boolean.coThen(
    onFalse: suspend () -> T = { throwMessage { "`True` result expected but was `false`. Unhandled behavior..." } },
    onTrue: suspend () -> T
) =
    takeIf { this }?.run { onTrue() } ?: onFalse()

internal fun <T> Boolean.then(
    onFalse: () -> T = { throwMessage { "`True` result expected but was `false`. Unhandled behavior..." } },
    onTrue: () -> T
) =
    takeIf { this }?.run { onTrue() } ?: onFalse()

internal suspend fun execNestedBoolean(vararg sequence: suspend () -> Boolean) : Boolean =
    with(sequence.iterator()) {
        var result = hasNext()
        while (result && hasNext()) {
            result = next().invoke()
        }
        result
    }