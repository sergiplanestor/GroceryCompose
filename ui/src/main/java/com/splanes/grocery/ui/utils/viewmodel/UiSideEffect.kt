package com.splanes.grocery.ui.utils.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.splanes.grocery.ui.utils.flow.collectAsNullableState
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiModel
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiSideEffect
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.viewmodel.ComponentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun <M: UiModel, SE : UiSideEffect, VM : ComponentViewModel<M, *, SE>> VM.SideEffects(
    coroutineScope: CoroutineScope,
    onUiSideEffect: suspend CoroutineScope.(effect: SE) -> Unit
) {
    val sideEffectState = uiSideEffect.collectAsNullableState()
    LaunchedEffect(sideEffectState) {
        coroutineScope.launch { sideEffectState.value?.let { onUiSideEffect(it) } }
    }
}