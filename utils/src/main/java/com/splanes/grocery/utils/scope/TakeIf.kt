package com.splanes.grocery.utils.scope

inline fun <T> orNull(condition: Boolean, crossinline block: () -> T?): T? = if (condition) block() else null