package com.splanes.grocery.ui.component.scaffold

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.BottomSheetValue.Expanded
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.splanes.grocery.ui.component.bar.AppBars
import com.splanes.grocery.ui.component.bottomsheet.BottomSheets
import com.splanes.grocery.ui.component.bottomsheet.BottomSheets.shapeCornerSize
import com.splanes.grocery.ui.component.dialog.Dialogs
import com.splanes.grocery.ui.component.loader.Loaders
import com.splanes.grocery.ui.component.snackbar.Snackbars
import com.splanes.grocery.ui.utils.anim.tween
import com.splanes.grocery.ui.utils.shape.TopRoundedCornerShape
import com.splanes.toolkit.compose.ui.theme.feature.colors.ThemeColorScheme

@OptIn(ExperimentalMaterialApi::class)
object Scaffolds {

    data class UpdateEvent<State : SubcomponentUiState<*>>(val uiState: State)

    sealed class SideEffect {
        object Empty: SideEffect()
        object BottomSheetStateChanged : SideEffect()
        object SnackbarStateChanged : SideEffect()
    }

    data class UiState(
        val topAppBarUiState: TopAppBarUiState = TopAppBarUiState.empty(),
        val containerUiState: ContainerUiState = ContainerUiState.empty(),
        val bottomBarUiState: BottomBarUiState = BottomBarUiState.empty(),
        val bottomSheetUiState: BottomSheetUiState = BottomSheetUiState(),
        val snackbarUiState: SnackbarUiState = SnackbarUiState.empty(),
        val dialogUiState: DialogUiState = DialogUiState.empty(),
        val loaderUiState: LoaderUiState = LoaderUiState.empty()
    ) {

        val topBarUiModel: AppBars.Top.UiModel get() = topAppBarUiState.uiModel
        val containerUiModel: ContainerUiModel get() = containerUiState.uiModel
        val bottomBarUiModel: AppBars.Bottom.UiModel get() = bottomBarUiState.uiModel
        val bottomSheetUiModel: BottomSheets.UiModel get() = bottomSheetUiState.uiModel
        val snackbarUiModel: Snackbars.UiModel get() = snackbarUiState.uiModel
        val dialogUiModel: Dialogs.UiModel get() = dialogUiState.uiModel
        val loaderUiModel: Loaders.UiModel get() = loaderUiState.uiModel

        fun copy(
            topBarUiModel: AppBars.Top.UiModel = this.topBarUiModel,
            containerUiModel: ContainerUiModel = this.containerUiModel,
            bottomBarUiModel: AppBars.Bottom.UiModel = this.bottomBarUiModel,
            bottomSheetUiModel: BottomSheets.UiModel = this.bottomSheetUiModel,
            snackbarUiModel: Snackbars.UiModel = this.snackbarUiModel,
            dialogUiModel: Dialogs.UiModel = this.dialogUiModel,
            loaderUiModel: Loaders.UiModel = this.loaderUiModel,
        ): UiState =
            copy(
                topAppBarUiState = topAppBarUiState.copy(uiModel = topBarUiModel),
                containerUiState = containerUiState.copy(uiModel = containerUiModel),
                bottomBarUiState = bottomBarUiState.copy(uiModel = bottomBarUiModel),
                bottomSheetUiState = bottomSheetUiState.copy(uiModel = bottomSheetUiModel),
                snackbarUiState = snackbarUiState.copy(uiModel = snackbarUiModel),
                dialogUiState = dialogUiState.copy(uiModel = dialogUiModel),
                loaderUiState = loaderUiState.copy(uiModel = loaderUiModel),
            )

        companion object {
            fun empty(): UiState = UiState(
                topAppBarUiState = TopAppBarUiState.empty(),
                containerUiState = ContainerUiState.empty(),
                bottomBarUiState = BottomBarUiState.empty(),
                bottomSheetUiState = BottomSheetUiState(),
                snackbarUiState = SnackbarUiState.empty(),
                dialogUiState = DialogUiState.empty(),
                loaderUiState = LoaderUiState.empty()
            )
        }
    }

    fun UiState.topBarUiModel(): AppBars.Top.UiModel = topAppBarUiState.uiModel
    fun UiState.bottomBarUiModel(): AppBars.Bottom.UiModel = bottomBarUiState.uiModel
    fun UiState.bottomSheetUiModel(): BottomSheets.UiModel = bottomSheetUiState.uiModel
    fun UiState.snackbarUiModel(): Snackbars.UiModel = snackbarUiState.uiModel
    fun UiState.dialogUiModel(): Dialogs.UiModel = dialogUiState.uiModel
    fun UiState.loaderUiModel(): Loaders.UiModel = loaderUiState.uiModel

