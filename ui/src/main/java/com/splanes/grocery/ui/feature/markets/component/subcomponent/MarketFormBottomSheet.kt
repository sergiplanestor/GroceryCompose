package com.splanes.grocery.ui.feature.markets.component.subcomponent

import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.splanes.grocery.ui.component.bottomsheet.BottomSheetCloseButton
import com.splanes.grocery.ui.component.bottomsheet.BottomSheetTitle
import com.splanes.grocery.ui.component.bottomsheet.BottomSheets
import com.splanes.grocery.ui.component.scaffold.Scaffolds
import com.splanes.grocery.ui.feature.markets.component.subcomponent.form.MarketOriginPickerForm
import com.splanes.grocery.ui.feature.markets.component.subcomponent.form.contract.MarketBottomSheetType
import com.splanes.grocery.ui.feature.markets.component.subcomponent.form.contract.MarketFormType
import com.splanes.grocery.ui.feature.markets.component.subcomponent.form.contract.MarketFormType.FindNear
import com.splanes.grocery.ui.feature.markets.component.subcomponent.form.contract.MarketFormType.Manual
import com.splanes.grocery.ui.feature.markets.component.subcomponent.form.contract.MarketFormType.OriginPicker
import com.splanes.grocery.ui.feature.markets.component.subcomponent.form.contract.MarketOriginType.Location
import com.splanes.grocery.ui.feature.markets.component.subcomponent.form.contract.MarketOriginType.ManualForm
import com.splanes.grocery.ui.feature.markets.component.subcomponent.form.location.MarketByLocationSearch
import com.splanes.grocery.ui.feature.markets.component.subcomponent.form.manual.MarketByManualForm
import com.splanes.grocery.ui.utils.resources.dp
import com.splanes.grocery.ui.utils.resources.string
import com.splanes.grocery.ui.utils.shape.TopRoundedCornerShape

fun MarketBottomSheetType.marketBottomSheetUiState(
    onRequestLocationPermissions: (onGranted: @Composable () -> Unit) -> Unit,
    onBottomSheetChange: (Scaffolds.BottomSheetUiState) -> Unit,
    onClose: () -> Unit
): Scaffolds.BottomSheetUiState =
    when (this) {
        is MarketFormType -> marketFormBottomSheetUiState(
            type = this,
            onBottomSheetChange = onBottomSheetChange,
            onRequestLocationPermissions = onRequestLocationPermissions,
            onClose = onClose
        )
    }

@OptIn(ExperimentalMaterialApi::class)
fun marketFormBottomSheetUiState(
    type: MarketFormType,
    onBottomSheetChange: (Scaffolds.BottomSheetUiState) -> Unit,
    onRequestLocationPermissions: (onGranted: @Composable () -> Unit) -> Unit,
    onClose: () -> Unit
): Scaffolds.BottomSheetUiState {
    return Scaffolds.BottomSheetUiState(
        uiModel = BottomSheets.UiModel(
            id = type.id,
            uiContent = BottomSheets.UiContent(
                title = {
                    BottomSheetTitle(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = dp { mediumSmall }),
                        text = string { type.titleRes },
                        align = TextAlign.Start
                    )
                },
                close = { BottomSheetCloseButton(onClick = onClose) },
                content = {
                    MarketFormBottomSheetContent(
                        type = type,
                        onBottomSheetChange = onBottomSheetChange,
                        onRequestLocationPermissions = onRequestLocationPermissions,
                        onClose = onClose
                    )
                }
            )
        ),
        sheetValue = BottomSheets.Expanded,
        shape = { TopRoundedCornerShape(size = 28) }
    )
}

@Composable
private fun MarketFormBottomSheetContent(
    type: MarketFormType,
    onBottomSheetChange: (Scaffolds.BottomSheetUiState) -> Unit,
    onRequestLocationPermissions: (onGranted: @Composable () -> Unit) -> Unit,
    onClose: () -> Unit
) {
    when (type) {
        FindNear -> {
            MarketByLocationSearch()
        }
        Manual -> {
            MarketByManualForm()
        }
        OriginPicker -> {
            MarketFormOriginPickerContent(
                onBottomSheetChange = onBottomSheetChange,
                onRequestLocationPermissions = { onGranted ->
                    onClose()
                    onRequestLocationPermissions(onGranted)
                },
                onClose = onClose
            )
        }
    }
}

@Composable
private fun MarketFormOriginPickerContent(
    onBottomSheetChange: (Scaffolds.BottomSheetUiState) -> Unit,
    onRequestLocationPermissions: (@Composable () -> Unit) -> Unit,
    onClose: () -> Unit
) {
    MarketOriginPickerForm(
        onOriginSelected = { origin ->
            val openMarketFormBottomSheet = {
                onBottomSheetChange(
                    marketFormBottomSheetUiState(
                        type = origin.toBottomSheetType(),
                        onBottomSheetChange = onBottomSheetChange,
                        onRequestLocationPermissions = onRequestLocationPermissions,
                        onClose = onClose
                    )
                )
            }
            when (origin) {
                Location -> onRequestLocationPermissions { openMarketFormBottomSheet() }
                ManualForm -> openMarketFormBottomSheet()
            }
        }
    )
}