package com.splanes.grocery.ui.utils.resources

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


val Dp.Companion.Zero: Dp get() = 0.dp

operator fun Dp.minus(other: Int): Dp = value.minus(other).dp

operator fun Dp.plus(other: Int): Dp = value.plus(other).dp