package com.splanes.grocery.ui.feature.products.component.subcomponent.form

import com.splanes.grocery.ui.component.form.model.Forms
import com.splanes.grocery.ui.component.form.model.Forms.satisfies
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
    val nameFormState: Forms.State<String> = Forms.Idle(value = ""),
    val descriptionFormState: Forms.State<String> = Forms.Idle(value = ""),
    val categoryFormState: Forms.State<String> = Forms.Idle(value = ""),
    val marketsFormState: Forms.State<String> = Forms.Idle(value = ""),
    val pricesFormState: Forms.State<String> = Forms.Idle(value = ""),
    val isHighlightFormState: Forms.State<String> = Forms.Idle(value = ""),
    val isAddToCurrentFormState: Forms.State<String> = Forms.Idle(value = ""),
    val frequencyFormState: Forms.State<String> = Forms.Idle(value = ""),
): UiModel

object ProductFormEvent : UiEvent
object ProductFormSideEffect : UiSideEffect

val NotNullOrBlankValidators: List<Forms.Validator<String>> = listOf(
    Forms.Validator.NotNull(error = Strings.field_error_mandatory),
    Forms.Validator.NotBlank(error = Strings.field_error_mandatory),
)

fun <T> Forms.State<T>.onChanged(value: T, validators: List<Forms.Validator<T>> = emptyList()): Forms.State<T> {
    val validatorResult = value.satisfies(validators)
    return when (this) {
        is Forms.Idle -> {
            when (validatorResult) {
                is Forms.Validator.Error -> Forms.Error(value, validatorResult.error)
                Forms.Validator.Valid -> copy(value)
            }
        }
        is Forms.Error -> {
            when (validatorResult) {
                is Forms.Validator.Error -> copy(value = value, message = validatorResult.error)
                Forms.Validator.Valid -> Forms.Idle(value)
            }
        }
    }
}