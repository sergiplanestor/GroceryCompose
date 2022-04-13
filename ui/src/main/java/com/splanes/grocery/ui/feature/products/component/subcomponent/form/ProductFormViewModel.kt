package com.splanes.grocery.ui.feature.products.component.subcomponent.form

import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiState
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.viewmodel.ComponentViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductFormViewModel @Inject constructor(

) : ComponentViewModel<ProductFormUiModel, ProductFormEvent, ProductFormSideEffect>(){

    override val uiStateAtStart: UiState<ProductFormUiModel>
        get() = UiState.Ready(ProductFormUiModel())

    override fun onUiEventHandled(uiEvent: ProductFormEvent) {

    }

    fun onFieldUpdate(block: ProductFormUiModel.() -> ProductFormUiModel) {
        updateUiModel(block)
    }
}