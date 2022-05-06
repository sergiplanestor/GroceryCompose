package com.splanes.grocery.ui.component.form.market.brandpicker

import com.splanes.grocery.utils.logger.utils.throwMessageNonFatal

internal fun MarketBrandPicker.UiModel.indexOfLast(): Int = dataSet.lastIndex

internal fun MarketBrandPicker.UiModel.editing(): Boolean = editState.enabled()

internal fun MarketBrandPicker.UiModel.indexOfSelected(fallback: Int = -1): Int =
    dataSet.indexOf(dataSet.find { i -> i.id == selected?.id }).takeUnless { it == -1 } ?: fallback

internal fun MarketBrandPicker.UiModel.itemAt(index: Int): MarketBrandPicker.ItemUiModel =
    runCatching { dataSet[index] }.getOrElse { e ->
        throwMessageNonFatal { e.message }
        middle()
    }

internal fun MarketBrandPicker.UiModel.middle(): MarketBrandPicker.ItemUiModel =
    itemAt(dataSet.size / 2)