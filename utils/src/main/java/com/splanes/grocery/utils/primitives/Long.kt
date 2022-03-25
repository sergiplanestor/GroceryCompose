package com.splanes.grocery.utils.primitives

const val EMPTY_LONG = 0L

fun Long?.orEmpty(default: Long = EMPTY_LONG): Long = this ?: default