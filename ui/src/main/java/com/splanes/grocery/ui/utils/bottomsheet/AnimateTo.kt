package com.splanes.grocery.ui.utils.bottomsheet

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.BottomSheetValue.Collapsed
import androidx.compose.material.BottomSheetValue.Expanded
import androidx.compose.material.ExperimentalMaterialApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
fun CoroutineScope.bottomSheetAnimateTo(
    bottomSheetState: BottomSheetState,
    value: BottomSheetValue,
    spec: AnimationSpec<Float> = spring(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessMedium
    )
) = launch { bottomSheetState.animateTo(value, spec) }

@OptIn(ExperimentalMaterialApi::class)
fun CoroutineScope.bottomSheetInvertState(
    bottomSheetState: BottomSheetState,
    spec: AnimationSpec<Float> = bottomSheetState.animSpec()
) = bottomSheetAnimateTo(bottomSheetState, !bottomSheetState.currentValue, spec)

@OptIn(ExperimentalMaterialApi::class)
fun CoroutineScope.bottomSheetExpand(
    bottomSheetState: BottomSheetState,
    spec: AnimationSpec<Float> = BottomSheetExpandAnimSpec
) = bottomSheetAnimateTo(bottomSheetState, Expanded, spec)

@OptIn(ExperimentalMaterialApi::class)
fun CoroutineScope.bottomSheetCollapse(
    bottomSheetState: BottomSheetState,
    spec: AnimationSpec<Float> = BottomSheetCollapseAnimSpec
) = bottomSheetAnimateTo(bottomSheetState, Collapsed, spec)

val BottomSheetExpandAnimSpec: AnimationSpec<Float> by lazy {
    spring(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessMedium
    )
}

val BottomSheetCollapseAnimSpec: AnimationSpec<Float> by lazy {
    spring(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessMediumLow
    )
}

@OptIn(ExperimentalMaterialApi::class)
fun BottomSheetState.animSpec(): AnimationSpec<Float> =
    if (isExpanded) BottomSheetExpandAnimSpec else BottomSheetCollapseAnimSpec

@OptIn(ExperimentalMaterialApi::class)
operator fun BottomSheetValue.not(): BottomSheetValue = if (this == Expanded) Collapsed else Expanded