package com.splanes.grocery.ui.component.bottomsheet

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
object BottomSheets {

    val Expanded: BottomSheetValue = BottomSheetValue.Expanded
    val Collapsed: BottomSheetValue = BottomSheetValue.Collapsed

    data class UiModel(
        val scrollable: Boolean = true,
        val alignment: Alignment.Horizontal = Alignment.CenterHorizontally,
        val uiContent: UiContent = UiContent(content = {})
    )

    data class UiContent(
        val title: @Composable RowScope.() -> Unit = {},
        val close: @Composable RowScope.() -> Unit = {},
        val content: @Composable ColumnScope.(ScrollState?) -> Unit
    )

    @Composable
    fun BottomSheetState.shapeCornerSize(full: Int = 0, idle: Int = 16): Dp = if (targetValue == Expanded) {
        idle - (idle * this.progress.fraction)
    } else {
        full + (idle * this.progress.fraction)
    }.dp
}
