package com.splanes.grocery.ui.feature.mainscreen.contract

import com.splanes.grocery.domain.feature.product.model.GroceryList
import com.splanes.grocery.ui.component.bar.AppBars
import com.splanes.grocery.ui.component.scaffold.Scaffolds
import com.splanes.grocery.ui.feature.mainscreen.component.subcomponent.bottombar.BottomBarItem
import com.splanes.grocery.ui.utils.resources.Strings
import com.splanes.grocery.ui.utils.resources.color
import com.splanes.grocery.ui.utils.resources.string
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiEvent
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiModel
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiSideEffect
import com.splanes.toolkit.compose.ui.components.common.utils.color.composite

sealed class MainScreenUiModel(open val scaffoldUiState: Scaffolds.UiState = ScaffoldInitialUiState) : UiModel {
    data class Idle(
        override val scaffoldUiState: Scaffolds.UiState = ScaffoldInitialUiState,
        val groceryLists: List<GroceryList>
    ) : MainScreenUiModel(scaffoldUiState = scaffoldUiState)

    data class Skeleton(override val scaffoldUiState: Scaffolds.UiState = ScaffoldInitialUiState) :
        MainScreenUiModel(scaffoldUiState = scaffoldUiState)

    data class Empty(override val scaffoldUiState: Scaffolds.UiState = ScaffoldInitialUiState) :
        MainScreenUiModel(scaffoldUiState = scaffoldUiState)
}

sealed class MainScreenEvent : UiEvent {
}

sealed class MainScreenSideEffect : UiSideEffect
sealed class Redirection : MainScreenSideEffect() {
}

private val ScaffoldInitialUiState
    get() = Scaffolds.UiState(
        topAppBarUiState = Scaffolds.TopAppBarUiState(
            uiModel = AppBars.Top.UiModel(
                title = { string { Strings.grocery } },
                colors = {
                    AppBars.Top.colorsSmall(
                        containerColor = color { primary },
                        titleColor = color { onPrimary }
                    )
                }
            )
        ),
        containerUiState = Scaffolds.ContainerUiState(
            uiModel = Scaffolds.ContainerUiModel { onSurface.composite(surface, .15) }
        ),
        bottomBarUiState = Scaffolds.BottomBarUiState(
            uiModel = AppBars.Bottom.UiModel(
                items = BottomBarItems,
                selected = BottomBarItems.first { it.id == BottomBarItem.GroceryListDashboard.id }
            )
        )
    )

private val BottomBarItems: List<AppBars.Bottom.UiModel.Item> = BottomBarItem.values().map {
    AppBars.Bottom.UiModel.Item(id = it.id, label = it.labelRes, icon = it.icon)
}