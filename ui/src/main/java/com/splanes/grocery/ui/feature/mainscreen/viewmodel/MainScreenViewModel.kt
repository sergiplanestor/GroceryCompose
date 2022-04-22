package com.splanes.grocery.ui.feature.mainscreen.viewmodel

import androidx.compose.material.ExperimentalMaterialApi
import com.splanes.grocery.ui.component.scaffold.Scaffolds
import com.splanes.grocery.ui.feature.mainscreen.component.subcomponent.bottombar.BottomBarItem
import com.splanes.grocery.ui.feature.mainscreen.contract.MainScreenEvent
import com.splanes.grocery.ui.feature.mainscreen.contract.MainScreenSideEffect
import com.splanes.grocery.ui.feature.mainscreen.contract.MainScreenUiModel
import com.splanes.grocery.ui.feature.mainscreen.contract.MainScreenUiModel.Empty
import com.splanes.grocery.ui.feature.mainscreen.contract.MainScreenUiModel.Idle
import com.splanes.grocery.ui.feature.mainscreen.contract.MainScreenUiModel.Skeleton
import com.splanes.grocery.ui.feature.mainscreen.contract.OnBottomBarItemClick
import com.splanes.grocery.ui.utils.resources.string
import com.splanes.grocery.ui.utils.viewmodel.uiModel
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiState
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.viewmodel.ComponentViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(

) : ComponentViewModel<MainScreenUiModel, MainScreenEvent, MainScreenSideEffect>() {

    override val uiStateAtStart: UiState<MainScreenUiModel>
        get() = UiState.Ready(Skeleton())

    val scaffoldUiState: Scaffolds.UiState get() = uiModel().scaffoldUiState

    override fun onUiEventHandled(uiEvent: MainScreenEvent) {
        with(uiEvent) {
            when (this) {
                is OnBottomBarItemClick -> onBottomBarRedirect(item)
            }
        }
    }

    private fun onBottomBarRedirect(item: BottomBarItem) = updateUiModel { from ->
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

    fun onScaffoldUiStateChanged(updater: Scaffolds.UiState.() -> Scaffolds.UiState) {
        updateUiModel { from ->
            val newScaffoldState = from.scaffoldUiState.updater()
            when (from) {
                is Empty -> from.copy(scaffoldUiState = newScaffoldState)
                is Idle -> from.copy(scaffoldUiState = newScaffoldState)
                is Skeleton -> from.copy(scaffoldUiState = newScaffoldState)
            }
        }
    }
}