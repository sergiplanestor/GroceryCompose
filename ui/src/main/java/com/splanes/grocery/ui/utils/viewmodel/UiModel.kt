package com.splanes.grocery.ui.utils.viewmodel

import com.splanes.grocery.utils.logger.utils.throwMessage
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiModel
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiState
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.viewmodel.ComponentViewModel

fun UiState<*>.unexpectedUiStateErrorMessage(): String =
    "Unexpected UiState. Expected UiState.Ready but was ${this::class.simpleName}"

fun <M : UiModel, VM : ComponentViewModel<M, *, *>> VM.uiModel(
    default: UiState<M>.() -> M = { throwMessage { unexpectedUiStateErrorMessage() } }
): M =
    with(uiState.value) { (this as? UiState.Ready<M>)?.data ?: default() }

fun <M : UiModel, VM : ComponentViewModel<M, *, *>, R> VM.withUiModel(
    default: UiState<M>.() -> R = { throwMessage { unexpectedUiStateErrorMessage() } },
    block: M.() -> R
): R = runCatching { uiModel() }.fold(
    onSuccess = block,
    onFailure = { default(uiState.value) }
)