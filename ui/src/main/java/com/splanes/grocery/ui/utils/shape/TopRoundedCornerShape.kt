package com.splanes.grocery.ui.utils.shape

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.splanes.grocery.ui.utils.resources.Zero

fun TopRoundedCornerShape(size: Dp) = RoundedCornerShape(
    topStart = size,
    topEnd = size,
    bottomEnd = Dp.Zero,
    bottomStart = Dp.Zero
)

fun TopRoundedCornerShape(size: Int) = TopRoundedCornerShape(size.dp)