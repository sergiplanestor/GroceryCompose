package com.splanes.grocery.ui.component.dialog

import androidx.compose.runtime.Composable

object Dialogs {
    data class UiModel(
        val dialogContent : @Composable () -> Unit = {}
    ) {
        companion object {
            fun empty() : UiModel = UiModel()
        }
    }
}