package com.splanes.grocery.ui.component.scaffold

import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SnackbarData
import androidx.compose.material.SnackbarHost
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.splanes.grocery.ui.component.bottomsheet.BottomSheet
import com.splanes.grocery.ui.component.scaffold.Scaffolds.DialogUiState
import com.splanes.grocery.ui.component.scaffold.Scaffolds.LoaderUiState
import com.splanes.grocery.ui.component.scaffold.Scaffolds.SideEffect
import com.splanes.grocery.ui.component.scaffold.Scaffolds.SideEffect.BottomSheet
import com.splanes.grocery.ui.component.scaffold.Scaffolds.SideEffect.Snackbar
import com.splanes.grocery.ui.component.scaffold.Scaffolds.bottomSheetUiModel
import com.splanes.grocery.ui.component.scaffold.Scaffolds.uiModel
import com.splanes.grocery.ui.utils.anim.tween
import com.splanes.grocery.ui.utils.bottomsheet.rememberBottomSheetState
import com.splanes.grocery.ui.utils.resources.same
import com.splanes.grocery.ui.utils.snackbar.rememberSnackbarHostState
import com.splanes.toolkit.compose.ui.theme.utils.accessors.Colors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Scaffold(
    viewModel: ScaffoldViewModel,
    topBar: @Composable (Scaffolds.UiModel) -> Unit = {},
    bottomBar: @Composable (Scaffolds.UiModel) -> Unit = {},
    snackbar: @Composable (Scaffolds.UiModel, SnackbarData) -> Unit = { _, _ -> },
    dialog: @Composable (Scaffolds.UiModel) -> Unit = {},
    loader: @Composable (Scaffolds.UiModel) -> Unit = {},
    content: @Composable (Scaffolds.UiModel) -> Unit = {},
) {
    val scaffoldUiState by viewModel.uiState

    val bottomSheetState = rememberBottomSheetState()
    val snackbarHostState = rememberSnackbarHostState()
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState,
        snackbarHostState = snackbarHostState
    )

    val coroutineScope = rememberCoroutineScope()

    val uiSideEffectState = viewModel.uiSideEffectFlow.collectAsNullableState()
    val containerColor = scaffoldUiState.containerUiModel.color(Colors)
    val containerColorAnim = remember { Animatable(containerColor) }

    LaunchedEffect(uiSideEffectState) {
        coroutineScope.launch {
            when (val effect = uiSideEffectState.value) {
                is BottomSheet -> { bottomSheetState.animateTo(effect.state) }
                Snackbar -> {
                    with(scaffoldUiState.snackbarUiModel) {
                        snackbarHostState.showSnackbar(message, actionLabel, duration)
                    }
                }
                null -> {
                    // Nothing to do
                }
            }
        }
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
            sheetShape = bottomSheetUiState.shape(bottomSheetState),
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
                        hostState = snackbarHostState,
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

@Composable
private fun Flow<SideEffect>.collectAsNullableState(): State<SideEffect?> =
    collectAsState(initial = null)
