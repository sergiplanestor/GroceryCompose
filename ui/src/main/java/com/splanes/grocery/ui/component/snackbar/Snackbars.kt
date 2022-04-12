package com.splanes.grocery.ui.component.snackbar

import androidx.compose.material.SnackbarDuration

object Snackbars {

    sealed class UiStyle {
        object Default : UiStyle()
        object None : UiStyle()
    }
    sealed class FeedbackStyle: UiStyle() {
        object Information : FeedbackStyle()
        object Positive : FeedbackStyle()
        object Negative : FeedbackStyle()
    }

    data class UiModel(
        val message: String,
        val actionLabel: String? = null,
        val duration: SnackbarDuration = SnackbarDuration.Short,
        val style: UiStyle = UiStyle.None
    ) {
        companion object {
            fun empty(): UiModel = UiModel(message = "")
        }
    }

}