package com.splanes.grocery.data.common.repository

import com.splanes.grocery.utils.logger.utils.throwError

abstract class RepositoryImpl {
    protected suspend fun <T> exec(
        errorMapper: Throwable.() -> Throwable = { this },
        block: suspend () -> T
    ): T =
        try {
            block()
        } catch (t: Throwable) {
            throwError { t.errorMapper() }
        }
}