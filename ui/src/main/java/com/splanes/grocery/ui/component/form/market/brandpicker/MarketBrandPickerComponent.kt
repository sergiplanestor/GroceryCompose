package com.splanes.grocery.ui.component.form.market.brandpicker

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.rounded.DriveFileRenameOutline
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.splanes.grocery.domain.feature.market.model.Market
import com.splanes.grocery.ui.component.form.market.brandpicker.MarketBrandPicker.Colors
import com.splanes.grocery.ui.component.form.market.brandpicker.MarketBrandPicker.UiState.Editing
import com.splanes.grocery.ui.component.form.market.brandpicker.MarketBrandPicker.UiState.Idle
import com.splanes.grocery.ui.component.form.market.brandpicker.MarketBrandPicker.UiState.Placeholder
import com.splanes.grocery.ui.component.icons.Icons
import com.splanes.grocery.ui.component.icons.Icons.Painter
import com.splanes.grocery.ui.component.icons.rounded
import com.splanes.grocery.ui.component.spacer.column.Space
import com.splanes.grocery.ui.component.spacer.row.Space
import com.splanes.grocery.ui.component.spacer.row.Weight
import com.splanes.grocery.ui.utils.anim.Animations
import com.splanes.grocery.ui.utils.resources.Zero
import com.splanes.grocery.ui.utils.resources.Strings
import com.splanes.grocery.ui.utils.resources.dp
import com.splanes.grocery.ui.utils.resources.painter
import com.splanes.grocery.ui.utils.resources.palette
import com.splanes.grocery.ui.utils.resources.shape
import com.splanes.grocery.ui.utils.resources.string
import com.splanes.grocery.ui.utils.resources.titleStyle
import com.splanes.grocery.ui.utils.ripple.RippleStyle
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MarketBrandPicker(
    model: MarketBrandPicker.UiModel,
    onPageChanged: (page: Int) -> Unit,
    onEditStateChanged: () -> Unit,
    modifier: Modifier = Modifier,
    colors: Colors = MarketBrandPicker.colors(),
    pagerState: PagerState = rememberPagerState(initialPage = model.indexOfSelected(fallback = model.dataSet.size / 2))
) {
    var currentPage by remember { mutableStateOf(pagerState.currentPage) }

    Column(modifier = modifier.fillMaxWidth()) {
        Label(color = colors.label)
        Space { mediumSmall }
        Crossfade(targetState = model.uiState) { state  ->
            when(state) {
                Editing -> Pager(pagerState = pagerState, count = model.dataSet.size) { index ->
                    with(model.itemAt(index)) {
                        val color by Animations.colorAsState(
                            condition = currentPage == index,
                            colorOnTrue = colors.pagerCardSelected,
                            colorOnFalse = colors.pagerCard
                        )
                        val scale by Animations.floatAsState(
                            condition = currentPage == index,
                            onTrue = 1.0,
                            onFalse = 0.75
                        )
                        Card(
                            modifier = Modifier.scale(scale = scale),
                            color = color,
                            onClick = onEditStateChanged,
                            icon = { Icon(icon = icon, description = name) },
                            text = { Name(name = name, color = colors.name) }
                        )
                    }
                }
                Idle -> with(model.itemAt(model.indexOfSelected())) {
                    Card(
                        color = colors.container.alpha(.25).compositeOver(palette { surface }),
                        onClick = onEditStateChanged,
                        border = BorderStroke(1.dp, color = colors.pagerCardSelected),
                        elevation = Dp.Zero,
                        icon = { Icon(icon = icon, description = name) },
                        text = { Name(name = name, color = colors.name) },
                        trailingButton = { EditButton(colors = colors, onClick = onEditStateChanged) }
                    )
                }
                Placeholder -> Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Space { small }
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Selected -- TODO",
                        style = titleStyle { small },
                        color = colors.name
                    )
                    Weight(value = .5)
                    RippleStyle(color = palette { tertiary }) {
                        Icons.Icon(
                            source = Icons.rounded { DriveFileRenameOutline },
                            size = 20.dp,
                            color = palette { onSurface },
                            onClick = onEditStateChanged
                        )
                    }
                    Space { small }
                }
            }
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        currentPage = pagerState.currentPage
        onPageChanged(pagerState.currentPage)
    }
}

