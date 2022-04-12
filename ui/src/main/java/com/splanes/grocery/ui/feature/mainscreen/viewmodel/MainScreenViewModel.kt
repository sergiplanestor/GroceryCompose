package com.splanes.grocery.ui.feature.mainscreen.viewmodel

import com.splanes.grocery.ui.component.scaffold.Scaffolds
import com.splanes.grocery.ui.feature.mainscreen.component.subcomponent.bottombar.BottomBarItem
import com.splanes.grocery.ui.feature.mainscreen.contract.MainScreenEvent
import com.splanes.grocery.ui.feature.mainscreen.contract.MainScreenSideEffect
import com.splanes.grocery.ui.feature.mainscreen.contract.MainScreenUiModel
import com.splanes.grocery.ui.feature.mainscreen.contract.MainScreenUiModel.Empty
import com.splanes.grocery.ui.feature.mainscreen.contract.MainScreenUiModel.Idle
import com.splanes.grocery.ui.feature.mainscreen.contract.MainScreenUiModel.Skeleton
import com.splanes.grocery.ui.utils.resources.string
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiState
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.viewmodel.ComponentViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(

) : ComponentViewModel<MainScreenUiModel, MainScreenEvent, MainScreenSideEffect>() {

    override val uiStateAtStart: UiState<MainScreenUiModel>
        get() = UiState.Ready(Skeleton())

    override fun onUiEventHandled(uiEvent: MainScreenEvent) {
    }

    fun onBottomBarRedirect(item: BottomBarItem) = updateUiModel { from ->
        val scaffoldUiState = from.scaffoldUiState.copy(
            topBarUiModel = from.scaffoldUiState.topBarUiModel.copy(title = { string { item.labelRes } }),
            bottomBarUiModel = from.scaffoldUiState.bottomBarUiModel.run {
                copy(selected = items.find { it.id == item.id })
            }
        )
        when (from) {
            is Empty -> from.copy(scaffoldUiState = scaffoldUiState)
            is Idle -> from.copy(scaffoldUiState = scaffoldUiState)
            is Skeleton -> from.copy(scaffoldUiState = scaffoldUiState)
        }
    }

    fun updateScaffoldState(updater: Scaffolds.UiState.() ->  Scaffolds.UiState) = updateUiModel { from ->
        when (from) {
            is Empty -> from.copy(scaffoldUiState = from.scaffoldUiState.updater())
            is Idle -> from.copy(scaffoldUiState = from.scaffoldUiState.updater())
            is Skeleton -> from.copy(scaffoldUiState = from.scaffoldUiState.updater())
        }
    }
}