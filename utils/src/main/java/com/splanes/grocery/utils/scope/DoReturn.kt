package com.splanes.grocery.utils.scope

inline fun <T> withResult(result: T, crossinline block: () -> Unit): T = block().let { result }