@Composable
private fun Label(color: Color) {
    Text(
        text = string { Strings.market_form_brand_label },
        style = titleStyle(),
        color = color
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun Pager(
    pagerState: PagerState,
    count: Int,
    content: @Composable RowScope.(index: Int) -> Unit
) {
    HorizontalPager(
        state = pagerState,
        count = count,
        contentPadding = PaddingValues(horizontal = 64.dp),
    ) { page ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Weight()
            content(page)
            Weight()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Card(
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    border: BorderStroke? = null,
    elevation: Dp = 1.dp,
    trailingButton: (@Composable () -> Unit)? = null,
    icon: @Composable () -> Unit,
    text: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        border = border,
        elevation = elevation,
        backgroundColor = color,
        shape = shape(size = 12),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dp { small }, vertical = dp { tiny }),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon()
            Space { mediumLarge }
            text()
            Weight(.7)
            AnimatedVisibility(
                visible = trailingButton != null,
                enter = fadeIn(animationSpec = tween(durationMillis = 500)),
                exit = fadeOut(animationSpec = tween(durationMillis = 500))
            ) {
                trailingButton?.let { button ->
                    button()
                    Space { medium }
                }
            }
        }
    }
}

@Composable
private fun Icon(
    icon: Icons.Source,
    description: String
) {
    Surface(
        modifier = Modifier.padding(all = dp { small }),
        shape = CircleShape,
        shadowElevation = 4.dp,
        color = palette { surface },
    ) {
        Box(
            modifier = Modifier.padding(all = dp { smallTiny }),
            contentAlignment = Alignment.Center,
            content = {
                Image(
                    modifier = Modifier.size(28.dp),
                    painter = painter { (icon as Painter).value },
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop,
                    contentDescription = description
                )
            }
        )
    }
}

@Composable
private fun Name(
    name: String,
    color: Color
) {
    Text(
        text = name,
        style = titleStyle(size = 20.0) { large }.copy(fontWeight = FontWeight.Medium),
        color = color,
    )
}

@Composable
private fun EditButton(
    colors: Colors,
    onClick: () -> Unit
) {
    Surface(
        shape = CircleShape,
        border = BorderStroke(width = 2.dp, color = colors.content.alpha(.3)),
        color = colors.container
    ) {
        RippleStyle(color = colors.content) {
            Icons.Icon(
                source = Icons.rounded { DriveFileRenameOutline },
                size = 22.dp,
                color = colors.content.alpha(.8),
                onClick = onClick
            )
        }
    }
}

@Composable
private fun Animate(
    visible: Boolean,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    val fadeSpec = tween<Float>(durationMillis = 600)
    //val expandShrinkSpec = tween<IntSize>(durationMillis = 600)
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = fadeSpec)/* + expandIn(
            animationSpec = expandShrinkSpec,
            expandFrom = Alignment.Center
        )*/,
        exit = fadeOut(animationSpec = fadeSpec)/* + shrinkOut(
            animationSpec = expandShrinkSpec,
            shrinkTowards = Companion.Center
        )*/,
        content = content
    )
}

private fun MarketBrandPicker.UiModel.isEditState(): Boolean =
    this.editing()

private fun MarketBrandPicker.UiModel.isSelectedBrandState(): Boolean =
    !this.isEditState() && this.selected.isNotNullOrUndefined()

private fun MarketBrandPicker.UiModel.isNonSelectedBrandState(): Boolean =
    !this.isEditState() && !this.isSelectedBrandState()

private fun MarketBrandPicker.ItemUiModel?.isNotNullOrUndefined(): Boolean =
    this != null && this.id != Market.Brand.Undefined.id