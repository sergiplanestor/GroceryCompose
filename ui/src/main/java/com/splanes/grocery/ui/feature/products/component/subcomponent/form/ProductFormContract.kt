package com.splanes.grocery.ui.feature.products.component.subcomponent.form

import com.splanes.grocery.ui.component.form.model.Forms

data class ProductFormUiState(
    val name: Forms.State<String> = Forms.Idle(value = ""),
    val description: Forms.State<String?> = Forms.Idle(value = ""),
    val category: Forms.State<String?> = Forms.Idle(value = ""),
    val markets: Forms.State<String?> = Forms.Idle(value = ""),
    val prices: Forms.State<String?> = Forms.Idle(value = ""),
    val isHighlight: Forms.State<String?> = Forms.Idle(value = ""),
    val isAddToCurrent: Forms.State<String?> = Forms.Idle(value = ""),
    val frequency: Forms.State<String?> = Forms.Idle(value = ""),
)