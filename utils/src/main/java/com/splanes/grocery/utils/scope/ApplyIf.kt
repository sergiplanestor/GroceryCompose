package com.splanes.grocery.utils.scope

inline fun <T> T.apply(condition: Boolean, default: T = this, block: T.() -> T): T =
    if (condition) block() else default

inline fun <T, R> T.applyIfNotNull(other: R?, default: T = this, block: T.(R) -> T): T =
    apply(condition = other != null, default = default) { block(other!!) }