package com.splanes.grocery.ui.component.loader

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object Loaders {

    data class UiModel(
        @StringRes val labelRes: Int? = null,
        @RawRes val animRawRes: Int? = null
    ) {
        companion object {
            fun empty() : UiModel = UiModel()
        }
    }

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