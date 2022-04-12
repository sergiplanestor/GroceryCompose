package com.splanes.grocery.ui.component.bar

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.splanes.grocery.ui.component.icons.Icons
import com.splanes.grocery.ui.utils.resources.color
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha
import com.splanes.toolkit.compose.ui.theme.feature.colors.ThemeColorScheme

object AppBars {

    object Status {

    }

    object Top {
        data class UiModel(
            val title: @Composable () -> String = { "" },
            val colors: @Composable ThemeColorScheme.() -> TopAppBarColors = { colorsSmall() },
            val navIcon: (() -> Icons.Source)? = null,
            val onNavClick: (NavHostController?) -> Unit = {},
            val actions: @Composable RowScope.() -> Unit = {}
        ) {
            companion object {
                fun empty(): UiModel = UiModel()
            }
        }

        @Composable
        fun colorsSmall(
            containerColor: Color = color { primary },
            titleColor: Color = color { onPrimary },
            navIconColor: Color = titleColor.alpha(.8),
            actionIconColor: Color = titleColor.alpha(.8),
        ): TopAppBarColors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = containerColor,
            titleContentColor = titleColor,
            navigationIconContentColor = navIconColor,
            actionIconContentColor = actionIconColor
        )

        @Composable
        fun TopAppBarColors.titleColor(isExpanded: Boolean = true): Color =
            titleContentColor(scrollFraction = if (isExpanded) .0f else 1.0f).value

        @Composable
        fun TopAppBarColors.navIconColor(isExpanded: Boolean = true): Color =
            navigationIconContentColor(scrollFraction = if (isExpanded) .0f else 1.0f).value

        @Composable
        fun TopAppBarColors.actionIconColor(isExpanded: Boolean = true): Color =
            actionIconContentColor(scrollFraction = if (isExpanded) .0f else 1.0f).value

        @Composable
        fun TopAppBarColors.containerColor(isExpanded: Boolean = true): Color =
            containerColor(scrollFraction = if (isExpanded) .0f else 1.0f).value
    }

    object Bottom {
        sealed class ContentUiModel
        data class UiModel(
            val items: List<Item> = emptyList(),
            val selected: Item? = null
        ) {
            data class Item(val id: Int, @StringRes val label: Int, val icon: Icons.Source) : ContentUiModel()
            object Divider : ContentUiModel()

            companion object {
                fun empty(): UiModel = UiModel()
            }
        }
    }

}