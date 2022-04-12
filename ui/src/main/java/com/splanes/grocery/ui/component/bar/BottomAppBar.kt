package com.splanes.grocery.ui.component.bar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.BottomNavigationDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.contentColorFor
import androidx.compose.material.primarySurface
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.splanes.grocery.ui.component.icons.Icons
import com.splanes.grocery.ui.utils.resources.bodyStyle
import com.splanes.grocery.ui.utils.resources.dp
import com.splanes.grocery.ui.utils.resources.minus
import com.splanes.grocery.ui.utils.resources.string
import com.splanes.grocery.ui.utils.shape.TopRoundedCornerShape
import com.splanes.grocery.utils.scope.apply
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha
import com.splanes.toolkit.compose.ui.theme.feature.colors.ThemeColorScheme
import com.splanes.toolkit.compose.ui.theme.utils.accessors.Colors

@Composable
fun BottomAppBar(
    uiModel: AppBars.Bottom.UiModel,
    modifier: Modifier = Modifier,
    shape: Shape = TopRoundedCornerShape(size = dp { small }),
    containerColor: @Composable ThemeColorScheme.() -> Color = { primary },
    contentColor: @Composable ThemeColorScheme.() -> Color = { onPrimary },
    elevation: Dp = BottomNavigationDefaults.Elevation,
    onSelected: (AppBars.Bottom.UiModel.Item) -> Unit
) {

    var itemSelectedState by remember { mutableStateOf(uiModel.selected) }
    val contentBackgroundColor = contentColor(Colors)

    BarContainer(
        modifier = modifier,
        containerColor = containerColor(Colors),
        contentColor = contentBackgroundColor,
        elevation = elevation,
        shape = shape
    ) {
        val barItems = buildBottomBarItemList(uiModel.items)
        barItems.forEach { item ->
            BarElement(
                item = item,
                color = contentBackgroundColor,
                isSelected = itemSelectedState == item,
                onClick = {
                    if (item is AppBars.Bottom.UiModel.Item && itemSelectedState != item) {
                        itemSelectedState = item
                        onSelected(item)
                    }
                }
            )
        }
    }
}

@Composable
fun BarContainer(
    modifier: Modifier = Modifier,
    shape: Shape = TopRoundedCornerShape(size = dp { small }),
    containerColor: Color = MaterialTheme.colors.primarySurface,
    contentColor: Color = contentColorFor(containerColor),
    elevation: Dp = BottomNavigationDefaults.Elevation,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        modifier = modifier,
        color = containerColor,
        contentColor = contentColor,
        elevation = elevation,
        shape = shape
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(BottomNavigationHeight)
                .selectableGroup(),
            horizontalArrangement = Arrangement.SpaceBetween,
            content = content
        )
    }
}

@Composable
fun RowScope.BarElement(
    item: AppBars.Bottom.ContentUiModel,
    color: Color,
    isSelected: Boolean = false,
    onClick: () -> Unit,
) {
    when (item) {
        AppBars.Bottom.UiModel.Divider -> {
            Divider(
                modifier = Modifier
                    .size(width = .5.dp, height = 34.dp)
                    .align(Alignment.CenterVertically),
                color = color.alpha(.15)
            )
        }
        is AppBars.Bottom.UiModel.Item -> {
            Item(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                item = item,
                isSelected = isSelected,
                color = color,
                onClick = onClick
            )
        }
    }
}

@Composable
fun Item(
    item: AppBars.Bottom.UiModel.Item,
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val iconSize by animateDpAsState(
        targetValue = if (!isSelected) ItemIconSize else ItemIconSelectedSize,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    val iconColor by animateColorAsState(
        targetValue = color.apply(condition = !isSelected) { alpha(.5) },
        animationSpec = tween(durationMillis = 400)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 2.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(color = color),
                onClick = onClick
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        with(item) {
            Icons.Icon(
                source = icon,
                size = iconSize,
                color = iconColor
            )

            AnimatedVisibility(
                modifier = Modifier.wrapContentSize(),
                visible = isSelected,
                enter = expandVertically(
                    expandFrom = Alignment.Top,
                    animationSpec = tween(durationMillis = 50)
                ),
                exit = shrinkVertically(
                    shrinkTowards = Alignment.Top,
                    animationSpec = tween(durationMillis = 50)
                )
            ) {
                Text(
                    modifier = Modifier.padding(top = 3.dp, bottom = 2.dp),
                    text = string { label },
                    color = color.alpha(.8),
                    style = bodyStyle { medium }.run { copy(fontSize = fontSize - 2) },
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

private fun buildBottomBarItemList(items: List<AppBars.Bottom.UiModel.Item>): List<AppBars.Bottom.ContentUiModel> =
    buildList {
        items.forEachIndexed { index, item ->
            add(item)
            if (index != items.lastIndex) add(AppBars.Bottom.UiModel.Divider)
        }
    }

val BottomNavigationHeight by lazy { 60.dp }
val ItemIconSize by lazy { 32.dp }
val ItemIconSelectedSize by lazy { 28.dp }