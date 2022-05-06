package com.splanes.grocery.ui.component.form.market.brandpicker

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import com.splanes.grocery.ui.component.icons.Icons
import com.splanes.grocery.ui.utils.resources.palette
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha

object MarketBrandPicker {
    data class UiModel(
        val dataSet: List<ItemUiModel>,
        val selected: ItemUiModel? = null,
        val editState: EditState = EditState.Disabled,
    ) {
        val uiState: UiState
            get() = when {
                editState == EditState.Enabled -> UiState.Editing
                selected != null -> UiState.Idle
                else -> UiState.Placeholder
            }
    }

    data class ItemUiModel(
        val id: String,
        val icon: Icons.Source,
        val name: String,
    )

    enum class EditState {
        Enabled, Disabled;

        fun enabled(): Boolean = this == Enabled

        operator fun not(): EditState =
            when (this) {
                Enabled -> Disabled
                Disabled -> Enabled
            }
    }

    enum class UiState { Editing, Idle, Placeholder }

    data class Colors(
        val label: Color,
        val name: Color,
        val pagerCard: Color,
        val pagerCardSelected: Color,
        val container: Color,
        val content: Color,
    )

    @Composable
    fun colors(
        label: Color = palette { onTertiaryContainer.alpha(.7).compositeOver(onSurface) },
        name: Color = palette { onTertiaryContainer.alpha(.7).compositeOver(onSurface) },
        pagerCard: Color = palette { tertiaryContainer.alpha(.7).compositeOver(surface) },
        pagerCardSelected: Color = palette { tertiary.alpha(.7).compositeOver(surface) },
        container: Color = palette { tertiaryContainer.alpha(.7).compositeOver(surface) },
        content: Color = palette { onTertiaryContainer.alpha(.7).compositeOver(background) },
    ): Colors =
        Colors(label, name, pagerCard, pagerCardSelected, container, content)
}