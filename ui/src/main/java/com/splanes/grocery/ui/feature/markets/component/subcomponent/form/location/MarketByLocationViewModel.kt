package com.splanes.grocery.ui.feature.markets.component.subcomponent.form.location

import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiState
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.viewmodel.ComponentViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MarketByLocationViewModel @Inject constructor(

): ComponentViewModel<MarketByLocationUiModel, MarketByLocationEvent, MarketByLocationEffect>() {

    override val uiStateAtStart: UiState<MarketByLocationUiModel>
        get() = UiState.Ready(MarketLocation.CheckPermissions)

    override fun onUiEventHandled(uiEvent: MarketByLocationEvent) {
        when (uiEvent) {
            StartSearch -> onNewSearch()
        }
    }

    private fun onNewSearch() {
        updateUiModel { MarketLocation.Searching }
    }
}