package com.splanes.grocery.ui.utils.bottomsheet

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun rememberBottomSheetState(
    state: BottomSheetValue = BottomSheetValue.Collapsed,
    animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec,
): BottomSheetState = remember {
    BottomSheetState(state, animationSpec)
}