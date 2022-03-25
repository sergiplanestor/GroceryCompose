package com.splanes.grocery.ui.utils.uistate

import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiModel
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiState

fun <T : UiModel> runOnUiReady(state: UiState<T>, block: (uiModel: T) -> Unit) {
    if (state is UiState.Ready) block(state.data)
}

fun <T : UiModel> runOnUiUninitialized(
    state: UiState<T>,
    block: (state: UiState.Uninitialized<T>) -> Unit
) {
    if (state is UiState.Uninitialized) block(state)
}

fun <T : UiModel> runOnUiLoading(state: UiState<T>, block: (state: UiState.Loading<T>) -> Unit) {
    if (state is UiState.Loading) block(state)
}

fun <T : UiModel> runOnUiError(state: UiState<T>, block: (state: UiState.Error<T>) -> Unit) {
    if (state is UiState.Error) block(state)
}