    fun UiState.uiModel(): UiModel = UiModel(
        topBarUiModel = topBarUiModel(),
        bottomBarUiModel = bottomBarUiModel(),
        bottomSheetUiModel = bottomSheetUiModel(),
        snackbarUiModel = snackbarUiModel(),
        dialogUiModel = dialogUiModel(),
        loaderUiModel = loaderUiModel(),
    )

    data class UiModel(
        val topBarUiModel: AppBars.Top.UiModel,
        val bottomBarUiModel: AppBars.Bottom.UiModel,
        val bottomSheetUiModel: BottomSheets.UiModel,
        val snackbarUiModel: Snackbars.UiModel,
        val dialogUiModel: Dialogs.UiModel,
        val loaderUiModel: Loaders.UiModel,
    )

    interface SubcomponentUiState<M> {
        val uiModel: M
    }

    data class ContainerUiModel(
        val color: @Composable ThemeColorScheme.() -> Color = { surface }
    ) {
        companion object {
            fun empty(): ContainerUiModel = ContainerUiModel()
        }
    }

    data class TopAppBarUiState(
        override val uiModel: AppBars.Top.UiModel,
    ) : SubcomponentUiState<AppBars.Top.UiModel> {
        companion object {
            fun empty(): TopAppBarUiState = TopAppBarUiState(uiModel = AppBars.Top.UiModel.empty())
        }
    }

    data class ContainerUiState(
        override val uiModel: ContainerUiModel
    ) : SubcomponentUiState<ContainerUiModel> {
        companion object {
            fun empty(): ContainerUiState = ContainerUiState(uiModel = ContainerUiModel.empty())
        }
    }

    data class BottomBarUiState(
        override val uiModel: AppBars.Bottom.UiModel
    ) : SubcomponentUiState<AppBars.Bottom.UiModel> {
        companion object {
            fun empty(): BottomBarUiState = BottomBarUiState(uiModel = AppBars.Bottom.UiModel.empty())
        }
    }

    data class BottomSheetUiState(
        override val uiModel: BottomSheets.UiModel = BottomSheets.UiModel(),
        val shape: @Composable (BottomSheetState) -> Shape = { bottomSheetState ->
            TopRoundedCornerShape(bottomSheetState.shapeCornerSize(idle = 24))
        },
        val peekHeight: Dp = 0.dp,
        val sheetValue: BottomSheetValue = BottomSheets.Collapsed
    ) : SubcomponentUiState<BottomSheets.UiModel> {
        fun expandedUiState(): BottomSheetUiState = copy(sheetValue = BottomSheets.Expanded)
        fun collapsedUiState(): BottomSheetUiState = copy(sheetValue = BottomSheets.Collapsed)
        val expanded: Boolean = sheetValue == Expanded
    }

    data class SnackbarUiState(
        override val uiModel: Snackbars.UiModel,
        val visible: Boolean = false,
    ) : SubcomponentUiState<Snackbars.UiModel> {
        companion object {
            fun empty(): SnackbarUiState = SnackbarUiState(uiModel = Snackbars.UiModel.empty())
        }
    }

    data class DialogUiState(
        override val uiModel: Dialogs.UiModel,
        val visible: Boolean = false,
        val enterTransition: EnterTransition = expandIn(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow,
                visibilityThreshold = null
            ),
            expandFrom = Alignment.Center
        ),
        val exitTransition: ExitTransition = shrinkOut(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow,
                visibilityThreshold = null
            ),
            shrinkTowards = Alignment.Center
        )
    ) : SubcomponentUiState<Dialogs.UiModel> {
        companion object {
            fun empty(): DialogUiState = DialogUiState(uiModel = Dialogs.UiModel.empty())
        }
    }

    data class LoaderUiState(
        override val uiModel: Loaders.UiModel,
        val visible: Boolean = false,
        val enterTransition: EnterTransition = fadeIn(animationSpec = tween(duration = 300)),
        val exitTransition: ExitTransition = fadeOut(animationSpec = tween(duration = 300)),
    ) : SubcomponentUiState<Loaders.UiModel> {
        companion object {
            fun empty(): LoaderUiState = LoaderUiState(uiModel = Loaders.UiModel.empty())
        }
    }

    @Composable
    fun bottomSheetScaffoldState(): BottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(initialValue = BottomSheets.Collapsed),
        snackbarHostState = SnackbarHostState()
    )
}