package com.splanes.grocery.ui.utils.resources

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

operator fun TextUnit.minus(other: Int): TextUnit = value.minus(other).sp

operator fun TextUnit.plus(other: Int): TextUnit = value.plus(other).sp