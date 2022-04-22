package com.splanes.grocery.ui.feature.markets.component.subcomponent.form.contract

import androidx.annotation.StringRes
import com.splanes.grocery.ui.utils.resources.Strings

private const val MARKET_ORIGIN_PICKER_BOTTOM_SHEET_ID = "BottomSheet.Market.Form.OriginPicker"
private const val MARKET_MANUAL_FORM_BOTTOM_SHEET_ID = "BottomSheet.Market.Form.Manual"
private const val MARKET_FIND_NEAR_BOTTOM_SHEET_ID = "BottomSheet.Market.Form.FindNear"

sealed class MarketBottomSheetType
sealed class MarketFormType(val id: String, @StringRes val titleRes: Int): MarketBottomSheetType() {
    object OriginPicker : MarketFormType(id = MARKET_ORIGIN_PICKER_BOTTOM_SHEET_ID, titleRes = Strings.market_form_origin_picker_title)
    object Manual : MarketFormType(id = MARKET_MANUAL_FORM_BOTTOM_SHEET_ID, titleRes = Strings.market_form_manual_title)
    object FindNear : MarketFormType(id = MARKET_FIND_NEAR_BOTTOM_SHEET_ID, titleRes = Strings.market_form_find_near_title)
}

enum class MarketOriginType {
    Location,
    ManualForm;

    fun toBottomSheetType(): MarketFormType = when(this) {
        Location -> MarketFormType.FindNear
        ManualForm -> MarketFormType.Manual
    }
}