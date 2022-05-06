package com.splanes.grocery.ui.component.form.textinput.utils

import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import com.splanes.grocery.ui.component.form.Forms
import com.splanes.grocery.ui.utils.resources.Zero

fun Forms.TextInputStyle.shape(): Shape =
    when (this) {
        Forms.NoneInputStyle -> RoundedCornerShape(size = Dp.Zero)
        is Forms.OutlinedInputStyle -> RoundedCornerShape(
            topStart = topStart,
            topEnd = topEnd,
            bottomEnd = bottomEnd,
            bottomStart = bottomStart
        )
        is Forms.CutCornerInputStyle -> CutCornerShape(
            topStart = topStart,
            topEnd = topEnd,
            bottomEnd = bottomEnd,
            bottomStart = bottomStart
        )
    }