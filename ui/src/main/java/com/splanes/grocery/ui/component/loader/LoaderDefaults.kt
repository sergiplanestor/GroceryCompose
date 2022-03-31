package com.splanes.grocery.ui.component.loader

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object LoaderDefaults {

    sealed class Order
    object TextBeforeAnim: Order()
    object AnimBeforeText: Order()

    class AnimSize {
        val small = 30
        val medium = 50
        val large = 100
    }

    fun animSize(block: AnimSize.() -> Int): Dp = block(AnimSize()).dp

}