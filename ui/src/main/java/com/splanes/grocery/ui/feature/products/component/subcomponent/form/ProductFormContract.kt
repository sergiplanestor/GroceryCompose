package com.splanes.grocery.ui.feature.products.component.subcomponent.form

import com.splanes.grocery.ui.component.form.Forms
import com.splanes.grocery.ui.component.form.utils.NotNullOrBlank
import com.splanes.grocery.ui.component.form.utils.resultOf
import com.splanes.grocery.ui.utils.resources.Strings
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiEvent
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiModel
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiSideEffect

enum class ProductFormField(val position: Int) {
    Name(position = 1),
    Description(position = 2),
    Category(position = 3),
    Markets(position = 4),
    Prices(position = 5),
    IsHighlight(position = 6),
    IsAddToCurrent(position = 7),
    Frequency(position = 8),
}

data class ProductFormUiModel(
    val nameFormState: Forms.State<String> = Forms.Valid(value = ""),
    val descriptionFormState: Forms.State<String> = Forms.Valid(value = ""),
    val categoryFormState: Forms.State<String> = Forms.Valid(value = ""),
    val marketsFormState: Forms.State<String> = Forms.Valid(value = ""),
    val pricesFormState: Forms.State<String> = Forms.Valid(value = ""),
    val isHighlightFormState: Forms.State<String> = Forms.Valid(value = ""),
    val isAddToCurrentFormState: Forms.State<String> = Forms.Valid(value = ""),
    val frequencyFormState: Forms.State<String> = Forms.Valid(value = ""),
): UiModel

object ProductFormEvent : UiEvent
object ProductFormSideEffect : UiSideEffect

val NotNullOrBlankValidators: List<Forms.Validator<String>> = listOf(
    Forms.Validator(error = Strings.field_error_mandatory, Forms.Validator.NotNullOrBlank),
)

fun <T> Forms.State<T>.onChanged(value: T, validators: List<Forms.Validator<T>> = emptyList()): Forms.State<T> {
    val validatorResult = Forms.Validator.resultOf(validators, value)
    return when (this) {
        is Forms.Valid -> {
            when (validatorResult) {
                is Forms.Validator.Error -> Forms.Error(value, validatorResult.error)
                Forms.Validator.Valid -> copy(value)
            }
        }
        is Forms.Error -> {
            when (validatorResult) {
                is Forms.Validator.Error -> copy(value = value, message = validatorResult.error)
                Forms.Validator.Valid -> Forms.Valid(value)
            }
        }
    }
}