package com.splanes.grocery.ui.component.scaffold

import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SnackbarData
import androidx.compose.material.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.splanes.grocery.ui.component.bottomsheet.BottomSheet
import com.splanes.grocery.ui.component.scaffold.Scaffolds.DialogUiState
import com.splanes.grocery.ui.component.scaffold.Scaffolds.LoaderUiState
import com.splanes.grocery.ui.component.scaffold.Scaffolds.bottomSheetUiModel
import com.splanes.grocery.ui.component.scaffold.Scaffolds.uiModel
import com.splanes.grocery.ui.utils.anim.tween
import com.splanes.grocery.ui.utils.resources.same
import com.splanes.toolkit.compose.ui.theme.utils.accessors.Colors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Scaffold(
    viewModel: ScaffoldViewModel,
    scaffoldState: BottomSheetScaffoldState,
    topBar: @Composable (Scaffolds.UiModel) -> Unit = {},
    bottomBar: @Composable (Scaffolds.UiModel) -> Unit = {},
    snackbar: @Composable (Scaffolds.UiModel, SnackbarData) -> Unit = { _, _ -> },
    dialog: @Composable (Scaffolds.UiModel) -> Unit = {},
    loader: @Composable (Scaffolds.UiModel) -> Unit = {},
    content: @Composable (Scaffolds.UiModel) -> Unit = {},
) {
    val scaffoldUiState by viewModel.uiState

    val coroutineScope = rememberCoroutineScope()

    //viewModel.scaffoldState = scaffoldState

    val uiSideEffect by viewModel.uiSideEffectFlow.collectAsState(initial = Scaffolds.SideEffect.Empty)
    val containerColor = scaffoldUiState.containerUiModel.color(Colors)
    val containerColorAnim = remember { Animatable(containerColor) }

    LaunchedEffect(uiSideEffect) {
        coroutineScope.uiSideEffects(uiSideEffect, scaffoldState, scaffoldUiState)
    }

    LaunchedEffect(!containerColor.same(containerColorAnim.value)) {
        if (!containerColor.same(containerColorAnim.value)) {
            coroutineScope.launch {
                containerColorAnim.animateTo(
                    targetValue = containerColor,
                    animationSpec = tween(duration = 200)
                )
            }
        }
    }

    with(scaffoldUiState) {
        val uiModel = uiModel()
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetShape = bottomSheetUiState.shape(scaffoldState.bottomSheetState),
            sheetContent = { BottomSheet(uiModel = bottomSheetUiModel()) },
            sheetPeekHeight = bottomSheetUiState.peekHeight,
            topBar = { topBar(uiModel) },
            backgroundColor = containerColorAnim.value,
            content = {
                Box(modifier = Modifier.fillMaxSize()) {

                    ScaffoldContent(
                        content = { content(uiModel) },
                        bottomBar = { bottomBar(uiModel) }
                    )

                    ScaffoldLoader(
                        uiState = loaderUiState,
                        content = { loader(uiModel) }
                    )

                    ScaffoldDialog(
                        uiState = dialogUiState,
                        content = { dialog(uiModel) }
                    )

                    SnackbarHost(
                        hostState = scaffoldState.snackbarHostState,
                        snackbar = { snackbarData -> snackbar(uiModel, snackbarData) }
                    )
                }
            }
        )
    }
}

@Composable
private fun ScaffoldContent(
    content: @Composable ColumnScope.() -> Unit = {},
    bottomBar: @Composable () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.weight(1f),
            content = content
        )
        bottomBar()
    }
}

@Composable
private fun ScaffoldLoader(
    uiState: LoaderUiState,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        modifier = Modifier.fillMaxSize(),
        visible = uiState.visible,
        enter = uiState.enterTransition,
        exit = uiState.exitTransition,
        content = content
    )
}

@Composable
private fun ScaffoldDialog(
    uiState: DialogUiState,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        modifier = Modifier.fillMaxSize(),
        visible = uiState.visible,
        enter = uiState.enterTransition,
        exit = uiState.exitTransition,
        content = content
    )
}

@OptIn(ExperimentalMaterialApi::class)
private fun CoroutineScope.uiSideEffects(
    effect: Scaffolds.SideEffect,
    state: BottomSheetScaffoldState,
    uiState: Scaffolds.UiState
) = launch {
    when (effect) {
        is Scaffolds.SideEffect.BottomSheetStateChanged -> {
            with(uiState.bottomSheetUiState) {
                if (uiModel.id.isNotBlank()) {
                    //state.bottomSheetState.animateTo(sheetValue)
                }/* else {
                    throwMessageNonFatal { "Attempt to launch bottom sheet without id associated. Invalid behavior!" }
                }*/
            }
        }
        Scaffolds.SideEffect.SnackbarStateChanged -> {
            with(uiState.snackbarUiModel) {
                state.snackbarHostState.showSnackbar(message, actionLabel, duration)
            }
        }
        Scaffolds.SideEffect.Empty -> {
            // Nothing to do
        }
    }
}
