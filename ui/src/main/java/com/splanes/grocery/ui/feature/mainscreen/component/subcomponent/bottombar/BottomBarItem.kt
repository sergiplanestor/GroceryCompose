package com.splanes.grocery.ui.feature.mainscreen.component.subcomponent.bottombar

import androidx.annotation.StringRes
import com.splanes.grocery.ui.component.icons.Icons
import com.splanes.grocery.ui.component.icons.painter
import com.splanes.grocery.ui.utils.resources.Drawables
import com.splanes.grocery.ui.utils.resources.Strings

enum class BottomBarItem(
    val id: Int,
    @StringRes val labelRes: Int,
    val icon: Icons.Source,
) {
    Markets(
        id = 0,
        labelRes = Strings.markets,
        icon = Icons.painter(Drawables.ic_grocery_market)
    ),
    Products(
        id = 1,
        labelRes = Strings.products,
        icon = Icons.painter(Drawables.ic_grocery_product)
    ),
    GroceryListDashboard(
        id = 2,
        labelRes = Strings.grocery,
        icon = Icons.painter(Drawables.ic_grocery_bag)
    ),
    Templates(
        id = 3,
        labelRes = Strings.templates,
        icon = Icons.painter(Drawables.ic_template)
    ),
    Account(
        id = 4,
        labelRes = Strings.account,
        icon = Icons.painter(Drawables.ic_account_settings)
    );

    companion object {
        fun map(id: Int?): BottomBarItem = values().find { it.id == id } ?: GroceryListDashboard
    }
}