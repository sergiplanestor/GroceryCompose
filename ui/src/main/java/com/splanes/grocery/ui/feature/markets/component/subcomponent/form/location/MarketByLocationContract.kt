package com.splanes.grocery.ui.feature.markets.component.subcomponent.form.location

import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiEvent
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiModel
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiSideEffect

sealed class MarketByLocationUiModel : UiModel
sealed class MarketLocation : MarketByLocationUiModel() {
    object CheckPermissions: MarketLocation()
    object Searching: MarketLocation()
    data class Results(val results: List<Any/* TODO */>): MarketLocation()
}
sealed class MarketByLocationEvent : UiEvent
object StartSearch : MarketByLocationEvent()

object MarketByLocationEffect : UiSideEffect