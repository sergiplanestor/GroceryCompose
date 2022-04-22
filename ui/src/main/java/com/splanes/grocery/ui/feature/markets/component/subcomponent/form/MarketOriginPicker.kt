package com.splanes.grocery.ui.feature.markets.component.subcomponent.form

import androidx.annotation.RawRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.splanes.grocery.ui.component.anim.LottieComponent
import com.splanes.grocery.ui.component.button.Buttons
import com.splanes.grocery.ui.component.spacer.column.Space
import com.splanes.grocery.ui.component.spacer.row.Weight
import com.splanes.grocery.ui.feature.markets.component.subcomponent.form.contract.MarketOriginType
import com.splanes.grocery.ui.utils.anim.Animations
import com.splanes.grocery.ui.utils.resources.Strings
import com.splanes.grocery.ui.utils.resources.bodyStyle
import com.splanes.grocery.ui.utils.resources.dp
import com.splanes.grocery.ui.utils.resources.palette
import com.splanes.grocery.ui.utils.resources.shape
import com.splanes.grocery.ui.utils.resources.string
import com.splanes.grocery.ui.utils.resources.titleStyle
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha
import com.splanes.toolkit.compose.ui.components.common.utils.color.composite
import com.splanes.grocery.ui.R.raw as Raw

@Composable
fun MarketOriginPickerForm(onOriginSelected: (MarketOriginType) -> Unit) {

    var originSelected by remember { mutableStateOf(MarketOriginType.Location) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dp { medium })
    ) {
        MarketOriginPickerDescription(
            modifier = Modifier.padding(
                top = dp { small },
                start = dp { small },
                end = dp { small })
        )
        Space { mediumLarge }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Weight()
            MarketOriginType.values().forEachIndexed { i, origin ->
                with(origin) {
                    MarketOriginOption(
                        text = uiText(),
                        animRes = animRes(),
                        selected = this == originSelected,
                        onSelect = { originSelected = this }
                    )
                }
                if (i == 0) Weight()
            }
            Weight()
        }
        Space { large }
        Buttons.Fill(
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            text = originSelected.uiButtonText(),
            onClick = { onOriginSelected(originSelected) }
        )
        Space { mediumLarge }
    }
}

@Composable
fun ColumnScope.MarketOriginPickerDescription(modifier: Modifier) {
    Text(
        modifier = modifier,
        text = string { Strings.market_form_origin_picker_description },
        style = titleStyle(),
        color = palette { onSurface }.alpha(.8),
        textAlign = TextAlign.Justify
    )
    Space { mediumSmall }
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(color = palette { onSurface }.alpha(.15), shape = shape(size = 20)),
        color = palette { onSurface }.alpha(.15)
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MarketOriginOption(
    text: String,
    @RawRes animRes: Int,
    selected: Boolean,
    onSelect: () -> Unit
) {
    val cardElevation by animateDpAsState(targetValue = (if (selected) 4 else 1).dp)
    val cardBackground by animateColorAsState(
        targetValue = palette {
            if (selected) {
                primaryContainer.composite(surface, .45)
            } else {
                surface
            }
        },
        animationSpec = Animations.tween { medium }
    )
    val animAlpha by animateFloatAsState(targetValue = (if (selected) 1 else .4).toFloat())
    val textSize by animateFloatAsState(targetValue = (if (selected) bodyStyle { medium } else bodyStyle { small }).fontSize.value)
    val textColor by animateColorAsState(targetValue = palette { if (selected) primary else onSurface })
    val textWeight by animateFloatAsState(targetValue = (if (selected) FontWeight.Medium else FontWeight.Normal).weight.toFloat())
    Card(
        modifier = Modifier.size(size = 120.dp),
        elevation = cardElevation,
        onClick = onSelect,
        role = Role.RadioButton,
        backgroundColor = cardBackground
    ) {
        Column(
            modifier = Modifier.padding(all = dp { mediumSmall }),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LottieComponent(
                modifier = Modifier
                    .weight(1f)
                    .alpha(animAlpha),
                animRawRes = animRes,
                size = 60.dp,
                speed = (if (selected) 1 else .45).toFloat(),
                loop = true,
            )
            Space { mediumSmall }
            Text(
                text = text,
                style = titleStyle(size = textSize.toDouble()),
                color = textColor,
                fontWeight = FontWeight(textWeight.toInt()),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun MarketOriginType.uiText(): String = string {
    when (this) {
        MarketOriginType.Location -> Strings.market_form_origin_location_text
        MarketOriginType.ManualForm -> Strings.market_form_origin_manual_form_text
    }
}

@Composable
private fun MarketOriginType.uiButtonText(): String = string {
    when (this) {
        MarketOriginType.Location -> Strings.search
        MarketOriginType.ManualForm -> Strings.fill
    }
}

private fun MarketOriginType.animRes(): Int = when (this) {
    MarketOriginType.Location -> Raw.anim_maps
    MarketOriginType.ManualForm -> Raw.anim_fill_form
}