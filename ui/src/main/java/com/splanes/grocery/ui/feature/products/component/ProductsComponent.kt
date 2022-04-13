package com.splanes.grocery.ui.feature.products.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.splanes.grocery.ui.component.bottomsheet.BottomSheets
import com.splanes.grocery.ui.component.icons.Icons
import com.splanes.grocery.ui.component.icons.rounded
import com.splanes.grocery.ui.component.spacer.row.Space
import com.splanes.grocery.ui.feature.mainscreen.viewmodel.MainScreenViewModel
import com.splanes.grocery.ui.feature.products.component.subcomponent.productFormBottomSheetUiState
import com.splanes.grocery.ui.utils.resources.color
import com.splanes.grocery.ui.utils.resources.dp
import com.splanes.grocery.ui.utils.resources.titleStyle
import com.splanes.grocery.utils.scope.apply
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductsComponent(
    navHostController: NavHostController,
    mainScreenViewModel: MainScreenViewModel,
    onBottomSheet: (BottomSheetValue) -> Unit
) {
    val productsScrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(productsScrollState)
        ) {
            // Find / Sort / Group products ...

            // Product list

        }
        AddProductFloatingButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = dp { large }),
            scrollState = productsScrollState,
            onClick = {
                mainScreenViewModel.onBottomSheetStateChanged {
                    copy(
                        bottomSheetUiState = productFormBottomSheetUiState(
                            onClose = { onBottomSheet(BottomSheets.Collapsed) }
                        )
                    )
                }
                onBottomSheet(BottomSheets.Expanded)
            }
        )
    }
}

@Composable
fun AddProductFloatingButton(
    modifier: Modifier,
    scrollState: ScrollState,
    onClick: () -> Unit
) {
    val iconSize by animateDpAsState(targetValue = (if (scrollState.isScrollInProgress) 26 else 20).dp)
    val iconColor by animateColorAsState(targetValue = color { onTertiary }.alpha(if (scrollState.isScrollInProgress) .6 else .8))
    val buttonColor by animateColorAsState(
        targetValue = color { tertiary.apply(scrollState.isScrollInProgress) { alpha(.35) } },
        animationSpec = tween(durationMillis = if (scrollState.isScrollInProgress) 1000 else 100)
    )
    val buttonElevation by animateFloatAsState(
        targetValue = (if (scrollState.isScrollInProgress) 0 else 6).toFloat(),
        animationSpec = tween(durationMillis = if (scrollState.isScrollInProgress) 100 else 1000)
    )
    val buttonCornerSize by animateDpAsState(
        targetValue = (if (scrollState.isScrollInProgress) 40 else 16).dp,
        animationSpec = tween(durationMillis = 750)
    )

    FloatingActionButton(
        modifier = modifier,
        elevation = FloatingActionButtonDefaults.elevation(buttonElevation.dp, buttonElevation.dp),
        shape = RoundedCornerShape(buttonCornerSize),
        containerColor = buttonColor,
        onClick = onClick,
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Icons.Icon(
                    modifier = Modifier.padding(vertical = dp { mediumSmall }, horizontal = dp { medium }),
                    source = Icons.rounded { Add },
                    size = iconSize,
                    color = iconColor
                )
                AnimatedVisibility(
                    modifier = Modifier.padding(
                        top = dp { mediumSmall },
                        bottom = dp { mediumSmall },
                        end = dp { medium }
                    ),
                    visible = !scrollState.isScrollInProgress,
                    enter = fadeIn(animationSpec = tween(durationMillis = 750)) +
                            expandHorizontally(
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioLowBouncy,
                                    stiffness = Spring.StiffnessMedium
                                )
                            ),
                    exit = fadeOut(animationSpec = tween(durationMillis = 750)) +
                            shrinkHorizontally(
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioLowBouncy,
                                    stiffness = Spring.StiffnessHigh
                                )
                            ),
                ) {
                    Space { mediumSmall }
                    Text(
                        text = "Add product",
                        style = titleStyle { large },
                        color = color { onTertiary }.alpha(.8)
                    )
                }
            }
        }
    )
}
