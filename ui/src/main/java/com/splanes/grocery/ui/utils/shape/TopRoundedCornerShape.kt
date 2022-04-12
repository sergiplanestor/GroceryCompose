package com.splanes.grocery.ui.utils.shape

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun TopRoundedCornerShape(size: Dp) = RoundedCornerShape(
    topStart = size,
    topEnd = size,
    bottomEnd = 0.dp,
    bottomStart = 0.dp
)

fun TopRoundedCornerShape(size: Int) = TopRoundedCornerShape(size.dp)