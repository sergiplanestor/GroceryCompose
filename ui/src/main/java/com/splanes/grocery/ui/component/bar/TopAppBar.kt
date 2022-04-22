package com.splanes.grocery.ui.component.bar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.splanes.grocery.ui.component.bar.AppBars.Top.navIconColor
import com.splanes.grocery.ui.component.bar.AppBars.Top.titleColor
import com.splanes.grocery.ui.component.icons.Icons
import com.splanes.grocery.ui.utils.resources.Strings
import com.splanes.grocery.ui.utils.resources.palette
import com.splanes.grocery.ui.utils.resources.headlineStyle
import com.splanes.grocery.ui.utils.resources.string
import com.splanes.toolkit.compose.ui.theme.utils.accessors.Colors

@Composable
fun TopAppBar(
    uiModel: AppBars.Top.UiModel,
    modifier: Modifier = Modifier,
    navHostController: NavHostController? = null
) {
    with(uiModel) {
        val topAppBarColors = colors(Colors)
        SmallTopAppBar(
            modifier = modifier,
            colors = topAppBarColors,
            title = {
                TopAppBarTitle(
                    text = title(),
                    color = topAppBarColors.titleColor(),
                    align = TextAlign.Start,
                    style = headlineStyle { small }
                )
            },
            navigationIcon = {
                navIcon?.let { iconSourceBuilder ->
                    Icons.Icon(
                        source = iconSourceBuilder(),
                        size = Icons.SizeSmall.dp,
                        color = topAppBarColors.navIconColor(),
                        onClick = { onNavClick(navHostController) },
                        description = string { Strings.content_desc_top_bar_nav_icon }
                    )
                }
            },
            actions = actions,
        )
    }
}

@Composable
fun TopAppBarTitle(
    text: String,
    color: Color = palette { onPrimary },
    align: TextAlign = TextAlign.Start,
    style: TextStyle = headlineStyle { small }
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = color,
            textAlign = align,
            style = style
        )
    }
}